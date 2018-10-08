package io.tguduru.spring.jdbc.db;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;

import io.tguduru.spring.jdbc.config.Configuration;
import io.tguduru.spring.jdbc.config.DatabaseConfig;
import io.tguduru.spring.jdbc.config.YamlConfig;

import oracle.jdbc.pool.OracleDataSource;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/5/18
 */
public class DatabaseResource {

    private static DatabaseResource databaseResourceInstance;
    private final OracleDataSource oracleDataSource;

    private DatabaseResource() throws SQLException {
        YamlConfig yamlConfig = Configuration.getInstance().getYamlConfig();
        DatabaseConfig databaseConfig = yamlConfig.getDatabase();
        oracleDataSource = new OracleDataSource();
        oracleDataSource.setURL(databaseConfig.getUrl());
        oracleDataSource.setUser(databaseConfig.getUserName());
        oracleDataSource.setPassword(databaseConfig.getPassword());
    }

    public static DatabaseResource getInstance() throws SQLException {
        if (databaseResourceInstance == null) {
            databaseResourceInstance = new DatabaseResource();
        }
        return databaseResourceInstance;
    }

    public SingleConnectionDataSource getConnection() throws SQLException {
        return new SingleConnectionDataSource(oracleDataSource.getConnection(), false);
    }
}
