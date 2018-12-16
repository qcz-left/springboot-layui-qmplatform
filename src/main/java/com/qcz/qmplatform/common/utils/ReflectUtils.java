package com.qcz.qmplatform.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 反射工具类
 * @author changzhongq
 */
public class ReflectUtils {

	/**
	 * 获取表名
	 * @param clazz 类类型
	 */
	public static String getTable(Class<?> clazz) {
		String simpleName = clazz.getSimpleName();
		Table tableAnnotation = clazz.getAnnotation(Table.class);
		String table = null;
		// 如果实体类上有注解，则从注解中获取表名，没有则使用类名驼峰大写转下划线名称（如：AbcDef：ABC_DEF）
		if (tableAnnotation == null) {
			table = StringUtils.underUpperScoreName((simpleName));
		} else {
			table = tableAnnotation.name();
		}
		return table;
	}

	/**
	 * 获取ID字段 格式 CREATE_USER_ID
	 * @param clazz 类类型
	 */
	public static String getIdColumn(Class<?> clazz) {
		String idColumn = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotation(Id.class) != null) {
				idColumn = StringUtils.underUpperScoreName(field.getName());
				break;
			}
		}
		return idColumn;
	}

	/**
	 * 获取表所有字段字符串
	 */
	public static String getColumnsStr(Class<?> clazz) {
		StringBuilder columnsSb = new StringBuilder();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if ("serialVersionUID".equals(fieldName)) {
				continue;
			}
			columnsSb.append(StringUtils.underUpperScoreName(fieldName));
			columnsSb.append(",");
		}
		return columnsSb.deleteCharAt(columnsSb.length() - 1).toString();
	}

	/**
	 * 获取数据ID值
	 * @param data
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getId(Object data) throws IllegalArgumentException, IllegalAccessException {
		Object id = null;
		Field[] fields = data.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotation(Id.class) != null) {
				field.setAccessible(true);
				id = field.get(data);
				break;
			}
		}
		return id;
	}

	/**
	 * 给实体设置id值
	 * @param data 实体数据
	 * @param id id值
	 * @return 设置后的实体数据
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object setId(Object data, Object id) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = data.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getAnnotation(Id.class) != null) {
				field.setAccessible(true);
				field.set(data, id);
				break;
			}
		}

		return data;
	}

	/**
	 * 根据子类获取父类中的泛型（service或controller中）
	 * @param clazz 子类类型
	 * @return 泛型类型
	 */
	public static Class<?> getEntityClass(Class<?> clazz) {
		ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
		return (Class<?>) type.getActualTypeArguments()[0];
	}
}
