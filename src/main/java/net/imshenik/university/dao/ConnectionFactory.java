package net.imshenik.university.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConnectionFactory {
    private static final Logger log = Logger.getLogger(ConnectionFactory.class.getName());
    private static Properties properties;
    private static String driver;
    private static String url;
    private static String login;
    private static String password;
    
    static Connection getConnection() throws DaoException {
        log.trace("getConnection() | start");
        if (properties == null) {
            loadConfigFromFile();
            loadDriver(driver);
        }
        Connection connection;
        try {
            log.trace("getConnection() | opening connection to database");
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            log.fatal("getConnection() | unable to create Connection", e);
            throw new DaoException("getConnection() | unable to create Connection", e);
        }
        log.trace("getConnection() | end");
        return connection;
    }
    
    private static void loadConfigFromFile() throws DaoException {
        log.trace("loadConfigFromFile() | start");
        properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(new File("config/config.properties"))) {
            properties.load(fileInputStream);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            login = properties.getProperty("login");
            password = properties.getProperty("password");
        } catch (FileNotFoundException e) {
            throw new DaoException("loadConfig() | file `config/config.ini` not found", e);
        } catch (IOException e) {
            throw new DaoException("loadConfig() | IOException while load `config/config.ini` ", e);
        }
        log.trace("loadConfigFromFile() | end");
    }
    
    private static void loadDriver(String driver) throws DaoException {
        log.trace("loadDriver() | start");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.fatal("AbstractDAO() | Unable to load driver " + driver, e);
            throw new DaoException("AbstractDAO() | Unable to load driver " + driver, e);
        }
        log.trace("loadDriver() | end");
    }
}
