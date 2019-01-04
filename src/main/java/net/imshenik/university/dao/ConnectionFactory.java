package net.imshenik.university.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import net.imshenik.university.dao.DaoException;

public class ConnectionFactory {
	private static final Logger log = Logger.getLogger(ConnectionFactory.class.getName());
	private static Properties properties;

	public static Connection getConnection() throws DaoException {
		if (properties == null) {
			loadConfigFromFile();
			loadDriver(properties.getProperty("driver"));
		}
		log.debug(String.format("Providing connection to the database '%s' with login '%s' and password '%s'",properties.getProperty("url"), properties.getProperty("login"),
				properties.getProperty("password")));
		Connection connection;
		try {
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("login"),
					properties.getProperty("password"));
		} catch (SQLException e) {
			log.error("getConnection() | unable to create Connection", e);
			throw new DaoException("getConnection() | unable to create Connection", e);
		}
		return connection;
	}

	private static void loadConfigFromFile() throws DaoException {
		log.info("Loading configuration file 'config.properties' ");
		try (FileInputStream fileInputStream = new FileInputStream(
				new File(ConnectionFactory.class.getClassLoader().getResource("config.properties").getFile()))) {
			properties = new Properties();
			properties.load(fileInputStream);
		} catch (FileNotFoundException e) {
			log.error("File 'config/config.properties' not found", e);
			throw new DaoException("file 'config/config.properties' not found", e);
		} catch (Exception e) {
			log.error("Exception while loading 'config/config.properties' ", e);
			throw new DaoException("Exception while load 'config/config.properties' ", e);
		}
		log.info("Configuration file was loaded");
	}

	private static void loadDriver(String driver) throws DaoException {
		log.info(String.format("Loading %s driver", driver));
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			log.error("Unable to load driver " + driver, e);
			throw new DaoException("Unable to load driver " + driver, e);
		}
		log.info("Driver was loaded");
	}
}
