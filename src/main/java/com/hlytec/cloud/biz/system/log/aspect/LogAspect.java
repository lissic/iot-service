package com.hlytec.cloud.biz.system.log.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hlytec.cloud.biz.system.user.utils.UserUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.apache.ibatis.mapping.SqlCommandType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.biz.system.log.model.entity.SysLog;
import com.hlytec.cloud.biz.system.log.service.LogService;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.utils.IpUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 日志切面类
 * @author: zero
 * @date: 2021/4/28 10:06
 */
@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true)
public class LogAspect {

    private static final String LOG_CONTENT = "[类名]:%s,[方法]:%s,[参数]:%s,[IP]:%s";

    private static final ThreadLocal<Long> startTimeThreadLocal =
            new NamedThreadLocal<>("ThreadLocal StartTime");

    @Autowired
    private LogService logService;

    /**
     * 日志切入点
     */
    @Pointcut("@annotation(com.hlytec.cloud.biz.system.log.annotation.Log)")
    public void logPointCut() {
    }
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        startTimeThreadLocal.set(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "logPointCut()")
    public void doAfter(JoinPoint joinPoint) {
        // 解析Log注解
        String methodName = joinPoint.getSignature().getName();
        Method method = currentMethod(joinPoint, methodName);
        Log log = method.getAnnotation(Log.class);
        Long executedTime = System.currentTimeMillis() - startTimeThreadLocal.get();
        this.saveLog(joinPoint, methodName, log.module()+"-"+log.description(), executedTime);
    }

    /**
     * 获取当前执行的方法
     *
     * @param joinPoint  连接点
     * @param methodName 方法名称
     * @return 方法
     */
    private Method currentMethod(JoinPoint joinPoint, String methodName) {
        // 获取目标类的所有方法，找到当前要执行的方法
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

    private void saveLog(JoinPoint joinPoint, String methodName, String module, Long executeTime) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            User userCache = UserUtil.getCurrentUser();
            String ip = IpUtil.getIpAddr(request);
            String content = this.operateContent(joinPoint, methodName, ip, request);
            String requestUrl = request.getRequestURI();
            String logType = this.getLogType(request);

            logService.save(createLog(module, content, requestUrl, logType, methodName, ip,
                    executeTime, userCache.getLoginName(), 0));
        } catch (Exception e) {
            log.error("save system log error, {}", e.getMessage());
        }
    }

    private SysLog createLog(String title, String content, String requestUrl,
                             String type, String bizType, String clientIp,
                             Long executeTime, String operator, int isException) {
        return SysLog.builder().title(title).content(content).requestUrl(requestUrl).type(type)
                .bizType(bizType).clientIp(clientIp).executeTime(executeTime).operator(operator)
                .isException(isException).build();
    }

    private String operateContent(JoinPoint joinPoint, String methodName, String ip, HttpServletRequest request)
            throws ClassNotFoundException, NotFoundException {
        String className = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, params);
        StringBuilder bf = new StringBuilder();
        nameAndArgs.forEach((key, value) -> {
            if (!StringUtils.equals(key, "request")) {
                bf.append(key).append("=");
                bf.append(value).append("&");
            }
        });

        if (StringUtils.isEmpty(bf.toString())) {
            bf.append(request.getQueryString());
        }
        return String.format(LOG_CONTENT, className, methodName, bf.toString(), ip);
    }

    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<>();

        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
            return map;
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            // paramNames即参数名
            map.put(attr.variableName(i + pos), args[i]);
        }
        return map;
    }

    private String getLogType(HttpServletRequest request) {
        String sqlCommandTypes = ObjectUtils.toString(request.getAttribute(SqlCommandType.class.getName()));
        if (StringUtils.containsAny("," + sqlCommandTypes + ",", ",INSERT,", ",UPDATE,", ",DELETE,")) {
            return SysConstants.TYPE_UPDATE;
        } else if (StringUtils.contains("," + sqlCommandTypes + ",", ",SELECT,")) {
            return SysConstants.TYPE_SELECT;
        } else {
            return SysConstants.TYPE_ACCESS;
        }
    }

}
