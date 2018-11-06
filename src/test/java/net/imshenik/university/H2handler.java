package net.imshenik.university;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import org.junit.Test;
import net.imshenik.university.dao.ConnectionFactory;

public class H2handler {
	@Test
	public static void createTable(String source) {
		String sql = "";
		try (FileReader fileReader = new FileReader(
				new File(H2handler.class.getClassLoader().getResource("sql/" + source).getFile()))) {
			int i = fileReader.read();
			while (i != -1) {
				sql += (char) i;
				System.out.print((char) i);
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
}
