package com.qcz.qmplatform.module.business.other.domain;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

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
@Data
@Accessors(chain = true)
@TableName("tbl_notepad")
public class Notepad implements Serializable {

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
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Timestamp createTime;

    /**
     * 是否公开（0：私密，1：公开）默认公开
     */
    @TableField("is_public")
    private Long isPublic;

}
