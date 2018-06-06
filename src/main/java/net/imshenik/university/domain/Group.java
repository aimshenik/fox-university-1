package net.imshenik.university.domain;

import java.io.Serializable;

public class Group implements Serializable {
    private int id;
    private String name;
    
    public Group() {
    }
    
    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return id ;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Group group = (Group) o;
        return id == group.id;
    }
    
    @Override
    public int hashCode() {
        return id * 31;
    }
    
    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", name='" + name + "'}";
    }
}
