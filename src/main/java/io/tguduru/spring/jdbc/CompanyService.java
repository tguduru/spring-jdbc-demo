package io.tguduru.spring.jdbc;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.tguduru.spring.jdbc.db.DatabaseResource;
import io.tguduru.spring.jdbc.entity.Region;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/8/18
 */
public class CompanyService {
    public static void main(String[] args) throws SQLException, InterruptedException {
        // SingleConnectionDataSource dataSource = DatabaseResource.getInstance().getConnection();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        /*
         * RegionsDataManager regionsDataManager = new RegionsDataManager(dataSource);
         * Optional<List<Region>> regions = regionsDataManager.getReqions();
         * regions.ifPresent(regions1 -> regions1.forEach(out::println));
         * Region region = regionsDataManager.getRegionById(3l).get();
         * out.println(region);
         */
        Region region = new Region(4, "EMEA");
        executorService.submit(new RegionsUpdateCallable(DatabaseResource.getInstance().getConnection(), region, 5));

        Region region2 = new Region(4, "Middle East and Africa");
        executorService.submit(new RegionsUpdateCallable(DatabaseResource.getInstance().getConnection(), region2, 5));
        executorService.shutdown();
        executorService.awaitTermination(12, TimeUnit.SECONDS);

        // final data
        SingleConnectionDataSource dataSource = DatabaseResource.getInstance().getConnection();
        RegionsDataManager regionsDataManager = new RegionsDataManager(dataSource);
        System.out.println("----------------");
        System.out.println("Final Data : " + regionsDataManager.getRegionById(4l));

    }
}
