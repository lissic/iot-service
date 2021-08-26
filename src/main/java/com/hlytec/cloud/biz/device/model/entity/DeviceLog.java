package com.hlytec.cloud.biz.device.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.biz.system.user.utils.UserUtil;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * @description: DeviceLog
 * @author: zero
 * @date: 2021/7/21 14:17
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_device_log")
public class DeviceLog extends BaseEntity<DeviceLog> {
    public DeviceLog(){}
    @TableField("device_id")
    private String deviceId;
    @TableField("card_id")
    private String cardId;
    /**
     * 操作类型
     * 0-登录,心跳
     * 1-下发网络配置
     * 2-上报基础信息
     * 3-上报事件信息
     * 4-获取基本信息
     * 5-获取配置信息
     * 6-下发现场参数
     * 7-下发温度参数
     * 8-下发充电参数
     * 9-下发时间参数
     * 10-下发核容参数
     * 11-核容
     * 12-充电
     */
    @TableField("operation_type")
    private Integer operationType;
    @TableField("content")
    private String content;
    /**
     * 操作结果:0-失败；1-成功
     */
    @TableField("result")
    private Integer result;

    @TableField("operation_person")
    private String operationPerson;

    /**
     * 生成设备操作日志对象
     * @param device device
     * @param content content
     * @param operationType operationType
     * @return DeviceLog
     */
    public static DeviceLog buildDevLog(Device device, String content, Integer operationType) {
        String loginName = UserUtil.getCurrentUser().getLoginName();
        if (StringUtils.isEmpty(content)) {
            content = SysConstants.GUID_PREFIX + device.getCardId();
        }
        return DeviceLog.builder().deviceId(device.getId())
                .cardId(device.getCardId()).operationType(operationType)
                .content(content).operationPerson(loginName).result(2).build();
    }

}
