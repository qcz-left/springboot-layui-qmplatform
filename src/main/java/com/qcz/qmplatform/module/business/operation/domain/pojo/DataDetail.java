package com.qcz.qmplatform.module.business.operation.domain.pojo;

import com.qcz.qmplatform.common.utils.DateUtils;
import com.qcz.qmplatform.common.utils.RandomUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 制作数据 数据详情
 */
@Data
@Accessors(chain = true)
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
        return StringUtils.toLowerCase(tableName);
    }

    @Data
    @Accessors(chain = true)
    public static class ColumnDetail implements Serializable {

        private static final long serialVersionUID = 1L;

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
        int columnLength;

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
                return ColumnType.getColumnType(type).random(columnLength);
            }

            return null;
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
