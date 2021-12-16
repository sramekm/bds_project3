package org.but.feec.bds_project3.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private static final String APPLICATION_PROPERTIES = "application.properties";

    static {
        try (InputStream resourceAsStream = DataSourceConfig.class.getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES)) {
            Properties properties = new Properties();

            properties.load(resourceAsStream);

            config.setJdbcUrl(properties.getProperty("datasource.url"));
            config.setUsername(properties.getProperty("datasource.username"));
            config.setPassword(properties.getProperty("datasource.password"));
            ds = new HikariDataSource(config);
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            logger.error("Configuration error");
        } catch (Exception e) {
            logger.error("Could not connect to database");
        }
    }

    private DataSourceConfig() {}

    public static DataSource getDataSource() {return ds; }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


}