package com.qcz.qmplatform.module.base;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController {

    protected static Logger logger = null;

    public BaseController() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseResult<Map<String, String>> upload(MultipartFile file) throws IOException {
        File targetFileFolder = new File(ConfigLoader.getUploadFilePath());
        if (!targetFileFolder.exists()) {
            targetFileFolder.mkdirs();
        }
        String fileName = DateUtils.format(new Date(), "yyyyMMddhhmmss") + "_" + file.getOriginalFilename();
        File targetFile = new File(targetFileFolder.getCanonicalFile(), fileName);
        file.transferTo(targetFile);
        Map<String, String> response = new HashMap<>();
        response.put("fileName", fileName);
        response.put("filePath", "/file/" + fileName);
        return ResponseResult.ok(response);
    }

    /**
     * 文件下载
     *
     * @param filePath 文件路径
     */
    @GetMapping("/downloadFile")
    public ResponseEntity<InputStreamResource> downloadFile(String filePath) throws IOException {
        FileSystemResource file = new FileSystemResource(FileUtils.getRealFilePath(filePath));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", new String(file.getFilename().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
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
     * 导出
     */
    @RequestMapping("/export")
    @ResponseBody
    public ResponseResult<?> export(@RequestBody ExportParamVo exportParam, HttpServletRequest request) throws IOException {
        String httpUrl = HttpServletUtils.getLocalIpAddress() + ":" + request.getServerPort() + exportParam.getQueryUrl();
        HttpRequest httpRequest = HttpUtil.createGet(httpUrl);

        Map<String, Object> param = exportParam.getQueryParam();
        param.put("export", true);

        httpRequest.form(param);
        httpRequest.header(HttpHeaders.COOKIE, request.getHeader(HttpHeaders.COOKIE));
        HttpResponse httpResponse = httpRequest.execute();
        String body = httpResponse.body();
        Map<String, Object> queryResp = JSONUtil.toBean(body, Map.class);

        ExcelWriter writer = ExcelUtil.getWriter();
        // 只写入有列头的数据
        writer.setOnlyAlias(true);
        Map<String, String> colNames = exportParam.getColNames();
        for (String key : colNames.keySet()) {
            writer.addHeaderAlias(key, colNames.get(key));
        }
        List<Map<String, Object>> rows = (List<Map<String, Object>>) ((Map<String, Object>) queryResp.get("data")).get("list");
        exportFormat(rows);
        // 一次性写出内容，使用默认样式
        writer.write(rows);

        String tmpFilePath = ConfigLoader.getDeleteTmpPath() + StringUtils.uuid() + exportParam.getGenerateName();
        File tmpFile = new File(tmpFilePath);
        FileUtils.createIfNotExists(tmpFile);

        FileOutputStream out = new FileOutputStream(tmpFile);
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);

        return ResponseResult.ok(null, tmpFilePath);
    }

    /**
     * 格式化导出数据
     *
     * @param rows 导出数据
     */
    protected void exportFormat(List<Map<String, Object>> rows) {

    }

}
