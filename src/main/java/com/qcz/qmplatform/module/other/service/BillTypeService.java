package com.qcz.qmplatform.module.other.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.TreeUtils;
import com.qcz.qmplatform.module.other.domain.BillType;
import com.qcz.qmplatform.module.other.mapper.BillTypeMapper;
import com.qcz.qmplatform.module.other.pojo.BillTypeTree;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 账单类型 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2022-08-12
 */
@Service
public class BillTypeService extends ServiceImpl<BillTypeMapper, BillType> {

    public boolean saveOne(BillType billType) {
        return save(billType);
    }

    public boolean updateOne(BillType billType) {
        return updateById(billType);
    }

    public List<BillTypeTree> getBillTypeList(BillType billType) {
        return baseMapper.getBillTypeList(billType);
    }

    public List<BillTypeTree> getBillTypeTree(BillType billType) {
        return TreeUtils.buildTree(getBillTypeList(billType));
    }

    public boolean removeByIds(List<String> ids) {
        baseMapper.deleteByIds(ids);
        return true;
    }
}
