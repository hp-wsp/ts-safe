package com.ts.server.safe.controller.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ts.server.safe.BaseException;
import com.ts.server.safe.controller.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局Exception拦截处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ControllerAdvice
public class GlobalExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    @SuppressWarnings("unused")
    public ResultVo<String> handleBaseException(BaseException e){
        LOGGER.error("Controller error code={}, message={}", e.getCode(), e.getMessage());
        return ResultVo.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({ConversionFailedException.class, InvalidFormatException.class})
    @ResponseBody
    @SuppressWarnings("unused")
    public ResultVo<String> handlerConversionFailedException(ConversionFailedException e){
        LOGGER.error("Parameter conversion fail, error= {}", e.getMessage());
        return ResultVo.error(100, "参数转化失败");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @SuppressWarnings("unused")
    public ResultVo<String> handlerValidException(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        String[] messages = result.getFieldErrors().stream()
                .map(f -> f.getField() + "=" + f.getDefaultMessage()).toArray(String[]::new);
        return ResultVo.error(101, messages);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @SuppressWarnings("unused")
    public ResultVo<String> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return ResultVo.error(102, "请求路径或方法错误");
    }
}
