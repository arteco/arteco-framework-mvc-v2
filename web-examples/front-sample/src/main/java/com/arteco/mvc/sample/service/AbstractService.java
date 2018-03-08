package com.arteco.mvc.sample.service;

import com.arteco.mvc.bootstrap.WebThread;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;

import javax.sql.DataSource;

/**
 * Created by rarnau on 2/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
class AbstractService {

    private SQLQueryFactory queryFactory;

    private synchronized SQLQueryFactory initFactory() {
        if (queryFactory == null) {
            DataSource dataSource = WebThread.get().getJdbcDataSource();
            SQLTemplates templates = new MySQLTemplates();
            Configuration configuration = new Configuration(templates);
            return new SQLQueryFactory(configuration, dataSource);
        }
        return queryFactory;
    }

    SQLQueryFactory getQueryFactory() {
        if (queryFactory == null) {
            queryFactory = initFactory();
        }
        return queryFactory;
    }
}
