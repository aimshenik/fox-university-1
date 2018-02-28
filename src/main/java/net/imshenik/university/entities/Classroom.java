package net.imshenik.university.entities;

import java.io.Serializable;

public class Classroom implements Serializable {
    private int id;
    private String number;
    private String building;
    private int capacity;

    public Classroom() {
    }

    public Classroom(int id, String number, String building, int capacity) {
        this.id = id;
        this.number = number;
        this.building = building;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classroom classroom = (Classroom) o;

        if (number != null ? !number.equals(classroom.number) : classroom.number != null) return false;
        return building != null ? building.equals(classroom.building) : classroom.building == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (building != null ? building.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", building='" + building + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
