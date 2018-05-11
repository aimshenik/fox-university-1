package net.imshenik.university.dao.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

abstract class AbstractDAO {
    private static Connection   connection;
    private static final Logger log      = Logger.getLogger(ClassroomDAO.class.getName());
    private static final String DRIVER   = "org.postgresql.Driver";
    private static final String URL      = "jdbc:postgresql://localhost:5432/university";
    private static final String LOGIN    = "andrey";
    private static final String PASSWORD = "1234321";
    
    AbstractDAO() throws DAOException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.fatal("AbstractDAO() | Unable to load driver " + DRIVER, e);
            throw new DAOException("findAll() | Unable to load driver " + DRIVER, e);
        }
    }
    
    Connection getConnection() throws DAOException {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            log.error("getConnection() | Unable to create Connection", e);
            throw new DAOException("getConnection() | Unable to create Connection", e);
        }
        return connection;
    }
}
