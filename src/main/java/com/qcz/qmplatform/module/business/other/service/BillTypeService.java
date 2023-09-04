package com.qcz.qmplatform.module.business.other.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.TreeUtils;
import com.qcz.qmplatform.module.business.other.domain.BillType;
import com.qcz.qmplatform.module.business.other.mapper.BillTypeMapper;
import com.qcz.qmplatform.module.business.other.domain.pojo.BillTypeTree;
import com.qcz.qmplatform.module.business.other.domain.qo.BillTypeQO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 保存账单类型
     *
     * @param billType 账单类型数据
     */
    public boolean saveOne(BillType billType) {
        return save(billType);
    }

    /**
     * 更新账单类型
     *
     * @param billType 账单类型数据
     */
    public boolean updateOne(BillType billType) {
        return updateById(billType);
    }

    /**
     * 获取账单类型列表数据
     *
     * @param billTypeQO 查询参数
     */
    public List<BillTypeTree> getBillTypeList(BillTypeQO billTypeQO) {
        // 过滤下当前账单类型
        String id = billTypeQO.getId();
        if (StringUtils.isNotBlank(id)) {
            List<String> billTypeIdList = queryBillTypeIdRecursive(CollectionUtil.newArrayList(id));
            billTypeQO.setNotExistsIds(billTypeIdList);
        }
        return baseMapper.getBillTypeList(billTypeQO);
    }

    /**
     * 获取账单类型树结构数据
     *
     * @param billTypeQO 查询参数
     */
    public List<BillTypeTree> getBillTypeTree(BillTypeQO billTypeQO) {
        return TreeUtils.buildTree(getBillTypeList(billTypeQO));
    }

    /**
     * 删除账单类型（支持多选）
     *
     * @param ids 账单id集合
     */
    public boolean removeByBillTypeIds(List<String> ids) {
        List<String> billTypeIdList = queryBillTypeIdRecursive(ids);
        return super.removeByIds(billTypeIdList);
    }

    /**
     * 级联查询 账单类型ID
     *
     * @param billTypeIds 账单类型ID
     */
    public List<String> queryBillTypeIdRecursive(List<String> billTypeIds) {
        List<String> allIds = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(billTypeIds)) {
            CollectionUtil.addAll(allIds, billTypeIds);

            List<String> childIds = new ArrayList<>();
            CollectionUtil.addAll(childIds, baseMapper.selectObjs(
                    Wrappers.lambdaQuery(BillType.class)
                            .in(BillType::getParentId, billTypeIds)
                            .select(BillType::getId)
            ));

            CollectionUtil.addAll(allIds, queryBillTypeIdRecursive(childIds));
        }

        return allIds;
    }
}
