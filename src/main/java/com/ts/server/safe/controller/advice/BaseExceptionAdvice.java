package com.ts.server.safe.controller.advice;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.controller.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * BaseException拦截处理
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ControllerAdvice(annotations = RestController.class)
public class BaseExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionAdvice.class);

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    @SuppressWarnings("unused")
    public ResultVo<String> handleBaseException(BaseException e){
        LOGGER.error("Controller error code={}, message={}", e.getCode(), e.getMessage());
        return ResultVo.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseBody
    @SuppressWarnings("unused")
    public ResultVo<String> handlerConversionFailedException(ConversionFailedException e){
        LOGGER.error("Parameter conversion fail, error= {}", e.getMessage());
        return ResultVo.error(100, "参数错误");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultVo<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        LOGGER.error("Parameter valid fail, error={}", e.getMessage());
        BindingResult result = e.getBindingResult();
        String[] messages = result.getFieldErrors().stream()
                .map(f -> f.getField() + "=" + f.getDefaultMessage()).toArray(String[]::new);
        return ResultVo.error(1001, messages);
    }
}
