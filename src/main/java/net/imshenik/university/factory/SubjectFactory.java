package net.imshenik.university.factory;

import net.imshenik.university.entities.Subject;


public class SubjectFactory {
    public Subject createSubjC() {
        return new Subject(1, "C/C++");
    }

    public Subject createSubjJava() {
        return new Subject(2, "Java");
    }

    public Subject createSubjDB() {
        return new Subject(3, "Databases theory");
    }
}
