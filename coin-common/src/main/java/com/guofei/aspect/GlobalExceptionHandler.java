package com.guofei.aspect;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.guofei.model.R;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: GuoFei
 * @Date: 2021/07/05/19:50
 * @Description: 全局web异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 内部api调用的异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public R handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return R.fail(e.getErrorCode());
        }
        return R.fail(e.getMessage());
    }

    /**
     * 方法参数校验失败的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return R.fail(message);
    }

    /**
     * 对象内部使用 validate 没有检验成功的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public R handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return R.fail(message);
    }

}
