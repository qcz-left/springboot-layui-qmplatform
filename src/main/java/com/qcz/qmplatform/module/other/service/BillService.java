package com.qcz.qmplatform.module.other.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.other.domain.Bill;
import com.qcz.qmplatform.module.other.mapper.BillMapper;
import com.qcz.qmplatform.module.other.qo.BillQO;
import com.qcz.qmplatform.module.other.vo.BillVO;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean saveOne(Bill bill) {
        bill.setCreateTime(DateUtils.getCurrTimestamp());
        bill.setCreateUserId(SubjectUtils.getUserId());
        return save(bill);
    }

    public boolean updateOne(Bill bill) {
        return updateById(bill);
    }
}
