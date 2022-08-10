package com.qcz.qmplatform.module.other.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.other.domain.Bill;
import com.qcz.qmplatform.module.other.qo.BillQO;
import com.qcz.qmplatform.module.other.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private static final String PREFIX = "/module/other/";

    @Autowired
    BillService billService;

    @GetMapping("/billListPage")
    public String billListPage() {
        return PREFIX + "billList";
    }

    @GetMapping("/billDetailPage")
    public String billDetailPage() {
        return PREFIX + "billDetail";
    }

    @PostMapping("/getBillList")
    @ResponseBody
    public ResponseResult<PageResult> getBillList(PageRequest pageRequest, BillQO bill) {
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(billService.getList(bill)));
    }

    @PostMapping("/noNeedLogin/selectTest")
    @ResponseBody
    public ResponseResult<?> selectTest(PageRequest pageRequest) {
        billService.selectTest();
        return ResponseResult.ok();
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseResult<Bill> get(@PathVariable String id) {
        return ResponseResult.ok(billService.getById(id));
    }

    @PostMapping("/insert")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "新增账单")
    public ResponseResult<?> insert(@RequestBody Bill bill) {
        return ResponseResult.newInstance(billService.saveOne(bill));
    }

    @PostMapping("/update")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改账单")
    public ResponseResult<?> update(@RequestBody Bill bill) {
        return ResponseResult.newInstance(billService.updateOne(bill));
    }

    @PostMapping("/delete")
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除账单")
    public ResponseResult<?> delete(String ids) {
        return ResponseResult.newInstance(billService.removeByIds(StringUtils.split(ids, ',')));
    }
}
