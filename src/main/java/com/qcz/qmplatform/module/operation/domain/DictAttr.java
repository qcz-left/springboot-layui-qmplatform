package com.qcz.qmplatform.module.operation.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 字典属性
 * </p>
 *
 * @author quchangzhong
 * @since 2020-12-19
 */
@TableName("sys_dict_attr")
public class DictAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性名称
     */
    @TableId("attr_id")
    private String attrId;

    /**
     * 属性名称
     */
    @TableField("attr_name")
    private String attrName;

    /**
     * 属性值
     */
    @TableField("attr_value")
    private String attrValue;

    /**
     * 对应sys_dict的dict_id
     */
    @TableField("dict_id")
    private String dictId;

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    @Override
    public String toString() {
        return "DictAttr{" +
                "attrId='" + attrId + '\'' +
                ", attrName='" + attrName + '\'' +
                ", attrValue='" + attrValue + '\'' +
                ", dictId='" + dictId + '\'' +
                '}';
    }
}
