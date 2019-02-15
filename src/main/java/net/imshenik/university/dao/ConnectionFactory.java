package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        log.trace("Loading Datasource from Spring Context");
        return new ClassPathXmlApplicationContext("spring.xml").getBean("dataSource", javax.sql.DataSource.class);
    }
}
