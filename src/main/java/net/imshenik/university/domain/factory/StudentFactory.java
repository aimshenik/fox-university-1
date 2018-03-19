package net.imshenik.university.domain.factory;

import net.imshenik.university.domain.entities.Student;

public class StudentFactory {
    public static Student createStudentBelov() {
        return new Student(1,"Konstantin","Belov");
    }

    public static Student createStudentKrugova() {
        return new Student(2,"Darya","Krugova");
    }

    public static Student createStudentBogachev() {
        return new Student(3,"Ilya","Bogachev");
    }

    public static Student createStudentSidorenko() {
        return new Student(4,"Egor","Sidorenko");
    }

    public static Student createStudentSavin() {
        return new Student(5,"Valentin","Savin");
    }

    public static Student createStudentKorneev() {
        return new Student(6,"Sergey","Korneev");
    }
}
