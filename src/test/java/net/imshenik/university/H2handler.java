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
	String sqlCreateTable = "";

	public static H2handler getInstance() {
		if (instance == null) {
			instance = new H2handler();
		}
		return instance;
	}

	public void initialize() throws Exception {
		createTable("H2ClassroomsDropCreate.sql");
		insertContent("H2ClassroomsInsert.sql");
	}

	private void createTable(String sqlSource) throws Exception {
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

	private void insertContent(String sqlSource) {
		String sql = "";
		try (FileReader fileReader = new FileReader(
				new File(H2handler.class.getClassLoader().getResource("sql/" + sqlSource).getFile()))) {
			int i = fileReader.read();
			while (i != -1) {
				sql += (char) i;
				i = fileReader.read();
			}
			Class.forName("org.h2.Driver");
			try (Connection connection = ConnectionFactory.getConnection();
					Statement stmt = connection.createStatement()) {
				stmt.executeUpdate(sql);
			}
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
}
