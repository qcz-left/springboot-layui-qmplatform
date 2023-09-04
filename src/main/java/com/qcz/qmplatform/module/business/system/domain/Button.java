package com.qcz.qmplatform.module.business.system.domain;

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
@TableName("sys_button")
public class Button implements Serializable {

    /**
     * 按钮id
     */
    @TableId("button_id")
    private String buttonId;

    /**
     * 按钮名称
     */
    @TableField("button_name")
    private String buttonName;

    /**
     * 所属菜单id
     */
    @TableField("menu_id")
    private String menuId;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 排序
     */
    @TableField("iorder")
    private Integer iorder;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

}
