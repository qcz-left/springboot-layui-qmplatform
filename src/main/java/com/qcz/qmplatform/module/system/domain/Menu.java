package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@TableName("sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("menu_id")
    private String menuId;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 排序
     */
    @TableField("iorder")
    private Integer iorder;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 访问路径
     */
    @TableField("link_url")
    private String linkUrl;

    /**
     * 是否显示：0 不显示；1 显示
     */
    @TableField("display")
    private int display;

    /**
     * 父菜单id
     */
    @TableField(value = "parent_id", fill = FieldFill.INSERT_UPDATE)
    private String parentId;

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getIorder() {
        return iorder;
    }

    public void setIorder(Integer iorder) {
        this.iorder = iorder;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId='" + menuId + '\'' +
                ", menuName='" + menuName + '\'' +
                ", iorder=" + iorder +
                ", code='" + code + '\'' +
                ", icon='" + icon + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", display='" + display + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
