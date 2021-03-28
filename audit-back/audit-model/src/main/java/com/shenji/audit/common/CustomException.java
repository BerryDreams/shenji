package com.shenji.audit.common;

import com.shenji.audit.type.RespType;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/15 21:23
 */
@Getter
@Setter
public class CustomException extends RuntimeException{

    private String status;
    private String msg;

    public CustomException() {
        super();
    }

    public CustomException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CustomException(String status, String msg) {
        super(status);
        this.status = status;
        this.msg = msg;
    }

    public CustomException(RespType respType) {
        super(respType.getStatus());
        this.status = respType.getStatus();
        this.msg = respType.getMsg();
    }

    public CustomException(String status, String msg, Throwable cause) {
        super(status, cause);
        this.status = status;
        this.msg = msg;
    }


}
