package com.shenji.audit.result;

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

    public CustomException(StatusType statusType) {
        super(statusType.getStatus());
        this.status = statusType.getStatus();
        this.msg = statusType.getMsg();
    }

    public CustomException(String status, String msg, Throwable cause) {
        super(status, cause);
        this.status = status;
        this.msg = msg;
    }


}
