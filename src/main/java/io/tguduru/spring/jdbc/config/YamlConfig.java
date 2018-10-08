package io.tguduru.spring.jdbc.config;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/3/18
 */
public class YamlConfig {
    private DatabaseConfig database;

    public DatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }
}
