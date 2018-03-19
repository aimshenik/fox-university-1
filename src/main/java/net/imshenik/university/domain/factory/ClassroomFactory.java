package net.imshenik.university.domain.factory;

import net.imshenik.university.domain.entities.Classroom;

public class ClassroomFactory {
    public static Classroom createClassroom115(){
        return new Classroom(1,"25A", "3", 90);
    }

    public static Classroom createClassroom304(){
        return new Classroom(4,"25A", "1", 40);
    }

    public static Classroom createClassroom525(){
        return new Classroom(5,"25A", "1",  60);
    }
}
