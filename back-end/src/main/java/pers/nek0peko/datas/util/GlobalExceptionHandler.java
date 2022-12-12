package pers.nek0peko.datas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.nek0peko.datas.dto.data.SystemErrorEnum;
import pers.nek0peko.datas.dto.response.Response;
import pers.nek0peko.datas.exception.BaseException;
import pers.nek0peko.datas.exception.BusinessException;

/**
 * GlobalExceptionHandler
 *
 * @author nek0peko
 * @date 2022/12/12
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response parameterExceptionHandler(MethodArgumentNotValidException ex) {
        String msg = SystemErrorEnum.S_PARAMETER_ERROR.getErrMessage();
        BindingResult exceptions = ex.getBindingResult();
        if (exceptions.hasErrors()) {
            msg = exceptions.getAllErrors().stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage).orElse(SystemErrorEnum.S_PARAMETER_ERROR.getErrMessage());
        }

        return Response.buildFailure(SystemErrorEnum.S_PARAMETER_ERROR.getErrCode(), msg);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BusinessException.class})
    public Response handleException(BusinessException ex) {
        return Response.buildFailure(ex.getErrCode(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BaseException.class})
    public Response handleException(BaseException ex) {
        log.warn(ex.getMessage(), ex);
        return Response.buildFailure(ex.getErrCode(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public Response handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Response.buildFailure(SystemErrorEnum.S_SERVER_ERROR.getErrCode(), ex.getMessage());
    }

}
