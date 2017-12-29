package com.firstWeb.handler;

import com.firstWeb.common.ResultEntity;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.exception.CommonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ErrorHandler.class);

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> errorHandle(HttpServletRequest request, final Exception exception) {
        String code = null;
        String message = null;
        if (exception instanceof CommonException) {
            code = ((CommonException) exception).getErrorCode();
            message = ((CommonException) exception).getErrorMessage();
        } else {
            code = ResultCode.FAIL;
            message = exception.toString();
        }
        LOGGER.error("error --------------------------------------");
        LOGGER.error("Error Code: " + code + "----------------------------------");
        LOGGER.error("Error message: " + message);
        LOGGER.error("error occurred--------------------------------------");
        ResultEntity<String> result = new ResultEntity<>();
        result.setCode(code);
        result.setValue(message);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
