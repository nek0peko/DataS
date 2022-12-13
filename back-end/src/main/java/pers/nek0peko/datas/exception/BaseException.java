package pers.nek0peko.datas.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * BaseException
 *
 * @author nek0peko
 * @date 2022/12/13
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String errCode;

    public BaseException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
    }

    public BaseException(String errCode, String errMessage, Throwable e) {
        super(errMessage, e);
        this.errCode = errCode;
    }

}
