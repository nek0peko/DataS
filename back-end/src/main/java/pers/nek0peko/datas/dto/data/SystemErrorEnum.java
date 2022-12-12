package pers.nek0peko.datas.dto.data;

import pers.nek0peko.datas.exception.ErrorCodeI;

/**
 * SystemErrorEnum
 *
 * @author nek0peko
 * @date 2022/12/12
 */
public enum SystemErrorEnum implements ErrorCodeI {

    // 数据源名重复
    S_SERVER_ERROR("S_SERVER_ERROR", "系统繁忙"),

    // 参数错误
    S_PARAMETER_ERROR("S_PARAMETER_ERROR", "参数错误！");

    private final String errCode;

    private final String errMessage;

    SystemErrorEnum(String errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

}
