package net.imshenik.university.factory;

import net.imshenik.university.entities.Group;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupFactory {
    StudentFactory studentFactory = new StudentFactory();

    public Group createGroupK25() {
        return new Group(1, "K25", new ArrayList<>(Arrays.asList(studentFactory.createStudentBelov(), studentFactory.createStudentBogachev())));
    }

    public Group createGroupZ18() {
        return new Group(2, "Z18", new ArrayList<>(Arrays.asList(studentFactory.createStudentKorneev(), studentFactory.createStudentKrugova())));
    }

    public Group createGroupL32() {
        return new Group(3, "L32", new ArrayList<>(Arrays.asList(studentFactory.createStudentSavin(), studentFactory.createStudentSidorenko())));
    }
}
