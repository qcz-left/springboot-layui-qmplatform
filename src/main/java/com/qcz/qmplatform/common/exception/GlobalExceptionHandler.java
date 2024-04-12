package com.qcz.qmplatform.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(dateFormat, true));

        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat2, true));
        binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(dateFormat2, true));
    }

    /**
     * 业务异常
     */
    @ExceptionHandler({BusinessException.class, CommonException.class})
    @ResponseBody
    public ResponseResult<Void> errorHandleByBusiness(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseResult.error(ex.getMessage());
    }

    /**
     * 数据校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult<Void> errorHandleByValid(MethodArgumentNotValidException ex) {
        LOGGER.error(ex.getMessage(), ex);
        StringBuilder error = new StringBuilder();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        int errorSize = fieldErrors.size();
        if (errorSize == 1) {
            FieldError fieldError = fieldErrors.get(0);
            error.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage());
        } else {
            for (int i = 0; i < errorSize; i++) {
                FieldError fieldError = fieldErrors.get(i);
                error.append(i + 1).append(". ").append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage()).append(".</br>");
            }
        }
        return ResponseResult.error(error.toString());
    }

    /**
     * 没有资源权限
     */
    @ExceptionHandler({NotPermissionException.class, NotRoleException.class})
    @ResponseBody
    public ResponseResult<Void> errorHandleByPermission(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseResult<>(ResponseCode.PERMISSION_DENIED, "没有该资源权限！", null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String errorHandleBy404() {
        return "/error/404";
    }

    /**
     * 会话过期
     */
    @ExceptionHandler(NotLoginException.class)
    public String sessionTimeout(NotLoginException ex) {
        LOGGER.error("The session has expired and will redirect to the login page", ex);
        return "redirect:/loginPage";
    }

    /**
     * 服务器异常500
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<Void> errorHandleBy500(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseResult<>(ResponseCode.INTERNAL_SERVER_ERROR, "服务器好像出现错误了，请联系管理员！", null);
    }
}
