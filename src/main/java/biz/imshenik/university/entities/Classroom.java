package biz.imshenik.university.entities;

import java.util.UUID;

public class Classroom {
    private final UUID uuid;
    private final String number;
    private final String building;
    private final int floor;
    private final String sector;
    private final int capacity;

    public Classroom(UUID uuid, String number, String building, int floor, String sector, int capacity) {
        this.uuid = uuid;
        this.number = number;
        this.building = building;
        this.floor = floor;
        this.sector = sector;
        this.capacity = capacity;
    }


}
