package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 *
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@TableName("sys_organization")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织机构id
     */
    @TableId("organization_id")
    private String organizationId;

    /**
     * 组织机构名称
     */
    @TableField("organization_name")
    private String organizationName;

    /**
     * 组织机构编码
     */
    @TableField("organization_code")
    private String organizationCode;

    /**
     * 父id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 机构描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Timestamp createTime;

    /**
     * 排序
     */
    @TableField("iorder")
    private Integer iorder;


    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getIorder() {
        return iorder;
    }

    public void setIorder(Integer iorder) {
        this.iorder = iorder;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "organizationId=" + organizationId +
                ", organizationName=" + organizationName +
                ", organizationCode=" + organizationCode +
                ", parentId=" + parentId +
                ", remark=" + remark +
                ", createTime=" + createTime +
                ", iorder=" + iorder +
                "}";
    }
}
