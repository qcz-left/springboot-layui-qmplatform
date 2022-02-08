package com.qcz.qmplatform.module.system.realm;

import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.module.system.assist.LoginType;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class CustomCredentialsMatch extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //增加免密登录功能，使用自定义token
        CustomToken customToken = (CustomToken) token;

        //免密登录,不验证密码
        if (LoginType.NO_PASSWORD.equals(customToken.getType())) {
            return true;
        }

        return SecureUtils.accountCheck(new String(customToken.getPassword()), info.getCredentials().toString());
    }
}
