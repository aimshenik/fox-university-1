package net.imshenik.university.entities;

public class Classroom {
    private int id;
    private String number;
    private String building;
    private int floor;
    private String sector;
    private int capacity;

    public Classroom(int id, String number, String building, int floor, String sector, int capacity) {
        this.id = id;
        this.number = number;
        this.building = building;
        this.floor = floor;
        this.sector = sector;
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
