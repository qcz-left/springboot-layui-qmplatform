package com.qcz.qmplatform.common.exception;

import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseResult<Void> errorHandleByPermission(UnauthorizedException ex) {
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
    public ResponseResult<Void> errorHandleBy500(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseResult<>(ResponseCode.INTERNAL_SERVER_ERROR, "服务器好像出现错误了，请联系管理员！", null);
    }
}
