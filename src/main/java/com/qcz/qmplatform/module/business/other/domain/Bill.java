package com.qcz.qmplatform.module.business.other.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 账单
 * </p>
 *
 * @author quchangzhong
 * @since 2022-08-09
 */
@Data
@Accessors(chain = true)
@TableName("tbl_bill")
public class Bill implements Serializable {

    /**
     * 账单id
     */
    @TableId("id")
    private String id;

    /**
     * 账单类型id
     */
    @TableField("type_id")
    private String typeId;

    /**
     * 金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 消费人
     */
    @TableField("consumer")
    private String consumer;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 消费日期
     */
    @TableField("consume_time")
    private LocalDate consumeTime;

    /**
     * 账单创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 账单创建人id
     */
    @TableField("create_user_id")
    private String createUserId;

}
