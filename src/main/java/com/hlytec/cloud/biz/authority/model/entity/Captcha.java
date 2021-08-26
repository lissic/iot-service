package com.hlytec.cloud.biz.authority.model.entity;

import lombok.Data;

/**
 * @description: VerifyCode
 * @author: zero
 * @date: 2021/7/1 9:38
 */
@Data
public class Captcha {

    private String code;

    private byte[] imgBytes;

    private long expireTime;

}
