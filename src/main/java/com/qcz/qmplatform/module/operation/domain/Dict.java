package com.qcz.qmplatform.module.operation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-19
 */
@Data
@Accessors(chain = true)
@TableName("sys_dict")
public class Dict implements Serializable {

    /**
     * 字典主键id
     */
    @TableId("dict_id")
    private String dictId;

    /**
     * 字典编码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

}
