package net.imshenik.university;

import java.io.File;
import java.io.FileReader;

public class H2handler {
	public static void main(String[] args) {
		System.out.println("go to H2 DB");
		try {
			Class.forName("org.h2.Driver");
			FileReader fileReader = new FileReader(new File(
					"C:\\Users\\Andrey\\eclipse-workspace\\Task10DAOLayer\\sql\\tools\\createRoleAndDatabase.sql"));
			String s = "";
			int i = 0;
			while (i != 1) {
				i = fileReader.read();
				s += (char) i;
			}
			System.out.println(s.length());

		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("go out of  H2 DB");
	}
}
