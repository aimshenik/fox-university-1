package net.imshenik.university.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import net.imshenik.university.dao.DaoException;

public class ConnectionFactory {
    private static final Logger log = Logger.getLogger(ConnectionFactory.class.getName());
    private static Properties properties;

    public static Connection getConnection() throws DaoException {
        log.trace("getConnection() | start");
        if (properties == null) {
            log.debug("getConnection() | call loadConfigFromFile()");
            loadConfigFromFile();
            log.debug("getConnection() | call loadDriver()");
            loadDriver(properties.getProperty("driver"));
        }
        Connection connection;
        try {
            log.debug(String.format("getConnection() | getting DB connection to '%s'",properties.getProperty("url")));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("login"),
                    properties.getProperty("password"));
        } catch (SQLException e) {
            log.error("getConnection() | unable to create Connection", e);
            throw new DaoException("getConnection() | unable to create Connection", e);
        }
        log.debug("getConnection() | connection provided ");
        log.trace("getConnection() | end");
        return connection;
    }

    private static void loadConfigFromFile() throws DaoException {
        log.trace("loadConfigFromFile() | start");
        properties = new Properties();
        log.debug("loadConfigFromFile() | loading file 'config.properties' file and reading a property list");
        try (FileInputStream fileInputStream = new FileInputStream(
                new File(ConnectionFactory.class.getClassLoader().getResource("config.properties").getFile()))) {
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            log.error("loadConfig() | file 'config/config.properties' not found", e);
            throw new DaoException("loadConfig() | file 'config/config.properties' not found", e);
        } catch (Exception e) {
            log.error("loadConfig() | Exception while loading 'config/config.properties' ", e);
            throw new DaoException("loadConfig() | Exception while load 'config/config.properties' ", e);
        }
        log.trace("loadConfigFromFile() | end");
    }

    private static void loadDriver(String driver) throws DaoException {
        log.trace("loadDriver() | start");
        try {
            log.debug(String.format("loadDriver() | loading %s database driver", driver ));
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            log.fatal("loadDriver() | Unable to load driver " + driver, e);
            throw new DaoException("loadDriver() | Unable to load driver " + driver, e);
        }
        log.trace("loadDriver() | end");
    }
}
