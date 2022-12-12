package pers.nek0peko.datas.exception;

/**
 * BusinessException
 *
 * @author nek0peko
 * @date 2022/12/12
 */
public class BusinessException extends BaseException {

    private final ErrorCodeI errorCode;

    public BusinessException(ErrorCodeI errorCode) {
        super(errorCode.getErrCode(), errorCode.getErrMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCodeI errorCode, Throwable e) {
        super(errorCode.getErrCode(), errorCode.getErrMessage(), e);
        this.errorCode = errorCode;
    }

    public ErrorCodeI getErrorCode() {
        return this.errorCode;
    }

}
