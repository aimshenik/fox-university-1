package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

final class ConnectionFactory {
    private static final Logger log = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/university";
    private static final String LOGIN = "andrey";
    private static final String PASSWORD = "1234321";
    private static boolean driverIsNotLoaded = true;
    
    static Connection getConnection() throws DaoException {
        if (driverIsNotLoaded) {
            loadDriver();
        }
        Connection connection = null;
        try {
            log.trace("getConnection() | opening connection to database");
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            log.fatal("getConnection() | unable to create Connection", e);
            throw new DaoException("getConnection() | unable to create Connection", e);
        }
        return connection;
    }
    
    private static void loadDriver() throws DaoException {
        try {
            Class.forName(DRIVER);
            driverIsNotLoaded = false;
        } catch (ClassNotFoundException e) {
            log.fatal("AbstractDAO() | Unable to load driver " + DRIVER, e);
            throw new DaoException("AbstractDAO() | Unable to load driver " + DRIVER, e);
        }
    }
}
