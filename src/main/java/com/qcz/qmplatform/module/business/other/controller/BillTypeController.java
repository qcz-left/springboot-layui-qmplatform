package com.qcz.qmplatform.module.business.other.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.business.other.domain.BillType;
import com.qcz.qmplatform.module.business.other.domain.pojo.BillTypeTree;
import com.qcz.qmplatform.module.business.other.domain.qo.BillTypeQO;
import com.qcz.qmplatform.module.business.other.service.BillTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 账单类型 前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2022-08-12
 */
@Controller
@RequestMapping("/other/bill-type")
@Module("账单类型")
public class BillTypeController extends BaseController {

    @Resource
    BillTypeService billTypeService;

    @GetMapping("/billTypeListPage")
    public String billTypeListPage() {
        return "/module/other/billTypeList";
    }

    @GetMapping("/billTypeDetailPage")
    public String billTypeDetailPage() {
        return "/module/other/billTypeDetail";
    }

    @PostMapping("/getBillTypeList")
    @ResponseBody
    public ResponseResult<List<BillTypeTree>> getBillTypeList() {
        return ResponseResult.ok(billTypeService.getBillTypeList(new BillTypeQO()));
    }

    @GetMapping("/getBillTypeTree")
    @ResponseBody
    public ResponseResult<List<BillTypeTree>> getBillTypeTree(BillTypeQO billTypeQO) {
        return ResponseResult.ok(billTypeService.getBillTypeTree(billTypeQO));
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseResult<BillType> get(@PathVariable String id) {
        return ResponseResult.ok(billTypeService.getById(id));
    }

    @PostMapping("/insert")
    @ResponseBody
    @RecordLog(type = OperateType.INSERT, description = "新增账单类型")
    public ResponseResult<Void> insert(@RequestBody BillType billType) {
        return ResponseResult.newInstance(billTypeService.saveOne(billType));
    }

    @PostMapping("/update")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改账单类型")
    public ResponseResult<Void> update(@RequestBody BillType billType) {
        return ResponseResult.newInstance(billTypeService.updateOne(billType));
    }

    @PostMapping("/delete")
    @ResponseBody
    @RecordLog(type = OperateType.DELETE, description = "删除账单类型")
    public ResponseResult<Void> delete(String ids) {
        return ResponseResult.newInstance(billTypeService.removeByBillTypeIds(StringUtils.split(ids, ',')));
    }
}
