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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
    public void export(ExportParamVo exportParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        // 一次性写出内容，使用默认样式
        writer.write((List<?>) ((Map<?, ?>) queryResp.get("data")).get("list"), true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", new String(exportParam.getGenerateName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));

        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }

}
