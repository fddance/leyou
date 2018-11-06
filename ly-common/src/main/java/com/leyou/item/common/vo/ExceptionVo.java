package com.leyou.item.common.vo;

import com.leyou.item.common.enums.ExceptionEnum;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class ExceptionVo {

    private Integer status;
    private String msg;
    private String time;

    public ExceptionVo(ExceptionEnum exceptionEnum) {
        this.status = exceptionEnum.getStatus();
        this.msg = exceptionEnum.getMsg();
        this.time = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }

}
