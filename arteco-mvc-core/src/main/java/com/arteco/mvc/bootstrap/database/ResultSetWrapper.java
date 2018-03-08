package com.arteco.mvc.bootstrap.database;

import com.arteco.mvc.bootstrap.utils.ExceptionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rarnau on 2/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class ResultSetWrapper {
    private final ResultSet resultSet;

    public ResultSetWrapper(ResultSet rs) {
        this.resultSet = rs;
    }

    public String getString(String colname) {
        try {
            return resultSet.getString(colname);
        } catch (SQLException e) {
            throw ExceptionUtils.manage(e);
        }
    }
}
