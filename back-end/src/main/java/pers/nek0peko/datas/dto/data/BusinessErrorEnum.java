package pers.nek0peko.datas.dto.data;

import pers.nek0peko.datas.exception.ErrorCodeI;

/**
 * BusinessErrorEnum
 *
 * @author nek0peko
 * @date 2023/03/22
 */
public enum BusinessErrorEnum implements ErrorCodeI {

    // 图表配置JSON错误
    B_CHART_INVALID_CONFIG("B_CHART_INVALID_CONFIG", "图表配置校验失败！"),
    // 图表列类型错误
    B_CHART_COLUMN_TYPE_ERROR("B_CHART_COLUMN_TYPE_ERROR", "图表列类型错误！"),
    // 图表类型不支持
    B_CHART_UNSUPPORTED("B_CHART_UNSUPPORTED", "不支持该图表类型！"),
    // 数据源名重复
    B_DATASOURCE_EXISTS("B_DATASOURCE_EXISTS", "数据源已存在，请重新输入！"),
    // 数据源ID不存在
    B_DATASOURCE_NOT_EXISTS("B_DATASOURCE_NOT_EXISTS", "数据源不存在！"),
    // 数据源配置JSON错误
    B_DATASOURCE_INVALID_CONFIG("B_DATASOURCE_INVALID_CONFIG", "数据源配置校验失败！"),
    // 数据源类型不支持
    B_DATASOURCE_UNSUPPORTED("B_DATASOURCE_UNSUPPORTED", "不支持该数据源类型！"),
    // 数据源连接失败
    B_DATASOURCE_FAILED("B_DATASOURCE_FAILED", "数据源连接失败！"),
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
