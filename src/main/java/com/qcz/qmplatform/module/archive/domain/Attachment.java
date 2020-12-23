package com.qcz.qmplatform.module.archive.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 文件
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-12
 */
@TableName("tbl_attachment")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 附件ID，主键
     */
    @TableId("attachment_id")
    private String attachmentId;

    /**
     * 附件名称
     */
    @TableField("attachment_name")
    private String attachmentName;

    /**
     * 附件路径
     */
    @TableField("attachment_url")
    private String attachmentUrl;

    /**
     * 上传人id
     */
    @TableField("upload_user_id")
    private String uploadUserId;

    /**
     * 上传人名称
     */
    @TableField("upload_user_name")
    private String uploadUserName;

    /**
     * 上传时间
     */
    @TableField("upload_time")
    private Timestamp uploadTime;

    /**
     * 附件说明
     */
    @TableField("description")
    private String description;

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public String getUploadUserName() {
        return uploadUserName;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentId=" + attachmentId +
                ", attachmentName=" + attachmentName +
                ", attachmentUrl=" + attachmentUrl +
                ", uploadUserId=" + uploadUserId +
                ", uploadUserName=" + uploadUserName +
                ", uploadTime=" + uploadTime +
                ", description=" + description +
                "}";
    }
}
