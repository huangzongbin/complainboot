package com.krt.gen.util;

import com.krt.gen.enums.DbTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * DB工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月14日
 */
@Slf4j
public class DbUtils {

    /**
     * sql类型转java 类型
     *
     * @param dbType  数据库类别
     * @param sqlType sql类型
     */
    public static String sqlType2JavaType(String dbType, String sqlType) {
        if (DbTypeEnum.MYSQL.getValue().equalsIgnoreCase(dbType)) {
            if ("bit".equalsIgnoreCase(sqlType)) {
                return "Boolean";
            } else if ("tinyint".equalsIgnoreCase(sqlType)) {
                return "Byte";
            } else if ("smallint".equalsIgnoreCase(sqlType)) {
                return "Short";
            } else if ("int".equalsIgnoreCase(sqlType)) {
                return "Integer";
            } else if ("bigint".equalsIgnoreCase(sqlType)) {
                return "Long";
            } else if ("float".equalsIgnoreCase(sqlType)) {
                return "Float";
            } else if ("numeric".equalsIgnoreCase(sqlType)
                    || "real".equalsIgnoreCase(sqlType)
                    || "double".equalsIgnoreCase(sqlType)) {
                return "Double";
            } else if ("decimal".equalsIgnoreCase(sqlType)) {
                return "BigDecimal";
            } else if ("money".equalsIgnoreCase(sqlType)
                    || "smallmoney".equalsIgnoreCase(sqlType)) {
                return "Double";
            } else if ("varchar".equalsIgnoreCase(sqlType)
                    || "char".equalsIgnoreCase(sqlType)
                    || "nvarchar".equalsIgnoreCase(sqlType)
                    || "nchar".equalsIgnoreCase(sqlType)) {
                return "String";
            } else if ("datetime".equalsIgnoreCase(sqlType)) {
                return "Date";
            } else if ("image".equalsIgnoreCase(sqlType)) {
                return "Blob";
            } else if ("text".equalsIgnoreCase(sqlType)) {
                return "String";
            }
            log.error("---------------类型不支持~ 请反馈给殷帅-----------------");
        }
        if (DbTypeEnum.OARCLE.getValue().equalsIgnoreCase(dbType)) {
            if ("bit".equalsIgnoreCase(sqlType)) {
                return "Boolean";
            } else if ("NUMBER".equalsIgnoreCase(sqlType)
                    || "INTEGER".equalsIgnoreCase(sqlType)
                    || "INT".equalsIgnoreCase(sqlType)) {
                return "Integer";
            } else if ("NVARCHAR2".equalsIgnoreCase(sqlType)
                    || "VARCHAR2".equalsIgnoreCase(sqlType)
                    || "CHAR".equalsIgnoreCase(sqlType)
                    || "LONG".equalsIgnoreCase(sqlType)) {
                return "String";
            } else if ("DATE".equalsIgnoreCase(sqlType)) {
                return "Date";
            } else if ("BLOB".equalsIgnoreCase(sqlType)) {
                return "Blob";
            } else if ("CLOB".equalsIgnoreCase(sqlType)) {
                return "Clob";
            }
            log.error("----------------类型不支持~ 请反馈给殷帅-------------------");
        }

        if (DbTypeEnum.SQLSERVER.getValue().equalsIgnoreCase(dbType)) {
            //TODO: sqlserver 支持待处理
            log.debug("-------------------暂不支持sqlserver-----------------------");
        }
        return null;
    }
}
