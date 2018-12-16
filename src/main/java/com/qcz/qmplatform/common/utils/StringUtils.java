package com.qcz.qmplatform.common.utils;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 字符串处理工具，继承自org.apache.commons.lang3.StringUtils
 * @author quchangzhong
 * @time 2018年2月12日 上午11:34:33
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static final String STRING_UNDER_LINE = "_";// 下划线字符

	public static final String STRING_SPACE = " ";// 一个空格字符

	public static final String STRING_BLANK = "";// 空白字符串

	public static final String STRING_COMMA = ",";// 逗号

	/**
	 * 获取随机字符串
	 * @return 36位字符串
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 驼峰命名字符串 转为下划线，全小写
	 * @param str
	 */
	public static String underScoreName(String str) {
		StringBuilder sb = new StringBuilder();
		if (str != null && str.length() > 0) {
			sb.append(str.substring(0, 1).toLowerCase());
			for (int i = 1; i < str.length(); i++) {
				String s = str.substring(i, i + 1);
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					sb.append(STRING_UNDER_LINE);
				}

				sb.append(s.toLowerCase());
			}
		}

		return sb.toString();
	}

	/**
	 * 驼峰命名字符串 转为下划线，全大写
	 * @param str
	 */
	public static String underUpperScoreName(String str) {
		StringBuilder sb = new StringBuilder();
		if (str != null && str.length() > 0) {
			sb.append(str.substring(0, 1).toUpperCase());
			for (int i = 1; i < str.length(); i++) {
				String s = str.substring(i, i + 1);
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					sb.append(STRING_UNDER_LINE);
				}

				sb.append(s.toUpperCase());
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(underUpperScoreName("ABcD"));
	}

	/**
	 * 首字母大写,其它小写方法
	 * @param str
	 */
	public static String upperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	/**
	 * 下划线转为驼峰命名
	 * @param str
	 */
	public static String camelCaseName(String str) {
		StringBuilder sb = new StringBuilder();
		String[] split = str.split(STRING_UNDER_LINE);
		sb.append(split[0].toLowerCase());
		for (int i = 1; i < split.length; i++) {
			sb.append(upperCase(split[i]));
		}
		return sb.toString();
	}

	/**
	 * 判断对象为否位空
	 * @param object
	 */
	public static boolean isEmpty(Object object) {
		return object == null || "".equals(object);
	}

	/**
	 * 过滤文本中的HTML标签
	 */
	public static String toNoHtml(String inputString) {
		String htmlStr = inputString;
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签

			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}
}
