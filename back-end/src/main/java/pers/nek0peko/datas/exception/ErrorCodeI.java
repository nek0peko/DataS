package pers.nek0peko.datas.exception;

/**
 * ErrorCodeI
 *
 * @author nek0peko
 * @date 2022/12/12
 */
public interface ErrorCodeI {

    /**
     * 获取错误码
     * @return 错误码
     */
    String getErrCode();

    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getErrMessage();

}
