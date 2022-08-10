package com.qcz.qmplatform.common.exception;

import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
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
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public ResponseResult<?> errorHandleByCommon(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseResult.error(ex.getMessage());
    }

    /**
     * 数据校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult<?> errorHandleByValid(MethodArgumentNotValidException ex) {
        LOGGER.error(ex.getMessage(), ex);
        StringBuilder error = new StringBuilder();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        int errorSize = fieldErrors.size();
        if (errorSize == 1) {
            error.append(fieldErrors.get(0).getDefaultMessage());
        } else {
            for (int i = 0; i < errorSize; i++) {
                error.append(i + 1).append(". ").append(fieldErrors.get(i).getDefaultMessage()).append(".</br>");
            }
        }
        return ResponseResult.error(error.toString());
    }

    /**
     * 没有资源权限
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseResult<?> errorHandleByPermission(UnauthorizedException ex) {
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
    @ExceptionHandler(InvalidSessionException.class)
    public String sessionTimeout(InvalidSessionException ex) {
        LOGGER.error("The session has expired and will redirect to the login page", ex);
        return "redirect:/loginPage";
    }

    /**
     * 服务器异常500
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<?> errorHandleBy500(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseResult<>(ResponseCode.INTERNAL_SERVER_ERROR, "服务器好像出现错误了，请联系管理员！", null);
    }
}
