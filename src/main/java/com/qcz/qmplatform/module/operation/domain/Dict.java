package com.qcz.qmplatform.module.operation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-19
 */
@TableName("sys_dict")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典主键id
     */
    @TableId("dict_id")
    private String dictId;

    /**
     * 字典编码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Dict{" +
                "dictId='" + dictId + '\'' +
                ", dictCode='" + dictCode + '\'' +
                ", dictName='" + dictName + '\'' +
                ", remark='" + remark + '\'' +
                ", sort=" + sort +
                '}';
    }
}
