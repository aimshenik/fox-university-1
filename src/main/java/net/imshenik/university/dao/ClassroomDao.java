package net.imshenik.university.dao;

import java.util.List;
import net.imshenik.university.domain.Classroom;

public interface ClassroomDao {

    public List<Classroom> findAll() throws DaoException;

    public Classroom findOne(int id) throws DaoException;

    public Classroom create(String number, String building, int capacity) throws DaoException;

    public void update(int id, String number, String building, int capacity) throws DaoException;

    public void delete(int id) throws DaoException;
}
