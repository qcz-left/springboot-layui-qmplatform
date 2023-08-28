package com.qcz.qmplatform.module.system.realm;

import com.qcz.qmplatform.module.system.domain.assist.LoginType;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义token 继承UsernamePasswordToken,
 * 账号密码登陆（password） 和 免密登陆（no_password）
 */
public class CustomToken extends UsernamePasswordToken {
    private static final long serialVersionUID = -2564928913725078138L;

    private LoginType type;


    public CustomToken() {
        super();
    }


    public CustomToken(String username, String password, LoginType type, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
        this.type = type;
    }

    /**
     * 免密登录
     */
    public CustomToken(String username) {
        super(username, "", false, null);
        this.type = LoginType.NO_PASSWORD;
    }

    /**
     * 账号密码登录
     */
    public CustomToken(String username, String password) {
        super(username, password, false, null);
        this.type = LoginType.PASSWORD;
    }

    public LoginType getType() {
        return type;
    }


    public void setType(LoginType type) {
        this.type = type;
    }
}
