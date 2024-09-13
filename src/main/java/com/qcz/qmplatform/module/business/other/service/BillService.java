package com.qcz.qmplatform.module.business.other.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.business.other.domain.Bill;
import com.qcz.qmplatform.module.business.other.mapper.BillMapper;
import com.qcz.qmplatform.module.business.other.domain.qo.BillQO;
import com.qcz.qmplatform.module.business.other.domain.vo.BillVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账单 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2022-08-09
 */
@Service
public class BillService extends ServiceImpl<BillMapper, Bill> {

    public List<BillVO> getList(BillQO bill) {
        return baseMapper.getList(bill);
    }

    public void selectTest() {
        baseMapper.selectTest();
    }

    public List<Map<String, Object>> selectAmountGroupByType(BillQO bill) {
        return baseMapper.selectAmountGroupByType(bill);
    }

    public List<Map<String, Object>> selectAmountGroupByDate(BillQO bill) {
        return baseMapper.selectAmountGroupByDate(bill);
    }

    public boolean saveOne(Bill bill) {
        bill.setCreateTime(DateUtils.nowLocalDateTime());
        bill.setCreateUserId(SubjectUtils.getUserId());
        return save(bill);
    }

    public boolean updateOne(Bill bill) {
        return updateById(bill);
    }
}
