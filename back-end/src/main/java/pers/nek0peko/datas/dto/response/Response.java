package pers.nek0peko.datas.dto.response;

import pers.nek0peko.datas.dto.BaseDTO;

/**
 * Response
 *
 * @author nek0peko
 * @date 2022/12/12
 */
public class Response extends BaseDTO {

    private boolean success;

    private String errCode;

    private String errMessage;

    public Response() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String toString() {
        return "Response: [success=" + this.success + ", errCode=" + this.errCode + ", errMessage=" + this.errMessage + "]";
    }

    public static Response buildSuccess() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public static Response buildFailure(String errCode, String errMessage) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

}
