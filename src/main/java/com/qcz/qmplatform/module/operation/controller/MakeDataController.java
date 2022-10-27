package com.qcz.qmplatform.module.operation.controller;

import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.module.operation.pojo.DBDetail;
import com.qcz.qmplatform.module.operation.pojo.DataDetail;
import com.qcz.qmplatform.module.operation.service.MakeDataService;
import com.qcz.qmplatform.module.operation.vo.MakeDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 制作测试数据
 */
@Controller
@RequestMapping("/operation/make-data")
public class MakeDataController {

    private static final String PATH_PREFIX = "/module/operation/";

    @Autowired
    MakeDataService makeDataService;

    @GetMapping("/page")
    public String page() {
        return PATH_PREFIX + "makeData";
    }

    @PostMapping("/start")
    @ResponseBody
    public ResponseResult<?> start(@RequestBody MakeDataVO makeDataVO) {
        makeDataService.start(makeDataVO.getDbDetail(), makeDataVO.getDataDetail(), makeDataVO.getInsertNumber());
        return ResponseResult.ok();
    }

    @PostMapping("/testConnect")
    @ResponseBody
    public ResponseResult<?> testConnect(@RequestBody DBDetail dbDetail) throws Exception {
        return ResponseResult.newInstance(makeDataService.testConnect(dbDetail));
    }

    @PostMapping("/getColumnList")
    @ResponseBody
    public ResponseResult<List<DataDetail.ColumnDetail>> getColumnList(@RequestBody MakeDataVO makeDataVO) throws Exception {
        return ResponseResult.ok(makeDataService.getColumnList(makeDataVO.getDbDetail(), makeDataVO.getDataDetail().getTableName()));
    }
}
