package com.qcz.qmplatform.module.business.other.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 账单类型
 * </p>
 *
 * @author quchangzhong
 * @since 2022-08-12
 */
@Data
@Accessors(chain = true)
@TableName("tbl_bill_type")
public class BillType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账单类型id
     */
    @TableId("id")
    private String id;

    /**
     * 账单类型名称
     */
    @TableField("name")
    private String name;

    /**
     * 上级账单类型id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
