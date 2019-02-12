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
        Connection connection = null;
        try {
            Context ctx = new InitialContext();
            log.trace("Loading Datasource from Context");
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/universitydb");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            throw new DaoException("Unable provide connection to database");
        }
        return connection;
    }
}
