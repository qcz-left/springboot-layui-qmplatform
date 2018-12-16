package com.qcz.qmplatform.module.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 通用数据查询
 * @author changzhongq
 * @time 2018年11月14日 下午5:30:59
 */
public interface CommonDao {
	Map<String, Object> findById(@Param("table") String table, @Param("columnsStr") String columnsStr, @Param("fieldName") String fieldName, @Param("id") Object id);

	int batchInsert(@Param("table") String table, @Param("cloumns") String cloumns, @Param("list") List<List<Object>> list);

	int batchDelete(@Param("table") String table, @Param("idCloumn") String idCloumn, @Param("ids") Object[] ids);

	int deleteBy(@Param("table") String table, @Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);

	int deleteByFieldList(@Param("table") String table, @Param("list") List<Field> fields);
}
