package io.tguduru.spring.jdbc;

import static java.lang.System.out;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.tguduru.spring.jdbc.db.DatabaseResource;
import io.tguduru.spring.jdbc.entity.Region;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/3/18
 */
public class RegionsReader {
    public static void main(String[] args) throws SQLException {
        SingleConnectionDataSource connection = DatabaseResource.getInstance().getConnection();
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(connection);
        List<Region> regions = jdbcTemplate.query("SELECT * FROM REGIONS", new ResultSetExtractor<List<Region>>() {
            @Override
            public List<Region> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Region> regionList = new ArrayList<>();
                while (rs.next()) {
                    Region region = new Region(rs.getLong(1), rs.getString(2));
                    regionList.add(region);
                }
                return regionList;
            }
        });

        regions.forEach(out::println);
    }
}
