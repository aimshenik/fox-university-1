package net.imshenik.university;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import org.junit.Test;
import net.imshenik.university.dao.ConnectionFactory;

public class H2handler {
    @Test
    public static void main(String[] args) {
        String sql = "";
        try (FileReader fileReader = new FileReader(new File(
                "C:\\Users\\u0173307\\eclipse-workspace\\Task10DAOLayer\\src\\test\\resources\\sql\\h2tablecreation.sql"))) {
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
            System.out.println(e);
        }
    }
}
