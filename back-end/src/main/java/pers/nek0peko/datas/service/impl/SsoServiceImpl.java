package pers.nek0peko.datas.service.impl;

import org.springframework.stereotype.Service;
import pers.nek0peko.datas.common.constant.AuthConstants;
import pers.nek0peko.datas.common.constant.Constants;
import pers.nek0peko.datas.service.SsoServiceI;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 单点登录服务
 *
 * @author nek0peko
 * @date 2023/05/02
 */
@Service
public class SsoServiceImpl implements SsoServiceI {

    @Override
    public String requestToken(String code, String redirectUrl) {
        final Map<String, String> map = new HashMap<>(Constants.SIXTEEN);
        map.put(AuthConstants.GRANT_TYPE_KEY, AuthConstants.GRANT_TYPE);
        map.put(AuthConstants.CLIENT_ID_KEY, AuthConstants.CLIENT_ID);
        map.put(AuthConstants.CODE_KEY, code);
        map.put(AuthConstants.REDIRECT_URI_KEY, redirectUrl);

        final String basicAuth = Base64.getUrlEncoder().encodeToString(
                (AuthConstants.CLIENT_ID + ":" + AuthConstants.CLIENT_SECRET).getBytes());

        // TODO: Post token
        final Map responseMap = new HashMap<>(Constants.SIXTEEN);
        // TODO: Response->Map
        return (String) responseMap.get(AuthConstants.ACCESS_TOKEN_KEY);
    }

}
