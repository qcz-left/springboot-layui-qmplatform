package com.qcz.qmplatform.module.business.operation.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.IdUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.business.operation.domain.Dict;
import com.qcz.qmplatform.module.business.operation.domain.DictAttr;
import com.qcz.qmplatform.module.business.operation.mapper.DictMapper;
import jakarta.annotation.Resource;
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

    @Resource
    private DictAttrService dictAttrService;

    public List<Dict> getDictList(Dict dict) {
        String dictName = dict.getDictName();
        LambdaQueryWrapper<Dict> wrapper = Wrappers.lambdaQuery(Dict.class)
                .like(StringUtils.isNotBlank(dictName), Dict::getDictName, dictName);
        return list(wrapper);
    }

    public boolean addDictOne(Dict dict) {
        dict.setDictId(IdUtils.getUUID());
        return save(dict);
    }

    public boolean updateDictOne(Dict dict) {
        return updateById(dict);
    }

    public boolean deleteDict(List<String> dictIds) {
        LambdaQueryWrapper<DictAttr> dictAttrQueryWrapper = Wrappers.lambdaQuery(DictAttr.class)
                .in(DictAttr::getDictId, dictIds);
        dictAttrService.remove(dictAttrQueryWrapper);
        return removeByIds(dictIds);
    }

    public boolean validateDictCode(String dictId, String dictCode) {
        Assert.notBlank(dictCode);
        LambdaQueryWrapper<Dict> wrapper = Wrappers.lambdaQuery(Dict.class)
                .ne(StringUtils.isNotBlank(dictId), Dict::getDictId, dictId)
                .eq(Dict::getDictCode, dictCode);
        return baseMapper.selectCount(wrapper) == 0;
    }
}
