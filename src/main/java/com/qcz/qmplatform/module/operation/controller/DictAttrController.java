package com.qcz.qmplatform.module.operation.controller;


import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PageRequest;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.PageResultHelper;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.operation.domain.DictAttr;
import com.qcz.qmplatform.module.operation.service.DictAttrService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典属性管理前端控制器
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-19
 */
@Controller
@RequestMapping("/operation/dict-attr")
@Module("字典管理")
public class DictAttrController extends BaseController {

    private static final String PATH_PREFIX = "/module/operation/";

    @Autowired
    private DictAttrService dictAttrService;

    @GetMapping("/dictAttrListPage")
    public String dictAttrListPage() {
        return PATH_PREFIX + "dictAttrList";
    }

    @GetMapping("/dictAttrDetailPage")
    public String dictAttrDetailPage() {
        return PATH_PREFIX + "dictAttrDetail";
    }

    /**
     * 获取字典属性列表
     *
     * @param pageRequest 分页请求
     */
    @GetMapping("/getDictAttrList/{dictId}")
    @ResponseBody
    public ResponseResult<PageResult> getDictList(PageRequest pageRequest, @PathVariable String dictId) {
        PageResultHelper.startPage(pageRequest);
        return ResponseResult.ok(PageResultHelper.parseResult(dictAttrService.getDictAttrList(dictId)));
    }

    /**
     * 获取字典属性
     *
     * @param attrId 字典属性id
     */
    @GetMapping("/getDictAttrOne/{attrId}")
    @ResponseBody
    public ResponseResult<DictAttr> getDictAttrOne(@PathVariable String attrId) {
        return ResponseResult.ok(dictAttrService.getById(attrId));
    }

    /**
     * 验证属性值是否存在
     *
     * @param dictAttr 字典属性表单信息
     */
    @GetMapping("/validateAttrValue")
    @ResponseBody
    public ResponseResult<?> validateAttrValue(DictAttr dictAttr) {
        if (!dictAttrService.validateAttrValue(dictAttr)) {
            return ResponseResult.error();
        }
        return ResponseResult.ok();
    }

    /**
     * 根据字典编码查询属性
     *
     * @param code 字典编码
     */
    @GetMapping("/getDictAttrListByCode")
    @ResponseBody
    public ResponseResult<List<Map<String, String>>> getDictAttrListByCode(String code) {
        return ResponseResult.ok(dictAttrService.queryDictAttrListByCode(code));
    }

    /**
     * 添加字典属性
     *
     * @param dictAttr 字典属性信息
     */
    @PostMapping("/addDictAttr")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_DICT_ATTR_SAVE)
    @RecordLog(type = OperateType.INSERT, description = "新增字典属性")
    public ResponseResult<?> addDictAttr(@RequestBody DictAttr dictAttr) {
        if (dictAttrService.addDictAttrOne(dictAttr)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    /**
     * 修改字典属性
     *
     * @param dictAttr 字典属性信息
     */
    @PutMapping("/updateDictAttr")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_DICT_ATTR_SAVE)
    @RecordLog(type = OperateType.UPDATE, description = "修改字典属性")
    public ResponseResult<?> updateDictAttr(@RequestBody DictAttr dictAttr) {
        if (dictAttrService.updateDictAttrOne(dictAttr)) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }

    /**
     * 删除字典属性
     *
     * @param attrIds 字典属性id数组
     */
    @DeleteMapping("/deleteDictAttr")
    @ResponseBody
    @RequiresPermissions(PrivCode.BTN_CODE_DICT_ATTR_DELETE)
    @RecordLog(type = OperateType.DELETE, description = "删除字典属性")
    public ResponseResult<?> deleteDictAttr(String attrIds) {
        if (dictAttrService.deleteDictAttr(Arrays.asList(attrIds.split(",")))) {
            return ResponseResult.ok();
        }
        return ResponseResult.error();
    }
}
