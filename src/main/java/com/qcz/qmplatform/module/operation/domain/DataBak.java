package com.qcz.qmplatform.module.operation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 数据备份
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-26
 */
@TableName("ope_data_bak")
public class DataBak implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 备份主键id
     */
    @TableId("bak_id")
    private String bakId;

    /**
     * 备份名称
     */
    @TableField("bak_name")
    private String bakName;

    /**
     * 备份路径
     */
    @TableField("bak_path")
    private String bakPath;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Timestamp createTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    public String getBakId() {
        return bakId;
    }

    public void setBakId(String bakId) {
        this.bakId = bakId;
    }

    public String getBakName() {
        return bakName;
    }

    public void setBakName(String bakName) {
        this.bakName = bakName;
    }

    public String getBakPath() {
        return bakPath;
    }

    public void setBakPath(String bakPath) {
        this.bakPath = bakPath;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DataBak{" +
                "bakId=" + bakId +
                ", bakName=" + bakName +
                ", bakPath=" + bakPath +
                ", createTime=" + createTime +
                ", remark=" + remark +
                "}";
    }
}
