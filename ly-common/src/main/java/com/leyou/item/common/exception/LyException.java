package com.leyou.item.common.exception;

import com.leyou.item.common.enums.ExceptionEnum;
import lombok.Getter;

@Getter
public class LyException extends RuntimeException {
    private Integer status;

    private ExceptionEnum exceptionEnum;

    public LyException() {
        super();
    }

    public LyException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.exceptionEnum = exceptionEnum;
        this.status = exceptionEnum.getStatus();
    }

    public LyException(ExceptionEnum exceptionEnum, Throwable throwable) {
        super(exceptionEnum.getMsg(), throwable);
        this.exceptionEnum = exceptionEnum;
        this.status = exceptionEnum.getStatus();
    }
}
