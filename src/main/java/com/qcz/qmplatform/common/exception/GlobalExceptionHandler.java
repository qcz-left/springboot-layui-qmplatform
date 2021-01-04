package com.qcz.qmplatform.common.exception;

import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String PATH_PREFIX = "/error/";

    /**
     * 服务器异常500
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<?> errorHandleBy500(Exception ex) {
        ex.printStackTrace();
        return new ResponseResult<>(ResponseCode.INTERNAL_SERVER_ERROR, "服务器好像出现错误了，请联系管理员！", null);
    }

    /**
     * 没有资源权限
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseResult<?> errorHandleByPermission(UnauthorizedException ex) {
        ex.printStackTrace();
        return new ResponseResult<>(ResponseCode.PERMISSION_DENIED, "没有该资源权限！", null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String errorHandleBy404(NoHandlerFoundException ex) {
        return PATH_PREFIX + "404";
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(CommonException.class)
    public ResponseResult<?> errorHandleByCommon(CommonException ex) {
        ex.printStackTrace();
        return ResponseResult.error(ex.getMessage());
    }

    /**
     * 会话过期
     */
    @ExceptionHandler(InvalidSessionException.class)
    public String errorHandleByCommon(InvalidSessionException ex) {
        LOGGER.error("The session has expired and will redirect to the login page", ex);
        return "redirect:/loginPage";
    }

}
