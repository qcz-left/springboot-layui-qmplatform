package com.qcz.qmplatform.module.base.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.exception.CommonException;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.ServletUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.base.BaseController;
import com.qcz.qmplatform.module.base.ExcelTemplateVO;
import com.qcz.qmplatform.module.base.ExportColumn;
import com.qcz.qmplatform.module.base.ExportParamVO;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class CommonController extends BaseController {

    protected static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @GetMapping("/showLogPage")
    public String showLogPage() {
        return "/module/common/showLog";
    }

    /**
     * 文件下载
     *
     * @param filePath 文件路径
     */
    @GetMapping("/downloadFile")
    public ResponseEntity<InputStreamResource> downloadFile(String filePath, String fileName) {
        validateFile(filePath);
        FileSystemResource file = new FileSystemResource(FileUtils.getRealFilePath(filePath));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        fileName = StringUtils.isNotBlank(fileName) ? fileName : file.getFilename();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", new String(Objects.requireNonNull(fileName).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        try {
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(file.getInputStream()));
        } catch (IOException e) {
            throw new CommonException("Failed to download file!", e);
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    @DeleteMapping("/deleteFile")
    @ResponseBody
    public ResponseResult<Void> deleteFile(String filePath) {
        validateFile(filePath);
        FileUtils.del(FileUtils.getRealFilePath(filePath));
        return ResponseResult.ok();
    }

    /**
     * 生成导出文件
     */
    @RequestMapping("/generateExportFile")
    @ResponseBody
    public ResponseResult<String> generateExportFile(@RequestBody ExportParamVO exportParam, HttpServletRequest request) {
        String tmpFilePath;
        try {
            String httpUrl = ServletUtils.getServerPath(request) + exportParam.getQueryUrl();
            HttpRequest httpRequest = HttpUtil.createPost(httpUrl);

            Map<String, Object> param = exportParam.getQueryParam();
            param.put("export", true);

            httpRequest.form(param);
            httpRequest.header(HttpHeaders.COOKIE, request.getHeader(HttpHeaders.COOKIE));
            HttpResponse httpResponse = httpRequest.execute();
            String body = httpResponse.body();

            JSONObject queryResp = JSONUtil.parseObj(body);
            List<?> rows = JSONUtil.toList(queryResp.getJSONObject("data").getJSONArray("list"), Map.class);

            ExcelWriter writer = ExcelUtil.getWriter();
            setExcel(writer, exportParam.getColNames());
            // 一次性写出内容，使用默认样式
            writer.write(rows);
            tmpFilePath = ConfigLoader.getDeleteTmpPath() + DateUtils.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + "_" + exportParam.getGenerateName();
            writeExcel(writer, tmpFilePath);
        } catch (Exception e) {
            throw new CommonException("Failed to export file！", e);
        }

        return ResponseResult.ok(null, tmpFilePath);
    }

    /**
     * 导出文件下载专用请求
     *
     * @param filePath 文件路径
     * @see CommonController#downloadFile(java.lang.String, java.lang.String)
     */
    @GetMapping("/downloadExcelFile")
    public ResponseEntity<InputStreamResource> downloadExcelFile(String filePath, String fileName) {
        try {
            return downloadFile(filePath, fileName);
        } finally {
            if (!ConfigLoader.enableSaveTmpExportFile()) {
                FileUtils.del(filePath);
            }
        }
    }

    /**
     * 生成导入模板文件
     */
    @PostMapping("/generateTemplate")
    @ResponseBody
    public ResponseResult<String> generateTemplate(@RequestBody ExcelTemplateVO templateVO) {
        ExcelWriter writer = ExcelUtil.getWriter();
        setExcel(writer, templateVO.getColNames());

        String tmpFilePath = ConfigLoader.getDeleteTmpPath() + DateUtils.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + "_" + templateVO.getGenerateName();
        writeExcel(writer, tmpFilePath);
        return ResponseResult.ok(null, tmpFilePath);
    }

    /**
     * 导入选选择文件页面
     */
    @GetMapping("/importExcelPage")
    public String importExcelPage() {
        return "/module/common/importExcel";
    }

    /**
     * 有导入失败记录时结果页面
     */
    @GetMapping("/importResultPage")
    public String importResultPage() {
        return "/module/common/importResult";
    }

    private void setExcel(ExcelWriter writer, Map<String, ExportColumn> colNames) {
        // 设置字体
        Font headFont = writer.createFont();
        headFont.setFontHeight((short) 300);
        writer.getHeadCellStyle().setFont(headFont);
        Font rowFont = writer.createFont();
        rowFont.setFontHeight((short) 250);
        writer.getCellStyle().setFont(rowFont);

        // 只写入有列头的数据
        writer.setOnlyAlias(true);
        int columnIndex = 0;
        List<String> titles = new ArrayList<>();
        for (String key : colNames.keySet()) {
            ExportColumn exportColumn = colNames.get(key);
            writer.setColumnWidth(columnIndex, exportColumn.getWidth());
            String title = exportColumn.getTitle();
            writer.addHeaderAlias(key, title);
            if (exportColumn.isSelect()) {
                DataValidationHelper dataValidationHelper = writer.getSheet().getDataValidationHelper();
                //设置下拉框数据
                DataValidationConstraint constraint = dataValidationHelper.createExplicitListConstraint(ArrayUtil.toArray(exportColumn.getSelectArray(), String.class));
                //设置生效的起始行、终止行、起始列、终止列
                CellRangeAddressList addressList = new CellRangeAddressList(1, Integer.MAX_VALUE, columnIndex, columnIndex);
                DataValidation dataValidation = dataValidationHelper.createValidation(constraint, addressList);
                writer.addValidationData(dataValidation);
            }
            titles.add(title);
            columnIndex++;
        }
        writer.writeHeadRow(titles);
    }


    private void writeExcel(ExcelWriter writer, String tmpFilePath) {
        File tmpFile = new File(tmpFilePath);
        FileUtils.createIfNotExists(tmpFile);

        FileOutputStream out;
        try {
            out = new FileOutputStream(tmpFile);
            writer.flush(out, true);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } finally {
            IoUtil.close(writer);
        }
    }
}
