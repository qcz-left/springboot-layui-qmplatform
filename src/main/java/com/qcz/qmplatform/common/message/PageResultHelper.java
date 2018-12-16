package com.qcz.qmplatform.common.message;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qcz.qmplatform.common.utils.StringUtils;

/**
 * 
 * @author changzhongq
 * @time 2018年6月12日 上午9:16:44
 */
public class PageResultHelper extends PageHelper {

	public static <T> PageResult parseResult(List<T> list) {
		PageResult res = new PageResult();
		if (list instanceof Page) {
			Page<T> page = (Page<T>) list;
			res.setCount(page.getTotal());
			res.setData(page.getResult());
		} else {
			res.setCount(Long.valueOf(list.size()));
			res.setData(list);
		}
		// 此处是为了迎合layui 数据表格的数据格式
		res.setCode("0");
		if (res.getCount() == 0) {
			res.setMsg("无数据");
		} else {
			res.setMsg("查询成功");
		}
		return res;
	}

	public static <E> Page<E> startPage(PageRequest pageReq) {
		String order = pageReq.getOrder();
		if (StringUtils.isEmpty(order)) {
			return PageHelper.startPage(pageReq.getPage(), pageReq.getLimit());
		} else {
			return PageHelper.startPage(pageReq.getPage(), pageReq.getLimit(), StringUtils.underScoreName(pageReq.getField()) + " " + order);
		}
	}
}
