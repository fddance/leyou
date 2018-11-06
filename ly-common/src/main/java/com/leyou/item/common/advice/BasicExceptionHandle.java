package com.leyou.item.common.advice;

import com.leyou.item.common.exception.LyException;
import com.leyou.item.common.vo.ExceptionVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BasicExceptionHandle {

    @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionVo> handlException(LyException e) {
        ExceptionVo exceptionVo = new ExceptionVo(e.getExceptionEnum());
        return ResponseEntity.status(e.getStatus()).body(exceptionVo);
    }
}
