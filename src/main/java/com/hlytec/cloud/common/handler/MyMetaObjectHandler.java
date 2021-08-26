package com.hlytec.cloud.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hlytec.cloud.biz.system.user.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: MyMetaObjectHandler
 * @author: zero
 * @date: 2021/5/19 14:07
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入数据时自动填充属性
     * @param metaObject metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("[MyMetaObjectHandler] Before insert.");
        String userId = UserUtil.getCurrentUserId();
        if (StringUtils.isNotBlank(userId)) {
            this.strictInsertFill(metaObject, "createUser", String.class, userId);
            this.strictInsertFill(metaObject, "updateUser", String.class, userId);
        }
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
    }

    /**
     * 更新数据时自动填充属性
     * @param metaObject metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("[MyMetaObjectHandler] Before update.");
        String userId = UserUtil.getCurrentUserId();
        if (StringUtils.isNotBlank(userId)) {
            this.strictUpdateFill(metaObject, "updateUser", String.class, userId);
        }
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}
