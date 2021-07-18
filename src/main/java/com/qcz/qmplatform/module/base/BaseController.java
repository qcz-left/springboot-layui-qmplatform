package com.qcz.qmplatform.module.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.qcz.qmplatform.common.anno.ExcelField;
import com.qcz.qmplatform.common.bean.ExcelRow;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.exception.CommonException;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import org.apache.poi.ss.usermodel.Font;
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
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String PREFIX = "/module/common/";

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
    public ResponseEntity<InputStreamResource> downloadFile(String filePath, String fileName) {
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
    public ResponseResult<?> deleteFile(String filePath) {
        FileUtils.del(FileUtils.getRealFilePath(filePath));
        return ResponseResult.ok();
    }

    /**
     * 生成导出文件
     */
    @RequestMapping("/generateExportFile")
    @ResponseBody
    public ResponseResult<?> generateExportFile(@RequestBody ExportParamVO exportParam, HttpServletRequest request) {
        String tmpFilePath;
        try {
            ExcelWriter writer = ExcelUtil.getWriter();

            setExcel(writer, exportParam.getColNames());
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
            writer.setColumnWidth(columnIndex++, colNames.get(key).getWidth());
            String title = colNames.get(key).getTitle();
            writer.addHeaderAlias(key, title);
            titles.add(title);
        }
        writer.writeHeadRow(titles);
    }

    /**
     * 导出文件下载专用请求
     *
     * @param filePath 文件路径
     * @see com.qcz.qmplatform.module.base.BaseController#downloadFile(java.lang.String, java.lang.String)
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
    public ResponseResult<?> generateTemplate(@RequestBody ExcelTemplateVO templateVO) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter();
        setExcel(writer, templateVO.getColNames());

        String tmpFilePath = ConfigLoader.getDeleteTmpPath() + DateUtils.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + "_" + templateVO.getGenerateName();
        File tmpFile = new File(tmpFilePath);
        FileUtils.createIfNotExists(tmpFile);

        FileOutputStream out = new FileOutputStream(tmpFile);
        writer.flush(out, true);
        writer.close();
        return ResponseResult.ok(null, tmpFilePath);
    }

    /**
     * 导入选选择文件页面
     */
    @GetMapping("/importExcelPage")
    public String importExcelPage() {
        return PREFIX + "importExcel";
    }

    /**
     * 有导入失败记录时结果页面
     */
    @GetMapping("/importResultPage")
    public String importResultPage() {
        return PREFIX + "importResult";
    }

    /**
     * 解析导入的Excel文件数据
     *
     * @param inputStream 文件输入流
     * @param titleIndex  标题行所在行数
     * @param beanClass   数据实体类类型
     * @return 解析后的数据列表
     */
    protected static <T> List<ExcelRow<T>> getExcelData(InputStream inputStream, int titleIndex, Class<T> beanClass) {
        List<ExcelRow<T>> rows = new ArrayList<>();
        Map<String, String> excelFieldNameMap = new HashMap<>();
        Field[] fields = ReflectUtil.getFields(beanClass);
        for (Field field : fields) {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if (excelField != null) {
                excelFieldNameMap.put(excelField.value(), field.getName());
            }
        }
        Map<Integer, String> fieldIndex = new HashMap<>();

        ExcelUtil.readBySax(inputStream, 0, (sheetIndex, rowIndex, row) -> {
            if (rowIndex < titleIndex) {
                return;
            }
            if (rowIndex == titleIndex) {
                // 列表标题
                for (int i = 0; i < row.size(); i++) {
                    fieldIndex.put(i, excelFieldNameMap.get(row.get(i).toString()));
                }
            } else {
                ExcelRow<T> tExcelRow = new ExcelRow<>();
                tExcelRow.setRowIndex(rowIndex + 1);
                Map<String, Object> data = new HashMap<>();
                for (int i = 0; i < row.size(); i++) {
                    data.put(fieldIndex.get(i), row.get(i));
                }
                tExcelRow.setRow(BeanUtil.mapToBean(data, beanClass, true));
                rows.add(tExcelRow);
            }
        });
        return rows;
    }

    protected <T> List<ExcelRow<T>> getExcelData(MultipartFile file, int titleIndex, Class<T> beanClass) {
        try {
            return getExcelData(file.getInputStream(), titleIndex, beanClass);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

}
