package pers.nek0peko.datas.dto.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pers.nek0peko.datas.exception.ErrorCodeI;

/**
 * SystemErrorEnum
 *
 * @author nek0peko
 * @date 2023/04/03
 */
@Getter
@AllArgsConstructor
public enum SystemErrorEnum implements ErrorCodeI {

    // 数据源名重复
    S_SERVER_ERROR("S_SERVER_ERROR", "系统繁忙"),

    // 参数错误
    B_PARAMETER_ERROR("B_PARAMETER_ERROR", "请求参数错误！"),

    ;

    private final String errCode;

    private final String errMessage;

}
