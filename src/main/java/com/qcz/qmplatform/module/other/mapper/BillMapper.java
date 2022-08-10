package com.qcz.qmplatform.module.other.mapper;

import com.qcz.qmplatform.module.other.domain.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.other.qo.BillQO;
import com.qcz.qmplatform.module.other.vo.BillVO;

import java.util.List;

/**
 * <p>
 * 账单 Mapper 接口
 * </p>
 *
 * @author quchangzhong
 * @since 2022-08-09
 */
public interface BillMapper extends BaseMapper<Bill> {

    List<BillVO> getList(BillQO bill);

    List<BillVO> selectTest();
}
