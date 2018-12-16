package com.qcz.qmplatform.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间处理工具
 * @author quchangzhong
 * @time 2018年2月13日 下午3:50:47
 */
public class TimeUtils {

	/**
	 * 获取当前时间信息 格式：2018-02-13 15:57:26.366
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取年月日信息，格式：2018-02-13
	 * 
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public static String formatBeforeDate(long nowTime, long time) {
		String beforeTime = "";
		long l = (nowTime - time) / 1000;// 总秒差
		if (l == 0) {
			beforeTime = "刚刚";
		} else if (l > 0 & l / 60 == 0) {
			beforeTime = l + " 秒前";
		} else if ((l = l / 60) / 60 == 0) {
			beforeTime = l + " 分钟前";
		} else if ((l = l / 60) / 24 == 0) {
			beforeTime = l + " 小时前";
		} else if ((l = l / 24) / 30 == 0) {
			beforeTime = l + " 天前";
		} else if ((l = l / 30) / 12 == 0) {
			beforeTime = l + " 月前";
		} else {
			beforeTime = l / 12 + " 年前";
		}
		return beforeTime;
	}

	public static void main(String[] args) {
		System.out.println(getDate());
	}
}
