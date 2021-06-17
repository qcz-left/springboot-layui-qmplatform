package com.qcz.qmplatform.module.base;

import cn.hutool.core.date.DatePattern;
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
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import org.apache.poi.ss.usermodel.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/upload")
    @ResponseBody
    public ResponseResult<Map<String, String>> upload(MultipartFile file) {
        File targetFileFolder = new File(ConfigLoader.getUploadFilePath());
        FileUtils.createDirIfNotExists(targetFileFolder);
        String fileName = DateUtils.format(new Date(), DatePattern.PURE_DATETIME_PATTERN) + "_" + file.getOriginalFilename();
        File targetFile;
        try {
            targetFile = new File(targetFileFolder.getCanonicalFile(), fileName);
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new CommonException("Failed to upload file!", e);
        }
        Map<String, String> response = new HashMap<>();
        response.put("fileName", fileName);
        response.put("filePath", FileUtils.PATH_PRE + fileName);
        return ResponseResult.ok(response);
    }

    /**
     * 文件下载
     *
     * @param filePath 文件路径
     */
    @GetMapping("/downloadFile")
    public ResponseEntity<InputStreamResource> downloadFile(String filePath) {
        FileSystemResource file = new FileSystemResource(FileUtils.getRealFilePath(filePath));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", new String(Objects.requireNonNull(file.getFilename()).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
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
    public ResponseResult<?> deleteFile(String filePath) {
        FileUtils.del(FileUtils.getRealFilePath(filePath));
        return ResponseResult.ok();
    }

    /**
     * 生成导出文件
     */
    @RequestMapping("/generateExportFile")
    @ResponseBody
    public ResponseResult<?> generateExportFile(@RequestBody ExportParamVo exportParam, HttpServletRequest request) {
        String tmpFilePath;
        try {
            ExcelWriter writer = ExcelUtil.getWriter();

            // 设置字体
            Font headFont = writer.createFont();
            headFont.setFontHeight((short) 300);
            writer.getHeadCellStyle().setFont(headFont);
            Font rowFont = writer.createFont();
            rowFont.setFontHeight((short) 250);
            writer.getCellStyle().setFont(rowFont);

            // 只写入有列头的数据
            writer.setOnlyAlias(true);
            Map<String, ExportColumn> colNames = exportParam.getColNames();
            int columnIndex = 0;
            for (String key : colNames.keySet()) {
                writer.setColumnWidth(columnIndex++, colNames.get(key).getWidth());
                writer.addHeaderAlias(key, colNames.get(key).getTitle());
            }
            String httpUrl = HttpServletUtils.getServerPath(request) + exportParam.getQueryUrl();
            HttpRequest httpRequest = HttpUtil.createGet(httpUrl);

            Map<String, Object> param = exportParam.getQueryParam();
            param.put("export", true);

            httpRequest.form(param);
            httpRequest.header(HttpHeaders.COOKIE, request.getHeader(HttpHeaders.COOKIE));
            HttpResponse httpResponse = httpRequest.execute();
            String body = httpResponse.body();

            JSONObject queryResp = JSONUtil.parseObj(body);
            List<?> rows = JSONUtil.toList(queryResp.getJSONObject("data").getJSONArray("list"), Map.class);
            // 一次性写出内容，使用默认样式
            writer.write(rows);
            tmpFilePath = ConfigLoader.getDeleteTmpPath() + DateUtils.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + "_" + exportParam.getGenerateName();
            File tmpFile = new File(tmpFilePath);
            FileUtils.createIfNotExists(tmpFile);

            FileOutputStream out = new FileOutputStream(tmpFile);
            writer.flush(out, true);
            writer.close();
        } catch (Exception e) {
            throw new CommonException("Failed to export file！", e);
        }

        return ResponseResult.ok(null, tmpFilePath);
    }

    /**
     * 导出文件下载专用请求
     *
     * @param filePath 文件路径
     * @see com.qcz.qmplatform.module.base.BaseController#downloadFile(java.lang.String)
     */
    @GetMapping("/downloadExportFile")
    public ResponseEntity<InputStreamResource> downloadExportFile(String filePath) {
        try {
            return downloadFile(filePath);
        } finally {
            if (!ConfigLoader.enableSaveTmpExportFile()) {
                FileUtils.del(filePath);
            }
        }
    }

}
