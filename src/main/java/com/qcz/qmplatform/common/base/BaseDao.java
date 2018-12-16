package com.qcz.qmplatform.common.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author changzhongq
 * @time 2018年11月17日 下午10:39:02
 */
public interface BaseDao<T> extends Mapper<T> {
	int batchInsert(@Param("table") String table, @Param("cloumns") String cloumns, @Param("list") List<List<Object>> list);

	int batchDelete(@Param("table") String table, @Param("idCloumn") String idCloumn, @Param("ids") Object[] ids);
}
