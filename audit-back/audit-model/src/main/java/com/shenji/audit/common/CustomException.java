package com.shenji.audit.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private String status;
    private String msg;

    public CustomException() {
        super();
    }

    public CustomException(String msg) {
        super(msg);
        this.status = "500";
        this.msg = msg;
    }

    public CustomException(String status, String msg) {
        super(status);
        this.status = status;
        this.msg = msg;
    }

    public CustomException(String status, String msg, Throwable cause) {
        super(status, cause);
        this.status = status;
        this.msg = msg;
    }
}
