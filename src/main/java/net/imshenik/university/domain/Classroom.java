package net.imshenik.university.domain;

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
    
    public Classroom(String number, String building, int capacity) {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Classroom other = (Classroom) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
    
    @Override
    public String toString() {
        return "Classroom{" + "id=" + id + ", number='" + number + '\'' + ", building='" + building + '\''
                + ", capacity=" + capacity + '}';
    }
}
