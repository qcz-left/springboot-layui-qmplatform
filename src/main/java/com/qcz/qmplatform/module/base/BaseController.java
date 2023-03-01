package com.qcz.qmplatform.module.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.qcz.qmplatform.common.anno.ExcelField;
import com.qcz.qmplatform.common.bean.ExcelRow;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.exception.CommonException;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

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
                tExcelRow.setRowIndex((rowIndex + 1));
                Map<String, Object> data = new HashMap<>();
                for (int i = 0; i < row.size(); i++) {
                    data.put(fieldIndex.get(i), row.get(i));
                }
                tExcelRow.setRow(BeanUtil.toBeanIgnoreError(data, beanClass));
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
