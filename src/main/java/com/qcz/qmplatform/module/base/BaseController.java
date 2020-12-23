package com.qcz.qmplatform.module.base;

import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.FileUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", new String(file.getFilename().getBytes("UTF-8"), "ISO-8859-1")));
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

}
