package pers.nek0peko.datas.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pers.nek0peko.datas.dto.BaseDTO;

/**
 * Response
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Getter
@Setter
@NoArgsConstructor
public class Response extends BaseDTO {

    private boolean success;

    private String errCode;

    private String errMessage;

    public String toString() {
        return "Response: [success=" + this.success + ", errCode=" + this.errCode + ", errMessage=" + this.errMessage + "]";
    }

    public static Response buildSuccess() {
        final Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public static Response buildFailure(String errCode, String errMessage) {
        final Response response = new Response();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

}
