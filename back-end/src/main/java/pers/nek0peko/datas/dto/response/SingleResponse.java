package pers.nek0peko.datas.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SingleResponse
 *
 * @author nek0peko
 * @date 2022/12/13
 */
@Getter
@Setter
@NoArgsConstructor
public class SingleResponse<T> extends Response {

    private T data;

    public static SingleResponse buildSuccess() {
        SingleResponse response = new SingleResponse();
        response.setSuccess(true);
        return response;
    }

    public static SingleResponse buildFailure(String errCode, String errMessage) {
        SingleResponse response = new SingleResponse();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> SingleResponse<T> of(T data) {
        SingleResponse<T> response = new SingleResponse();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

}
