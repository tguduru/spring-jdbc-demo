package io.tguduru.spring.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.tguduru.spring.jdbc.entity.Region;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/7/18
 */
public class RegionsDataManager {
    private final SingleConnectionDataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RegionsDataManager(SingleConnectionDataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Optional<Integer> update(Region region) {
        String sql = "UPDATE regions SET region_name=:regionName where region_id = :regionId";
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("regionId", region.getId());
        sqlParameterSource.addValue("regionName", region.getName());
        int rowsUpdated = jdbcTemplate.update(sql, sqlParameterSource);
        return Optional.of(rowsUpdated);
    }

    public Optional<List<Region>> getReqions() {
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
        return Optional.of(regions);
    }

    public Optional<Region> getRegionById(Long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("regionId", id);
        Region region = jdbcTemplate.queryForObject("SELECT * from REGIONS WHERE region_id = :regionId",
                mapSqlParameterSource, (rs, rowNum) -> new Region(rs.getLong(1), rs.getString(2)));

        return Optional.of(region);
    }

    public void selectForUpdate(long regionId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("regionId", regionId);
        jdbcTemplate.queryForObject("SELECT * from REGIONS WHERE region_id = :regionId FOR UPDATE",
                mapSqlParameterSource, (rs, rowNum) -> new Region(rs.getLong(1), rs.getString(2)));

    }

    public Optional<Boolean> deleteReqion(Long id) {
        return Optional.of(false);
    }

    public Optional<Long> addRegion(Region region) {
        return Optional.empty();
    }

}
