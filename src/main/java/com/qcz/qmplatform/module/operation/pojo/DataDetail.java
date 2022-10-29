package com.qcz.qmplatform.module.operation.pojo;

import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.RandomUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 制作数据 数据详情
 */
public class DataDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段详情
     */
    private List<ColumnDetail> columnDetails;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnDetail> getColumnDetails() {
        return columnDetails;
    }

    public void setColumnDetails(List<ColumnDetail> columnDetails) {
        this.columnDetails = columnDetails;
    }

    public static class ColumnDetail {
        /**
         * 字段名
         */
        String name;
        /**
         * 字段类型
         */
        String type;
        /**
         * 字段值类型（1：固定值；2：随机值）
         */
        int valueType;
        /**
         * 字段值
         */
        String value;
        /**
         * 字段长度
         */
        int length;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getValueType() {
            return valueType;
        }

        public void setValueType(int valueType) {
            this.valueType = valueType;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getValue() {
            return value;
        }

        public Object getFinallyValue() {
            if (valueType == 1) {
                if ("number".equalsIgnoreCase(type)) {
                    return Long.parseLong(value);
                } else if ("date".equalsIgnoreCase(type)) {
                    return DateUtils.parse(value, "yyyy-MM-dd HH:mm:ss");
                } else {
                    return value;
                }
            }

            if (valueType == 2) {
                return ColumnType.getColumnType(type).random(length);
            }

            return null;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 字段类型
     */
    enum ColumnType {
        STRING("string"),
        NUMBER("number"),
        DATE("date"),
        IPV4("ipv4"),
        IPV6("ipv6"),
        MAC("mac"),

        INSTANCE("");


        private final String type;

        public String getType() {
            return this.type;
        }

        ColumnType(String type) {
            this.type = type;
        }

        public static ColumnType getColumnType(String type) {
            ColumnType[] values = values();
            for (ColumnType value : values) {
                if (value.type.equalsIgnoreCase(type)) {
                   return value;
                }
            }

            return INSTANCE;
        }

        public Object random(int length) {
            ColumnType columnType = getColumnType(type);
            if (columnType == STRING) {
                return RandomUtils.randomString(length);
            } else if (columnType == NUMBER) {
                return Long.parseLong(RandomUtils.randomNumbers(length));
            } else if (columnType == DATE) {
                return new Date(new Date().getTime() - RandomUtils.randomLong(0, 30 * 24 * 3600 * 1000L));
            } else if (columnType == IPV4) {
                return RandomUtils.randomIPV4();
            } else if (columnType == IPV6) {
                return RandomUtils.randomIPV6();
            } else if (columnType == MAC) {
                return RandomUtils.randomMAC();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(RandomUtils.randomNumbers(2));
    }
}
