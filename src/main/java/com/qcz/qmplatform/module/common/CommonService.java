package com.qcz.qmplatform.module.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.qcz.qmplatform.common.utils.CommonUtils;
import com.qcz.qmplatform.common.utils.ReflectUtils;
import com.qcz.qmplatform.common.utils.SpringContextUtil;
import com.qcz.qmplatform.common.utils.StringUtils;

/**
 * 通用服务（目前主要封装一些查询数据库的通用工具）
 * @author changzhongq
 * @time 2018年11月14日 下午5:25:48
 */
public class CommonService {

	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

	private static CommonDao commonDao;

	private static CommonDao getDao() {
		if (commonDao == null) {
			commonDao = (CommonDao) SpringContextUtil.getBean(CommonDao.class);
		}
		return commonDao;
	}

	/**
	 * 根据ID查询数据
	 * @param clazz 类类型
	 * @param id 数据ID
	 * @return key为数据库字段的Map数据
	 */
	public static Map<String, Object> findById(Class<?> clazz, Object id) {
		String table = ReflectUtils.getTable(clazz);
		String idColumn = ReflectUtils.getIdColumn(clazz);
		return getDao().findById(table, ReflectUtils.getColumnsStr(clazz), idColumn, id);
	}

	/**
	 * 根据ID查询数据
	 * @param clazz 类类型
	 * @param id 数据ID
	 * @return key为驼峰形式的Map数据
	 */
	public static Map<String, Object> findByIdWithLowerCaseKey(Class<?> clazz, Object id) {
		Map<String, Object> data = findById(clazz, id);
		if (data == null) {
			return null;
		}
		Map<String, Object> newData = new HashMap<>();
		for (String key : data.keySet()) {
			newData.put(StringUtils.camelCaseName(key), data.get(key));
		}
		return newData;
	}

	/**
	 * 批量添加
	 * @param models
	 * @return id集合
	 */
	@Transactional
	public static List<Object> batchInsert(Object[] models) {
		if (models.length == 0) {
			logger.warn("批量插入的数据集合为空！");
			return null;
		}
		Class<?> clazz = models[0].getClass();
		String table = ReflectUtils.getTable(clazz);
		List<Object> idValues = new ArrayList<Object>();
		Field[] fields = clazz.getDeclaredFields();
		List<List<Object>> listLists = new ArrayList<List<Object>>();
		// 需要插入的数据
		for (Object model : models) {
			List<Object> lists = new ArrayList<Object>();
			for (Field field : fields) {
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				Object fieldValue = null;
				// 设置可见性
				field.setAccessible(true);
				try {
					fieldValue = field.get(model);
					if (field.getAnnotation(Id.class) != null) {
						if (StringUtils.isEmpty(fieldValue)) {
							fieldValue = StringUtils.getUUID();
						}
						idValues.add(fieldValue);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error("反射获取属性：{}的值失败！", fieldName, e);
				}
				lists.add(fieldValue);
			}
			listLists.add(lists);
		}
		return getDao().batchInsert(table, ReflectUtils.getColumnsStr(clazz), listLists) == models.length ? idValues : null;
	}

	/**
	 * 根据字段值删除记录
	 * @param clazz 类类型
	 * @param fieldName
	 * @param fieldValue
	 */
	public static boolean deleteBy(Class<?> clazz, String fieldName, Object fieldValue) {
		String table = ReflectUtils.getTable(clazz);
		return getDao().deleteBy(table, StringUtils.underUpperScoreName(fieldName), fieldValue) >= 0;
	}

	/**
	 * 通过属性删除记录
	 * @param clazz 类类型
	 * @param map 属性值
	 */
	public static boolean deleteBy(Class<?> clazz, Map<String, Object> map) {
		String table = ReflectUtils.getTable(clazz);
		List<com.qcz.qmplatform.module.common.Field> fields = CommonUtils.mapToField(map);
		return getDao().deleteByFieldList(table, fields) >= 0;
	}

}
