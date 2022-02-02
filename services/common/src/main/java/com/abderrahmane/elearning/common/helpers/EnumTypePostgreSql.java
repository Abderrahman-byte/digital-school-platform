package com.abderrahmane.elearning.common.helpers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

@SuppressWarnings("rawtypes")
public class EnumTypePostgreSql extends EnumType {
    // I don't know what this method does; 
    // just copied it from : https://thorben-janssen.com/hibernate-enum-mappings/
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.toString(), Types.OTHER);
        }
    }
}
