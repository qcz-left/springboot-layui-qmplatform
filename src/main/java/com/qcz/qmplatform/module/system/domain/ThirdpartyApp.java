package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 第三方应用参数配置信息
 * </p>
 *
 * @author quchangzhong
 * @since 2022-01-26
 */
@TableName("sys_thirdparty_app")
public class ThirdpartyApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableField("id")
    private String id;

    /**
     * 第三方系统名称
     */
    @TableField("name")
    private String name;

    /**
     * 系统应用id
     */
    @TableField("app_key")
    private String appKey;

    /**
     * 应用秘钥
     */
    @TableField("app_secret")
    private String appSecret;

    /**
     * 描述
     */
    @TableField("remark")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ThirdpartyApp{" +
                "id=" + id +
                ", name=" + name +
                ", appKey=" + appKey +
                ", appSecret=" + appSecret +
                ", remark=" + remark +
                "}";
    }
}
