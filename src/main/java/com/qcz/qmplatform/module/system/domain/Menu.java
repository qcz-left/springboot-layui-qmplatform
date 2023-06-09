package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
@TableName("sys_menu")
public class Menu implements Serializable {

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

}
