package com.qcz.qmplatform.module.operation.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
        LambdaQueryWrapper<DictAttr> wrapper = Wrappers.lambdaQuery(DictAttr.class)
                .eq(DictAttr::getDictId, dictId);
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
        LambdaQueryWrapper<DictAttr> wrapper = Wrappers.lambdaQuery(DictAttr.class)
                .ne(StringUtils.isNotBlank(attrId), DictAttr::getAttrId, attrId)
                .eq(DictAttr::getAttrValue, attrValue)
                .eq(DictAttr::getDictId, dictAttr.getDictId());
        return baseMapper.selectCount(wrapper) == 0;
    }

    public List<Map<String, String>> queryDictAttrListByCode(String code) {
        return baseMapper.queryDictAttrListByCode(code);
    }
}
