package pers.nek0peko.datas.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SingleResponse
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Getter
@Setter
@NoArgsConstructor
public class SingleResponse<T> extends Response {

    private T data;

    public static SingleResponse buildSuccess() {
        final SingleResponse response = new SingleResponse();
        response.setSuccess(true);
        return response;
    }

    public static SingleResponse buildFailure(String errCode, String errMessage) {
        final SingleResponse response = new SingleResponse();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> SingleResponse<T> of(T data) {
        final SingleResponse<T> response = new SingleResponse();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

}
