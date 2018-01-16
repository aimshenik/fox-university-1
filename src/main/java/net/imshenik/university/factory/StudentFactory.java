package net.imshenik.university.factory;

import net.imshenik.university.entities.Student;
import net.imshenik.university.entities.Teacher;

public class StudentFactory {
    public Student createStudentBelov() {
        return new Student(1,"Konstantin","Belov");
    }
    public Student createStudentKrugova() {
        return new Student(2,"Darya","Krugova");
    }
    public Student createStudentBogachev() {
        return new Student(3,"Ilya","Bogachev");
    }
    public Student createStudentSidorenko() {
        return new Student(4,"Egor","Sidorenko");
    }
    public Student createStudentSavin() {
        return new Student(5,"Valentin","Savin");
    }
    public Student createStudentKorneev() {
        return new Student(6,"Sergey","Korneev");
    }
}
