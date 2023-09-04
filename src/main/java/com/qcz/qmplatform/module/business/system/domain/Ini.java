package com.qcz.qmplatform.module.business.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统属性配置
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-26
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_ini")
public class Ini implements Serializable {

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

}
