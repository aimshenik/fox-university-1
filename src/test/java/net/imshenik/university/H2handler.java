package net.imshenik.university;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.imshenik.university.dao.ConnectionFactory;
import net.imshenik.university.dao.DaoException;

public class H2handler {
    private static H2handler instance;
    private String sqlCreateTable = "";
    private String sqlInsertContent = "";

    private H2handler() {
    }

    public static H2handler getInstance() {
        if (instance == null) {
            instance = new H2handler();
        }
        return instance;
    }

    public void createTable(String sqlSource) throws Exception {
        if (sqlCreateTable == "") {
            try (FileReader fileReader = new FileReader(
                    new File(H2handler.class.getClassLoader().getResource("sql/" + sqlSource).getFile()))) {
                int i = fileReader.read();
                while (i != -1) {
                    sqlCreateTable += (char) i;
                    i = fileReader.read();
                }
            }
        }
        try (Connection connection = ConnectionFactory.getConnection(); Statement stmt = connection.createStatement()) {
            stmt.execute(sqlCreateTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertContent(String sqlSource) {
        if (sqlInsertContent == "") {
            try (FileReader fileReader = new FileReader(
                    new File(H2handler.class.getClassLoader().getResource("sql/" + sqlSource).getFile()))) {
                int i = fileReader.read();
                while (i != -1) {
                    sqlInsertContent += (char) i;
                    i = fileReader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try (Connection connection = ConnectionFactory.getConnection(); Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlInsertContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getNumberOfRows(String tablename) throws Exception {
        int quantity = 0;
        String sql = String.format("SELECT COUNT(*) FROM %s;", tablename);
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                quantity = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantity;
    }

    public static boolean exist(String tablename, Integer id) throws DaoException {
        if (id == null) {
            return false;
        }
        boolean found = false;
        String sql = String.format("SELECT EXISTS(SELECT 1 FROM %s WHERE ID=%d);", tablename, id);
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                if (resultSet.getBoolean(1) == true) {
                    found = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;
    }

    public static Object getField(String table, String column, Integer id) throws DaoException {
        String sql = String.format("SELECT %s FROM %s WHERE ID=%d;", column, table, id);
        Object data = null;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                data = resultSet.getObject(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
