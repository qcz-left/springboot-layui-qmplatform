package com.qcz.qmplatform.module.base;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.qcz.qmplatform.common.bean.PageResult;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        String uploadFilePath = ConfigLoader.getUploadFilePath();
        logger.info("file storage path: {}", uploadFilePath);
        FileUtils.del(FileUtils.getRealFilePath(filePath));
        return ResponseResult.ok();
    }

    /**
     * 导出
     */
    @PostMapping("/export")
    @ResponseBody
    public ResponseResult<?> export(@RequestBody ExportParamVo exportParamVo, HttpServletRequest request) {
        String httpUrl = HttpServletUtils.getLocalIpAddress() + ":" + request.getServerPort() + exportParamVo.getQueryUrl();
        HttpRequest httpRequest = HttpUtil.createGet(httpUrl);
        httpRequest.form("export", true);
        httpRequest.header(HttpHeaders.COOKIE, request.getHeader(HttpHeaders.COOKIE));
        HttpResponse response = httpRequest.execute();
        String body = response.body();
        ResponseResult queryResp = JSONUtil.toBean(body, ResponseResult.class);
        BigExcelWriter writer = ExcelUtil.getBigWriter(exportParamVo.getGenerateName());
        // 一次性写出内容，使用默认样式
        writer.write(((PageResult) queryResp.getData()).getList());
        // 关闭writer，释放内存
        writer.close();
        return ResponseResult.ok();
    }

}
