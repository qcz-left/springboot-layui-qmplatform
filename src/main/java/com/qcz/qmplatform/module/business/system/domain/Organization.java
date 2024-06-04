package com.qcz.qmplatform.module.business.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author quchangzhong
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
@TableName("sys_organization")
public class Organization implements Serializable {

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
    private LocalDateTime createTime;

    /**
     * 排序
     */
    @TableField("iorder")
    private Integer iorder;


}
