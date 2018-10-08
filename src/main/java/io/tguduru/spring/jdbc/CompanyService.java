package io.tguduru.spring.jdbc;

import static java.lang.System.out;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import io.tguduru.spring.jdbc.db.DatabaseResource;
import io.tguduru.spring.jdbc.entity.Region;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/8/18
 */
public class CompanyService {
    public static void main(String[] args) throws SQLException {
        SingleConnectionDataSource dataSource = DatabaseResource.getInstance().getConnection();
        RegionsDataManager regionsDataManager = new RegionsDataManager(dataSource);
        Optional<List<Region>> regions = regionsDataManager.getReqions();
        regions.ifPresent(regions1 -> regions1.forEach(out::println));

        Region region = regionsDataManager.getRegionById(3l).get();
        out.println(region);
    }
}
