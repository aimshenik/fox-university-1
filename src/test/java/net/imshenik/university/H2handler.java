package net.imshenik.university;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import net.imshenik.university.dao.ConnectionFactory;

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
}
