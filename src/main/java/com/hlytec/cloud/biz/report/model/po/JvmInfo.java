package com.hlytec.cloud.biz.report.model.po;

import com.hlytec.cloud.utils.MathUtil;
import com.hlytec.cloud.utils.ServerUtil;
import lombok.ToString;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;

/**
 * @description: JvmInfo
 * @author: zero
 * @date: 2021/4/27 10:02
 */
@ToString
public class JvmInfo {
	/**
	 * 当前JVM占用的内存总数(M)
	 */
	private double total;

	/**
	 * JVM最大可用内存总数(M)
	 */
	private double max;

	/**
	 * JVM空闲内存(M)
	 */
	private double free;

	/**
	 * JDK版本
	 */
	private String version;

	/**
	 * JDK路径
	 */
	private String home;

	/**
	 * Gets total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		return MathUtil.div(total, (1024 * 1024), 2);
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
	 * Gets max.
	 *
	 * @return the max
	 */
	public double getMax() {
		return MathUtil.div(max, (1024 * 1024), 2);
	}

	/**
	 * Sets max.
	 *
	 * @param max the max
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * Gets free.
	 *
	 * @return the free
	 */
	public double getFree() {
		return MathUtil.div(free, (1024 * 1024), 2);
	}

	/**
	 * Sets free.
	 *
	 * @param free the free
	 */
	public void setFree(double free) {
		this.free = free;
	}

	/**
	 * Gets used.
	 *
	 * @return the used
	 */
	public double getUsed() {
		return MathUtil.div(total - free, (1024 * 1024), 2);
	}

	/**
	 * Gets usage.
	 *
	 * @return the usage
	 */
	public double getUsage() {
		return MathUtil.mul(MathUtil.div(total - free, total, 4), 100);
	}

	/**
	 * 获取JDK名称
	 *
	 * @return the name
	 */
	public String getName() {
		return ManagementFactory.getRuntimeMXBean().getVmName();
	}

	/**
	 * Gets version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets version.
	 *
	 * @param version the version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets home.
	 *
	 * @return the home
	 */
	public String getHome() {
		return home;
	}

	/**
	 * Sets home.
	 *
	 * @param home the home
	 */
	public void setHome(String home) {
		this.home = home;
	}

	/**
	 * JDK启动时间
	 *
	 * @return the start time
	 */
	public String getStartTime() {
		return DateFormatUtils.format(ServerUtil.getServerStartDate(), ServerUtil.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * JDK运行时间
	 *
	 * @return the run time
	 */
	public String getRunTime() {
		return ServerUtil.getDatePoor(ServerUtil.now(), ServerUtil.getServerStartDate());
	}
}
