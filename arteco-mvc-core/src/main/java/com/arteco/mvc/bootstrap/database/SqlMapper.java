package com.arteco.mvc.bootstrap.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rarnau on 2/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@FunctionalInterface
public interface SqlMapper<T> {
    T apply(ResultSet rs) throws SQLException;
}
