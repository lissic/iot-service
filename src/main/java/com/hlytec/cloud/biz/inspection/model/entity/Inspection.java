package com.hlytec.cloud.biz.inspection.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @description: InspectionEntity
 * @author: zero
 * @date: 2021/6/30 17:25
 */

@Setter
@Getter
@SuperBuilder
@TableName("net_inspection")
public class Inspection extends BaseEntity<Inspection> {
    public Inspection() {}
    /**
     * 巡检名称
     */
    @TableField("name")
    private String name;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("start_time")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("end_time")
    private Date endTime;
    /**
     * 签到方式：0-现场定位;1-现场拍照
     */
    @TableField("sign_in")
    private Integer signIn;
    /**
     * 巡检状态：0-未开始;1-进行中;2-已完成
     */
    @TableField("status")
    private Integer status;
    /**
     * 实际巡检时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("real_inspect_time")
    private Date realInspectTime;
    /**
     * 巡检人
     */
    @TableField("check_person")
    private String checkPerson;
    /**
     * 巡检结果
     */
    @TableField("check_result")
    private String checkResult;
    /**
     * 巡检结果照片
     */
    @TableField("check_photo")
    private String checkPhoto;

}
