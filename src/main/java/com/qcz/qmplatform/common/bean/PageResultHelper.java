package com.qcz.qmplatform.common.bean;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qcz.qmplatform.common.exception.BusinessException;
import com.qcz.qmplatform.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具
 *
 * @author quchangzhong
 */
public class PageResultHelper extends PageHelper {

    public static <T> PageResult<T> parseResult(List<T> list) {
        PageResult<T> res = new PageResult<>();
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            res.setCount(page.getTotal());
            res.setList(page.getResult());
        } else {
            res.setCount((long) list.size());
            res.setList(list);
        }
        return res;
    }

    public static <E> Page<E> startPage(PageRequest pageReq) {
        String orderName = StringUtils.nullToDefault(pageReq.getOrderName(), "");
        String order = StringUtils.nullToDefault(pageReq.getOrder(), "");
        // 校验排序字段参数，防止order by注入
        if (StringUtils.isNotBlank(orderName)) {
            if (!ReUtil.isMatch(Validator.GENERAL, orderName.trim())) {
                throw new BusinessException("排序字段参数异常，请检查！");
            }
        }
        if (StringUtils.isNotBlank(order)) {
            if (!StringUtils.equalsAnyIgnoreCase(order.trim(), "asc",  "desc")) {
                throw new BusinessException("排序字段参数异常，请检查！");
            }
        }

        Integer page = pageReq.getPage();
        Integer limit = pageReq.getLimit();
        if (page == null) {
            pageReq.setPage(1);
        }
        if (limit == null) {
            pageReq.setLimit(10);
        }
        if (StringUtils.isBlank(orderName)) {
            return PageHelper.startPage(pageReq.getPage(), pageReq.getLimit());
        } else {
            String[] orderNames = orderName.split(",");
            String[] orders = order.split(",");
            List<String> orderBy = new ArrayList<>();
            for (int i = 0; i < orderNames.length; i++) {
                orderBy.add(StringUtils.toUnderlineCase(orderNames[i]) + " " + (StringUtils.isBlank(orders[i]) ? "asc" : orders[i]));
            }
            return PageHelper.startPage(pageReq.getPage(), pageReq.getLimit(), CollectionUtil.join(orderBy, ","));
        }
    }

}
