package com.qcz.qmplatform.module.business.operation.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 字典属性
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-19
 */
@Data
@Accessors(chain = true)
@TableName("sys_dict_attr")
public class DictAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性名称
     */
    @TableId("attr_id")
    private String attrId;

    /**
     * 属性名称
     */
    @TableField("attr_name")
    private String attrName;

    /**
     * 属性值
     */
    @TableField("attr_value")
    private String attrValue;

    /**
     * 属性描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 对应sys_dict的dict_id
     */
    @TableField("dict_id")
    private String dictId;

}
