package net.imshenik.university.domain.factory;

import net.imshenik.university.domain.entities.Subject;


public class SubjectFactory {
    public static Subject createSubjC() {
        return new Subject(1, "C/C++");
    }

    public static Subject createSubjJava() {
        return new Subject(2, "Java");
    }

    public static Subject createSubjDB() {
        return new Subject(3, "Databases theory");
    }
}
