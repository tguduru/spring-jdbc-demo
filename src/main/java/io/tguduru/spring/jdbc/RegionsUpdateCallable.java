package io.tguduru.spring.jdbc;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.tguduru.spring.jdbc.entity.Region;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/8/18
 */
public class RegionsUpdateCallable implements Callable<Integer> {
    private final SingleConnectionDataSource dataSource;
    private final Region region;
    private final int waitTime;

    public RegionsUpdateCallable(SingleConnectionDataSource dataSource, Region region, int waitTime) {
        this.dataSource = dataSource;
        this.region = region;
        this.waitTime = waitTime;
    }

    @Override
    public Integer call() throws Exception {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        int rowsUpdated = 0;
        try {
            RegionsDataManager regionsDataManager = new RegionsDataManager(dataSource);
            System.out
                    .println(Instant.now() + " : Before update : " + regionsDataManager.getRegionById(region.getId()));
            System.out.println("Acquiring Row Lock for : " + region);
            regionsDataManager.selectForUpdate(region.getId());
            System.out.println("Row Locked  for : " + region);

            String sql = "UPDATE regions SET region_name=:regionName where region_id = :regionId";
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
            sqlParameterSource.addValue("regionId", region.getId());
            sqlParameterSource.addValue("regionName", region.getName());
            rowsUpdated = jdbcTemplate.update(sql, sqlParameterSource);
            TimeUnit.SECONDS.sleep(waitTime);
            System.out.println(Instant.now() + " : updated : " + region);

        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
        } finally {
            System.out.println(Instant.now() + " : committed transaction for : " + region);
            transactionManager.commit(transactionStatus);
            RegionsDataManager regionsDataManager = new RegionsDataManager(dataSource);
            System.out.println(Instant.now() + " : After commit : " + regionsDataManager.getRegionById(region.getId()));
        }
        return rowsUpdated;
    }
}
