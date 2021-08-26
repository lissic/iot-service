package com.hlytec.cloud.biz.report.model.po;


import com.hlytec.cloud.utils.MathUtil;
import lombok.ToString;

/**
 * @description: Cpu
 * @author: zero
 * @date: 2021/4/27 10:02
 */
@ToString
public class CpuInfo {
	/**
	 * 核心数
	 */
	private int cpuNum;

	/**
	 * CPU总的使用率
	 */
	private double total;

	/**
	 * CPU系统使用率
	 */
	private double sys;

	/**
	 * CPU用户使用率
	 */
	private double used;

	/**
	 * CPU当前等待率
	 */
	private double wait;

	/**
	 * CPU当前空闲率
	 */
	private double free;

	/**
	 * Gets cpu num.
	 *
	 * @return the cpu num
	 */
	public int getCpuNum() {
		return cpuNum;
	}

	/**
	 * Sets cpu num.
	 *
	 * @param cpuNum the cpu num
	 */
	public void setCpuNum(int cpuNum) {
		this.cpuNum = cpuNum;
	}

	/**
	 * Gets total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		return MathUtil.round(MathUtil.mul(total, 100), 2);
	}

	/**
	 * Sets total.
	 *
	 * @param total the total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Gets sys.
	 *
	 * @return the sys
	 */
	public double getSys() {
		return MathUtil.round(MathUtil.mul(sys / total, 100), 2);
	}

	/**
	 * Sets sys.
	 *
	 * @param sys the sys
	 */
	public void setSys(double sys) {
		this.sys = sys;
	}

	/**
	 * Gets used.
	 *
	 * @return the used
	 */
	public double getUsed() {
		return MathUtil.round(MathUtil.mul(used / total, 100), 2);
	}

	/**
	 * Sets used.
	 *
	 * @param used the used
	 */
	public void setUsed(double used) {
		this.used = used;
	}

	/**
	 * Gets wait.
	 *
	 * @return the wait
	 */
	public double getWait() {
		return MathUtil.round(MathUtil.mul(wait / total, 100), 2);
	}

	/**
	 * Sets wait.
	 *
	 * @param wait the wait
	 */
	public void setWait(double wait) {
		this.wait = wait;
	}

	/**
	 * Gets free.
	 *
	 * @return the free
	 */
	public double getFree() {
		return MathUtil.round(MathUtil.mul(free / total, 100), 2);
	}

	/**
	 * Sets free.
	 *
	 * @param free the free
	 */
	public void setFree(double free) {
		this.free = free;
	}
}
