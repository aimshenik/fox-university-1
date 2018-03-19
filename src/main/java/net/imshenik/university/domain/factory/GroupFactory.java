package net.imshenik.university.domain.factory;

import java.util.ArrayList;
import java.util.Arrays;

import net.imshenik.university.domain.entities.Group;

public class GroupFactory {
    StudentFactory studentFactory = new StudentFactory();

    public static Group createGroupK25() {
        return new Group(1, "K25", new ArrayList<>(Arrays.asList(StudentFactory.createStudentBelov(), StudentFactory.createStudentBogachev())));
    }

    public static Group createGroupZ18() {
        return new Group(2, "Z18", new ArrayList<>(Arrays.asList(StudentFactory.createStudentKorneev(), StudentFactory.createStudentKrugova())));
    }

    public static Group createGroupL32() {
        return new Group(3, "L32", new ArrayList<>(Arrays.asList(StudentFactory.createStudentSavin(), StudentFactory.createStudentSidorenko())));
    }
}
