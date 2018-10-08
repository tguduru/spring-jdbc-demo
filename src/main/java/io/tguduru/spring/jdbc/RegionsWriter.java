package io.tguduru.spring.jdbc;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import io.tguduru.spring.jdbc.entity.Region;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/5/18
 */
public class RegionsWriter implements Runnable {

    private final SingleConnectionDataSource dataSource;
    private final Region region;

    public RegionsWriter(Region region, SingleConnectionDataSource dataSource) {
        this.dataSource = dataSource;
        this.region = region;
    }

    @Override
    public void run() {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            String sql = "UPDATE regions SET region_name=:regionName where region_id = :regionId";

            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
            sqlParameterSource.addValue("regionId", region.getId());
            sqlParameterSource.addValue("regionName", region.getName());
            jdbcTemplate.update(sql, sqlParameterSource);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
        } finally {
            System.out.println("committed transaction");
            transactionManager.commit(transactionStatus);
        }
    }
}
