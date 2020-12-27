package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 系统属性配置
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-26
 */
@TableName("sys_ini")
public class Ini implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性组
     */
    @TableField("section")
    private String section;

    /**
     * 属性名
     */
    @TableField("property")
    private String property;

    /**
     * 属性值
     */
    @TableField("value")
    private String value;

    public Ini(){

    }

    public Ini(String section, String property, String value) {
        this.section = section;
        this.property = property;
        this.value = value;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Ini{" +
                "section=" + section +
                ", property=" + property +
                ", value=" + value +
                "}";
    }
}
