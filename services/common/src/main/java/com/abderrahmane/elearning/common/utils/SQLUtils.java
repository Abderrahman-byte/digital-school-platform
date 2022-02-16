package com.abderrahmane.elearning.common.utils;

import java.util.Map;

public class SQLUtils {
    public static String constructSQLUpdateString(String tableName, String pk, Map<String, Object> data) {
        return "UPDATE " + tableName + " SET "
                + String.join(", ", data.keySet().stream().map(key -> key + " = ?").toList())
                + " WHERE " + pk + " = ?";
    }
}
