package net.imshenik.university;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import net.imshenik.university.dao.ConnectionFactory;

public class H2DbTablesCreator {
    private static final Logger log = Logger.getLogger(H2DbTablesCreator.class.getName());
    
    private static void createTestTables(Connection connection) {
        log.trace("createTestTables() | start");
        String sql = "DROP TABLE classrooms;" + "CREATE TABLE IF NOT EXISTS classrooms("
                + "id INTEGER auto_increment (1,1)," + "number varchar(10), building varchar(10),"
                + "capacity integer NOT NULL," + "CONSTRAINT classroom_id_pkey PRIMARY KEY (id));";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            log.fatal("createTestTables() | unable to create tables in test database", e);
        }
        log.trace("createTestTables() | end");
    }
}
