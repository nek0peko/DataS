package pers.nek0peko.datas.dto.data;

import pers.nek0peko.datas.exception.ErrorCodeI;

/**
 * BusinessErrorEnum
 *
 * @author nek0peko
 * @date 2022/12/13
 */
public enum BusinessErrorEnum implements ErrorCodeI {

    // 数据源名重复
    B_DATASOURCE_EXISTS("B_DATASOURCE_EXISTS", "数据源已存在，请重新输入！"),
    // 数据源ID不存在
    B_DATASOURCE_NOT_EXISTS("B_DATASOURCE_NOT_EXISTS", "数据源不存在！"),
    // 数据源配置JSON错误
    B_DATASOURCE_INVALID_CONFIG("B_DATASOURCE_INVALID_CONFIG", "数据源配置校验失败！"),
    B_DATASOURCE_PARAMETER_ERROR("B_PARAMETER_ERROR", "数据源参数错误！"),
    // 数据源类型不支持
    B_DATASOURCE_UNSUPPORTED("B_DATASOURCE_UNSUPPORTED", "不支持该数据源类型！"),
    ;

    private final String errCode;

    private final String errMessage;

    BusinessErrorEnum(String errCode, String errMessage) {
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
