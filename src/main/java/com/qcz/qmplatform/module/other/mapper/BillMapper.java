package com.qcz.qmplatform.module.other.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcz.qmplatform.module.other.domain.Bill;
import com.qcz.qmplatform.module.other.domain.qo.BillQO;
import com.qcz.qmplatform.module.other.domain.vo.BillVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据账单类型统计金额
     */
    @MapKey("name")
    List<Map<String, Object>> selectAmountGroupByType(BillQO bill);

    /**
     * 根据账单消费日期统计金额
     */
    @MapKey("name")
    List<Map<String, Object>> selectAmountGroupByDate(BillQO bill);
}
