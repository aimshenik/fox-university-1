package net.imshenik.university.domain;

import java.io.Serializable;

public class Teacher implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String passport;
    
    public Teacher() {
    }
    
    public Teacher(int id, String firstName, String lastName, String passport) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPassport() {
        return passport;
    }
    
    public void setPassport(String passport) {
        this.passport = passport;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id;
    }
    
    @Override
    public int hashCode() {
        return id * 31;
    }
    
    @Override
    public String toString() {
        return "Teacher{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
                + ", passport='" + passport + '\'' + '}';
    }
}
