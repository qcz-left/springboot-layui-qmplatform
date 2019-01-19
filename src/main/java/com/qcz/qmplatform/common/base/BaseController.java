package com.qcz.qmplatform.common.base;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.common.utils.StringUtils;

/**
 * 基础控制器，所有Controller继承它可以使用通用方法
 * @author changzhongq
 * @time 2018年11月17日 下午7:58:25
 */
public class BaseController<T, S extends BaseService<T, ?>> {

    protected Logger logger = null;

    @Autowired
    protected S service;

    /**
     * 构造方法，同时为继承的类赋Class值
     */
    protected BaseController() {
        logger = LoggerFactory.getLogger(getClass());
    }

    /**
     * 根据ID查询单条记录
     * @param id 数据ID
     */
    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    @ResponseBody
    public T data(@PathVariable("id") Object id) {
        return service.find(id);
    }

    /**
     * 单条记录删除
     * @param id 数据ID
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseResult delete(@PathVariable("id") Object id) {
        return service.delete(id);
    }

    /**
     * 新增或修改操作
     * @param data 需要保存的数据
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult insertOrUpdate(T data) {
        return ResponseResult.ok("保存数据成功！", service.save(data));
    }

    /**
     * 通用导出方法
     * @param request  请求信息
     * @param response 响应信息
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    @RequestMapping("/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response) {
        String param = (String) request.getParameter("param");
        Map<String, Object> paramMap = (Map<String, Object>) JSONUtils.parse(StringUtils.isEmpty(param) ? "{}" : param);
        // 创建HSSFWorkbook
        try {
            HSSFWorkbook wb = service.getHSSFWorkbook(paramMap);
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode((String) paramMap.get("fileName"), "UTF-8"));
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送响应流方法
     * @param response 响应信息
     * @param fileName 文件名
     */
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
    }
}
