package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class ConnectionFactory {
    private static final Logger log = Logger.getLogger(ConnectionFactory.class.getName());
    private static DataSource ds;

    public static Connection getConnection() throws DaoException {
        if (ds == null) {
            lookup();
        }
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            throw new DaoException("Unable provide connection to database", e);
        }
        return connection;
    }

    private static void lookup() {
        Context ctx;
        try {
            ctx = new InitialContext();
            log.trace("Loading Datasource from Context");
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/universitydb");
        } catch (NamingException e) {
            throw new DaoException("Unable provide lookup Datasource", e);
        }
    }
}
