package net.imshenik.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class ConnectionFactory {
    private static final Logger log = Logger.getLogger(ConnectionFactory.class.getName());
    private static boolean driverIsNotLoaded = true;
    private static boolean testTablesNotCreated = true;
    
    static Connection getConnection() throws DaoException {
        log.trace("getConnection() | start");
        Environment environment = new ProductionEnvironment();
        if (driverIsNotLoaded) {
            loadDriver(environment.getDRIVER());
        }
        Connection connection;
        try {
            log.trace("getConnection() | opening connection to database");
            connection = DriverManager.getConnection(environment.getURL(), environment.getLOGIN(),
                    environment.getPASSWORD());
            if (environment instanceof TestEnvironment && testTablesNotCreated) {
                createTestTables(connection);
            }
        } catch (SQLException e) {
            log.fatal("getConnection() | unable to create Connection", e);
            throw new DaoException("getConnection() | unable to create Connection", e);
        }
        log.trace("getConnection() | end");
        return connection;
    }
    
    private static void createTestTables(Connection connection) {
        log.trace("createTestTables() | start");
        String sql = "DROP TABLE classrooms;"
                + "CREATE TABLE IF NOT EXISTS classrooms(" + "id INTEGER auto_increment (1,1),"
                + "number varchar(10), building varchar(10)," + "capacity integer NOT NULL,"
                + "CONSTRAINT classroom_id_pkey PRIMARY KEY (id));";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            testTablesNotCreated = false;
        } catch (SQLException e) {
            log.fatal("createTestTables() | unable to create tables in test database", e);
        }
        log.trace("createTestTables() | end");
    }
    
    private static void loadDriver(String driver) throws DaoException {
        try {
            Class.forName(driver);
            driverIsNotLoaded = false;
        } catch (ClassNotFoundException e) {
            log.fatal("AbstractDAO() | Unable to load driver " + driver, e);
            throw new DaoException("AbstractDAO() | Unable to load driver " + driver, e);
        }
    }
}

class ProductionEnvironment implements Environment {
    private String DRIVER = "org.postgresql.Driver";
    private String URL = "jdbc:postgresql://localhost:5432/university";
    private String LOGIN = "andrey";
    private String PASSWORD = "1234321";
    
    public String getDRIVER() {
        return DRIVER;
    }
    
    public String getURL() {
        return URL;
    }
    
    public String getLOGIN() {
        return LOGIN;
    }
    
    public String getPASSWORD() {
        return PASSWORD;
    }
}

class TestEnvironment implements Environment {
    private String DRIVER = "org.h2.Driver";
    private String URL = "jdbc:h2:./database/university";
    private String LOGIN = "andrey";
    private String PASSWORD = "1234321";
    
    public String getDRIVER() {
        return DRIVER;
    }
    
    public String getURL() {
        return URL;
    }
    
    public String getLOGIN() {
        return LOGIN;
    }
    
    public String getPASSWORD() {
        return PASSWORD;
    }
}

interface Environment {
    public String getDRIVER();
    
    public String getURL();
    
    public String getLOGIN();
    
    public String getPASSWORD();
}
