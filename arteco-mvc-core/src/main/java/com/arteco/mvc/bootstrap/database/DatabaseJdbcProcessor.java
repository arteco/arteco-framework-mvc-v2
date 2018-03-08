package com.arteco.mvc.bootstrap.database;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.WebProcessor;
import com.arteco.mvc.bootstrap.chain.WebChain;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Refer to QueryDSL jdbc for code generatio: https://github.com/querydsl/querydsl/tree/master/querydsl-sql
 */
@Slf4j
public class DatabaseJdbcProcessor implements WebProcessor {


    private HikariDataSource dataSource;

    public DatabaseJdbcProcessor() {
        //no database
        this.dataSource = null;
    }

    public DatabaseJdbcProcessor(HikariConfig config) {
        configure(config);
    }

    public DatabaseJdbcProcessor(String url, String username, String password) {
        configure(url, username, password);
    }

    public DatabaseJdbcProcessor(Properties properties) {
        HikariConfig config = new HikariConfig(properties);
        configure(config);
    }

    public DatabaseJdbcProcessor(String proertyFile) {
        HikariConfig config = new HikariConfig(proertyFile);
        configure(config);
    }

    public void configure(String url, String username, String password) {
        Properties props = new Properties();
        props.setProperty("jdbcUrl", url);
        props.setProperty("username", username);
        props.setProperty("password", password);
//        los drivers nuevos no es necesario, se auto detecta de la url
//        props.setProperty("driverClassName", driverClassName);
//        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.prepStmtCacheSize", "250");
        props.setProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        props.setProperty("dataSource.cachePrepStmts", "true");
        props.setProperty("dataSource.useServerPrepStmts", "true");
        props.setProperty("dataSource.useLocalSessionState", "true");
        props.setProperty("dataSource.useLocalTransactionState", "true");
        props.setProperty("dataSource.rewriteBatchedStatements", "true");
        props.setProperty("dataSource.cacheResultSetMetadata", "true");
        props.setProperty("dataSource.cacheServerConfiguration", "true");
        props.setProperty("dataSource.elideSetAutoCommits", "true");
        props.setProperty("dataSource.maintainTimeStats", "false");
        props.put("dataSource.logWriter", log);
        HikariConfig config = new HikariConfig(props);
        configure(config);
    }

    private void configure(HikariConfig config) {
        this.dataSource = new HikariDataSource(config);
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(this.dataSource);
        flyway.migrate();
    }

    @Override
    public void process(WebContext webContext, WebChain chain) throws Exception {
        Connection con = null;
        try {
            try {
                if (dataSource != null) {
                    con = dataSource.getConnection();
                    con.setAutoCommit(false);
                    webContext.setJdbcDataSource(dataSource);
                    webContext.setJdbcConnection(con);
                }
                // execute main chain
                chain.process();
                // execute main chain
                if (con != null) {
                    con.commit();
                }
            } catch (Exception e) {
                if (con != null) {
                    con.rollback();
                }
                throw e;
            } finally {
                if (con != null) {
                    con.close();
                }
            }
        } catch (SQLException sqle) {
            throw ExceptionUtils.manage(sqle);
        }
    }
}
