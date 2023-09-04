package com.qcz.qmplatform.module.business.operation.mapper;

import com.qcz.qmplatform.module.business.operation.domain.DictAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-19
 */
public interface DictAttrMapper extends BaseMapper<DictAttr> {
    List<Map<String, String>> queryDictAttrListByCode(String code);
}
