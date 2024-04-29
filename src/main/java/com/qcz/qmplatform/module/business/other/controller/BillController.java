package com.qcz.qmplatform.module.business.other.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.business.other.domain.Bill;
import com.qcz.qmplatform.module.business.other.domain.qo.BillQO;
import com.qcz.qmplatform.module.business.other.service.BillService;
import com.qcz.qmplatform.module.business.other.domain.vo.BillVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账单 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2022-08-09
 */
@Controller
@RequestMapping("/other/bill")
@Module("账单")
public class BillController extends BaseController {

    @Resource
    BillService billService;

    @GetMapping("/billListPage")
    public String billListPage() {
        return "/module/other/billList";
    }

    @GetMapping("/billDetailPage")
    public String billDetailPage() {
        return "/module/other/billDetail";
    }

    @PostMapping("/getBillList")
    @ResponseBody
    public ResponseResult<PageResult<BillVO>> getBillList(PageRequest pageRequest, BillQO bill) {
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(billService.getList(bill)));
    }

    @PostMapping("/nnl/selectTest")
    @ResponseBody
    public ResponseResult<Void> selectTest() {
        billService.selectTest();
        return ResponseResult.ok();
    }

    /**
     * 通过账单类型分组统计金额
     */
    @PostMapping("/getAmountGroupByType")
    @ResponseBody
    public ResponseResult<List<Map<String, Object>>> getAmountGroupByType(@RequestBody BillQO bill) {
        return ResponseResult.ok(billService.selectAmountGroupByType(bill));
    }

    /**
     * 通过账单消费时间分组统计金额
     */
    @PostMapping("/getAmountGroupByDate")
    @ResponseBody
    public ResponseResult<List<Map<String, Object>>> getAmountGroupByDate(@RequestBody BillQO bill) {
        return ResponseResult.ok(billService.selectAmountGroupByDate(bill));
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseResult<Bill> get(@PathVariable String id) {
        return ResponseResult.ok(billService.getById(id));
    }

    @PostMapping("/insert")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "新增账单")
    public ResponseResult<Void> insert(@RequestBody Bill bill) {
        return ResponseResult.newInstance(billService.saveOne(bill));
    }

    @PostMapping("/update")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改账单")
    public ResponseResult<Void> update(@RequestBody Bill bill) {
        return ResponseResult.newInstance(billService.updateOne(bill));
    }

    @PostMapping("/delete")
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除账单")
    public ResponseResult<Void> delete(String ids) {
        return ResponseResult.newInstance(billService.removeByIds(StringUtils.split(ids, ',')));
    }
}
