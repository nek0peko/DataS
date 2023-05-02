package pers.nek0peko.datas.service;

/**
 * 单点登录服务
 *
 * @author nek0peko
 * @date 2023/05/02
 */
public interface SsoServiceI {

    /**
     * 请求Token
     *
     * @param code code
     * @param redirectUrl 重定向url
     * @return token
     */
    String requestToken(String code, String redirectUrl);

}
