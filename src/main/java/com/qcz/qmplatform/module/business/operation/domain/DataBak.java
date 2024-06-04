package com.qcz.qmplatform.module.business.operation.domain;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 数据备份
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-26
 */
@Data
@Accessors(chain = true)
@TableName("ope_data_bak")
public class DataBak implements Serializable {

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
    private LocalDateTime createTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 备份文件大小（字节）
     */
    @TableField("file_size")
    private long fileSize;

}
