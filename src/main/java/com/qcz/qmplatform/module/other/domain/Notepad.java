package com.qcz.qmplatform.module.other.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 记事本
 * </p>
 *
 * @author quchangzhong
 * @since 2021-11-04
 */
@TableName("tbl_notepad")
public class Notepad implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据id
     */
    @TableId("id")
    private String id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 创建人id
     */
    @TableField("create_user_id")
    private String createUserId;

    /**
     * 创建人名称
     */
    @TableField("create_user_name")
    private String createUserName;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Timestamp createTime;

    /**
     * 是否公开（0：私密，1：公开）默认公开
     */
    @TableField("is_public")
    private Long isPublic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Long isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "Notepad{" +
                "id=" + id +
                ", title=" + title +
                ", content=" + content +
                ", createUserId=" + createUserId +
                ", createUserName=" + createUserName +
                ", createTime=" + createTime +
                "}";
    }
}
