package pers.nek0peko.datas.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.nek0peko.datas.common.constant.AuthConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * ServletUtils
 *
 * @author nek0peko
 * @date 2023/05/02
 */
public class ServletUtils {

    public static HttpServletRequest request() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.requireNonNull(servletRequestAttributes).getRequest();
    }

    public static HttpServletResponse response() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.requireNonNull(servletRequestAttributes).getResponse();
    }

    public static void setToken(String token) {
        HttpServletResponse httpServletResponse = response();
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Authorization");
        httpServletResponse.setHeader(AuthConstants.TOKEN_KEY, token);
    }

    public static String getToken() {
        return request().getHeader(AuthConstants.TOKEN_KEY);
    }

}
