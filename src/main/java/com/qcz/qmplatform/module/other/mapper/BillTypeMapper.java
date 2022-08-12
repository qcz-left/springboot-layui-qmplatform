package com.qcz.qmplatform.module.other.mapper;

import com.qcz.qmplatform.module.other.domain.BillType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.other.pojo.BillTypeTree;

import java.util.List;

/**
 * <p>
 * 账单类型 Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2022-08-12
 */
public interface BillTypeMapper extends BaseMapper<BillType> {

    List<BillTypeTree> getBillTypeList(BillType billType);

    void deleteByIds(List<String> ids);
}
