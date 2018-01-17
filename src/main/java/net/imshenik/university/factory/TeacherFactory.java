package net.imshenik.university.factory;

import net.imshenik.university.entities.Teacher;

public class TeacherFactory {
    public static Teacher createTeacherIvanov() {
        return new Teacher(1,"Ivan","Ivanov", "I1111111111");
    }
    public static Teacher createTeacherPetrov() {
        return new Teacher(2,"Petr","Petrov", "P22222222222");
    }
    public static Teacher createTeacherSidorov() {
        return new Teacher(3,"Sidor","Sidorov", "S33333333333");
    }
}
