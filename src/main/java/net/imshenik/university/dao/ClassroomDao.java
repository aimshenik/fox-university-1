package net.imshenik.university.dao;

import java.util.List;

public interface ClassroomDao<T> extends CommonDao<T>{

    public List<T> findAll() throws DaoException;

    public T findOne(int id) throws DaoException;

    public T create(T t) throws DaoException;

    public void update(T t) throws DaoException;

    public void delete(int id) throws DaoException;
}
