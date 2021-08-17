package com.qcz.qmplatform.module.operation.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.operation.domain.Dict;
import com.qcz.qmplatform.module.operation.domain.DictAttr;
import com.qcz.qmplatform.module.operation.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-19
 */
@Service
public class DictService extends ServiceImpl<DictMapper, Dict> {

    @Autowired
    private DictAttrService dictAttrService;

    public List<Dict> getDictList(Dict Dict) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        String DictName = Dict.getDictName();
        wrapper.like(StringUtils.isNotBlank(DictName), "dict_name", DictName);
        return list(wrapper);
    }

    public boolean addDictOne(Dict dict) {
        dict.setDictId(IdUtils.simpleUUID());
        return save(dict);
    }

    public boolean updateDictOne(Dict dict) {
        return updateById(dict);
    }

    public boolean deleteDict(List<String> dictIds) {
        QueryWrapper<DictAttr> dictAttrQueryWrapper = new QueryWrapper<>();
        dictAttrQueryWrapper.in("dict_id", dictIds);
        dictAttrService.remove(dictAttrQueryWrapper);
        return removeByIds(dictIds);
    }

    public boolean validateDictCode(String dictId, String dictCode) {
        Assert.notBlank(dictCode);
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.ne(StringUtils.isNotBlank(dictId), "dict_id", dictId);
        wrapper.eq("dict_code", dictCode);
        return baseMapper.selectCount(wrapper) == 0;
    }
}
