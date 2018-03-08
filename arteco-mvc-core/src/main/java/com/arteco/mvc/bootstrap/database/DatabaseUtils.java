package com.arteco.mvc.bootstrap.database;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebThread;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rarnau on 2/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class DatabaseUtils {


    public static <T> List<T> query(String query, SqlMapper<T> mapper) {
        List<T> result = new ArrayList<>();
        try {
            WebContext ctx = WebThread.get();
            Connection conn = ctx.getJdbcConnection();
            ResultSet rs = conn.prepareStatement(query).executeQuery();
            while (rs.next()) {
                result.add(mapper.apply(rs));
            }
            rs.close();
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
        return result;
    }
}
