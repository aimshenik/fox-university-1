package net.imshenik.university.factory;

import net.imshenik.university.entities.Classroom;

public class ClassroomFactory {
    public Classroom createClassroom115(){
        return new Classroom(1,"25A", "3", 1, "A", 90);
    }

    public Classroom createClassroom304(){
        return new Classroom(1,"25A", "1", 3, "B", 40);
    }

    public Classroom createClassroom525(){
        return new Classroom(1,"25A", "1", 5, "C", 60);
    }
}
