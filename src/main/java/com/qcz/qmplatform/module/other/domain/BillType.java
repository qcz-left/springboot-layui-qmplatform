package com.qcz.qmplatform.module.other.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 账单类型
 * </p>
 *
 * @author quchangzhong
 * @since 2022-08-12
 */
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "BillType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
