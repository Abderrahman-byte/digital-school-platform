package com.abderrahmane.elearning.common.utils;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SQLUtils {
    // FIXME : I dont know if it is good idea to put this here
    public static boolean updateTable(EntityManager entityManager, String tableName, String pk, String pkValue, Map<String, Object> data) {
        String sqlString = SQLUtils.constructSQLUpdateString(tableName, pk, data);
        Query query = entityManager.createNativeQuery(sqlString);
        Object[] values = data.values().toArray();

        for (int i = 0; i < values.length; i++) query.setParameter(i + 1, values[i]);

        query.setParameter(values.length + 1, pkValue);

        return query.executeUpdate() > 0;
    }

    public static String constructSQLUpdateString(String tableName, String pk, Map<String, Object> data) {
        return "UPDATE " + tableName + " SET "
                + String.join(", ", data.keySet().stream().map(key -> key + " = ?").toList())
                + " WHERE " + pk + " = ?";
    }
}
