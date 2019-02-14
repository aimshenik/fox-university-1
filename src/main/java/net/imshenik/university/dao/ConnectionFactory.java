package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class ConnectionFactory {
    private static final Logger log = Logger.getLogger(ConnectionFactory.class.getName());
    private static DataSource ds = lookup();

    public static Connection getConnection() throws DaoException {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new DaoException("Unable provide connection to database", e);
        }
    }

    private static DataSource lookup() {
        try {
            log.trace("Loading Datasource from Context");
            return (DataSource) new InitialContext().lookup("java:comp/env/jdbc/universitydb");
        } catch (NamingException e) {
            throw new DaoException("Unable provide lookup Datasource", e);
        }
    }
}
