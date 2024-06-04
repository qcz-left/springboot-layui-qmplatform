package com.qcz.qmplatform.module.business.archive.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-12
 */
@Data
@Accessors(chain = true)
@TableName("tbl_attachment")
public class Attachment implements Serializable {

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
    private LocalDateTime uploadTime;

    /**
     * 附件说明
     */
    @TableField("description")
    private String description;

    /**
     * 附件大小（单位：字节）
     */
    @TableField("size")
    private long size;

}
