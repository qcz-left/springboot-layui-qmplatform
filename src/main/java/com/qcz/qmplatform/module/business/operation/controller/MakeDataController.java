package com.qcz.qmplatform.module.business.operation.controller;

import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.module.business.operation.domain.pojo.DBDetail;
import com.qcz.qmplatform.module.business.operation.domain.pojo.DataDetail;
import com.qcz.qmplatform.module.business.operation.service.MakeDataService;
import com.qcz.qmplatform.module.business.operation.domain.vo.MakeDataVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 制作测试数据
 */
@Controller
@RequestMapping("/operation/make-data")
public class MakeDataController {

    @Resource
    MakeDataService makeDataService;

    @GetMapping("/page")
    public String page() {
        return "/module/operation/makeData";
    }

    @PostMapping("/start")
    @ResponseBody
    public ResponseResult<Void> start(@RequestBody MakeDataVO makeDataVO) {
        makeDataService.start(makeDataVO.getDbDetail(), makeDataVO.getDataDetail(), makeDataVO.getInsertNumber());
        return ResponseResult.ok();
    }

    @PostMapping("/testConnect")
    @ResponseBody
    public ResponseResult<Void> testConnect(@RequestBody DBDetail dbDetail) {
        return ResponseResult.newInstance(makeDataService.testConnect(dbDetail));
    }

    @PostMapping("/getColumnList")
    @ResponseBody
    public ResponseResult<List<DataDetail.ColumnDetail>> getColumnList(@RequestBody MakeDataVO makeDataVO) {
        return ResponseResult.ok(makeDataService.getColumnList(makeDataVO.getDbDetail(), makeDataVO.getDataDetail().getTableName()));
    }

    @PostMapping("/saveConfigToLocal")
    @ResponseBody
    public ResponseResult<String> saveConfigToLocal(@RequestBody MakeDataVO makeDataVO) {
        String tmpFilePath = ConfigLoader.getDeleteTmpPath() + "/MakeData.dat";
        FileUtils.writeObjectToFile(makeDataVO, tmpFilePath);
        return ResponseResult.ok(null, tmpFilePath);
    }

    @PostMapping("/importLocalConfig")
    @ResponseBody
    public ResponseResult<MakeDataVO> importLocalConfig(MultipartFile file) throws IOException {
        return ResponseResult.ok(FileUtils.readObjectFromFile(file.getInputStream(), MakeDataVO.class));
    }
}
