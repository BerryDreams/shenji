package com.shenji.audit.exception;

import com.shenji.audit.common.CustomException;
import com.shenji.audit.common.RespBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/16 7:18
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public RespBean exceptionHandler(CustomException customException) {
        return RespBean.build(customException);
    }
}
