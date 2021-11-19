package com.qcz.qmplatform.module.system.vo;

public class LoginFormVO extends PasswordVO {

    private static final long serialVersionUID = 1789788162400554173L;
    /**
     * 第三方系统
     */
    private String thirdparty;
    /**
     * 第三方系统账号id
     */
    private String thirdparyId;

    public String getThirdparty() {
        return thirdparty;
    }

    public void setThirdparty(String thirdparty) {
        this.thirdparty = thirdparty;
    }

    public String getThirdparyId() {
        return thirdparyId;
    }

    public void setThirdparyId(String thirdparyId) {
        this.thirdparyId = thirdparyId;
    }
}
