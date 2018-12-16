package com.qcz.qmplatform.common.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.common.utils.ExcelUtils;
import com.qcz.qmplatform.common.utils.ReflectUtils;
import com.qcz.qmplatform.common.utils.StringUtils;

/**
 * 基础服务类
 * @author changzhongq
 * @time 2018年11月17日 下午11:03:13
 */
@SuppressWarnings("unchecked")
public class BaseService<T, M extends BaseDao<T>> {

	protected Logger logger = null;

	@Autowired
	protected M mapper;

	private Class<T> classT;

	protected BaseService() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		classT = (Class<T>) params[0];
		logger = LoggerFactory.getLogger(getClass());
	}

	public T find(Object id) {
		return mapper.selectByPrimaryKey(id);
	}

	public ResponseResult delete(Object id) {
		int deleteByPrimaryKey = mapper.deleteByPrimaryKey(id);
		if (deleteByPrimaryKey > 0) {
			return ResponseResult.ok("删除记录成功！", id);
		}
		return ResponseResult.error("删除失败！");
	}

	public Object save(T data) {
		Object id = null;
		try {
			id = ReflectUtils.getId(data);
			if (StringUtils.isEmpty(id)) {
				id = StringUtils.getUUID();
				return mapper.insertSelective((T) ReflectUtils.setId(data, id)) > 0 ? id : null;
			} else if (mapper.selectByPrimaryKey(id) == null) {
				return mapper.insertSelective(data) > 0 ? id : null;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return mapper.updateByPrimaryKeySelective(data) > 0 ? id : null;
	}

	/**
	 * 批量添加
	 * @param models
	 */
	@Transactional
	public List<Object> batchInsert(Object[] models) {
		if (models.length == 0) {
			return null;
		}
		String table = ReflectUtils.getTable(classT);
		List<Object> idValues = new ArrayList<Object>();
		Field[] fields = classT.getDeclaredFields();
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
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				lists.add(fieldValue);
			}
			listLists.add(lists);
		}
		return mapper.batchInsert(table, ReflectUtils.getColumnsStr(classT), listLists) == models.length ? idValues : null;
	}

	/**
	 * 批量更新
	 * @param models
	 */
	@Transactional
	public boolean batchUpdate(T[] models) {
		for (T model : models) {
			int update = mapper.updateByPrimaryKey(model);
			if (update <= 0) {
				throw new RuntimeException("更新记录失败...");
			}
		}
		return true;
	}

	/**
	 * 批量删除记录
	 * @param ids id数组
	 */
	public boolean batchDelete(Object[] ids) {
		String idColumn = ReflectUtils.getIdColumn(classT);
		String table = ReflectUtils.getTable(classT);
		return mapper.batchDelete(table, idColumn, ids) == ids.length;
	}

	/**
	 * 获取HSSFWorkbook对象，使用URL方式调用Java接口，获取数据后按格式填充值
	 * @param map
	 * @throws IOException
	 */
	public HSSFWorkbook getHSSFWorkbook(Map<String, Object> map) throws IOException {
		String exportUrl = (String) map.get("exportUrl");
		URL url = new URL(exportUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder result = new StringBuilder();
		String line = "";
		while ((line = br.readLine()) != null) {
			result.append(line);
		}
		// 释放资源
		br.close();
		connection.disconnect();

		Map<String, Object> pageResult = (Map<String, Object>) JSONUtils.parse(result.toString());
		List<?> rows = (List<?>) pageResult.get("rows");// 需要导出的数据，需要转换
		List<String> titleLabels = (List<String>) map.get("titleLabels");// 列名，对应jqgrid的colModel中的label属性
		List<String> titleNames = (List<String>) map.get("titleNames");// 列名标识，对应jqgrid的colModel中的index属性
		String sheetName = (String) map.get("sheetName");// sheet名称
		return ExcelUtils.getHSSFWorkbook(sheetName, titleLabels, titleNames, rows, null);
	}

}
