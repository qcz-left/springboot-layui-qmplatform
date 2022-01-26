package com.qcz.qmplatform.module.operation.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.operation.domain.DictAttr;
import com.qcz.qmplatform.module.operation.mapper.DictAttrMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-19
 */
@Service
public class DictAttrService extends ServiceImpl<DictAttrMapper, DictAttr> {

    public List<DictAttr> getDictAttrList(String dictId) {
        QueryWrapper<DictAttr> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_id", dictId);
        return list(wrapper);
    }

    public boolean addDictAttrOne(DictAttr dictAttr) {
        dictAttr.setAttrId(IdUtils.getUUID());
        return save(dictAttr);
    }

    public boolean updateDictAttrOne(DictAttr dictAttr) {
        return updateById(dictAttr);
    }

    public boolean deleteDictAttr(List<String> attrIds) {
        return removeByIds(attrIds);
    }

    public boolean validateAttrValue(DictAttr dictAttr) {
        String attrValue = dictAttr.getAttrValue();
        String attrId = dictAttr.getAttrId();
        Assert.notBlank(attrValue);
        QueryWrapper<DictAttr> wrapper = new QueryWrapper<>();
        wrapper.ne(StringUtils.isNotBlank(attrId), "attr_id", attrId);
        wrapper.eq("attr_value", attrValue);
        wrapper.eq("dict_id", dictAttr.getDictId());
        return baseMapper.selectCount(wrapper) == 0;
    }

    public List<Map<String, String>> queryDictAttrListByCode(String code) {
        return baseMapper.queryDictAttrListByCode(code);
    }
}
