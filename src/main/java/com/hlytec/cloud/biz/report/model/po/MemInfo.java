package com.hlytec.cloud.biz.report.model.po;


import com.hlytec.cloud.utils.MathUtil;
import lombok.ToString;

/**
 * @description: MemInfo
 * @author: zero
 * @date: 2021/4/27 10:02
 */
@ToString
public class MemInfo {
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    /**
     * Gets total.
     *
     * @return the total
     */
    public double getTotal() {
        return MathUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * Gets used.
     *
     * @return the used
     */
    public double getUsed() {
        return MathUtil.div(used, (1024 * 1024 * 1024), 2);
    }

    /**
     * Sets used.
     *
     * @param used the used
     */
    public void setUsed(long used) {
        this.used = used;
    }

    /**
     * Gets free.
     *
     * @return the free
     */
    public double getFree() {
        return MathUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    /**
     * Sets free.
     *
     * @param free the free
     */
    public void setFree(long free) {
        this.free = free;
    }

    /**
     * Gets usage.
     *
     * @return the usage
     */
    public double getUsage() {
        return MathUtil.mul(MathUtil.div(used, total, 4), 100);
    }
}
