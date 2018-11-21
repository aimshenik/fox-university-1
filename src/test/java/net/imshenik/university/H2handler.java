package net.imshenik.university;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import org.apache.log4j.Logger;

import net.imshenik.university.dao.ConnectionFactory;

public class H2handler {
	private static final Logger log = Logger.getLogger(H2handler.class.getName());
	private static H2handler instance;
	private static boolean initializationRequired = true;

	public static H2handler getInstance() {
		if (instance == null) {
			instance = new H2handler();
		}
		return instance;
	}

	public void initializeIfRequired() {
		if (initializationRequired) {
			createTable("H2ClassroomsDropCreate.sql");
			insertContent("H2ClassroomsInsert.sql");
			initializationRequired = false;
			log.info("initializeIfRequired() | DATABASE WAS INITIALIZED");
		}
	}

	private void createTable(String sqlSource) {
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
				stmt.execute(sql);
			}
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
}
