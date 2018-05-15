package net.imshenik.university.dao;

import java.util.List;

public interface Dao<T> {
    public List<T> findAll() throws DaoException;

    public T findOne(int id) throws DaoException;

    public T create(Object... o) throws DaoException;

    public void update(int id, Object... o) throws DaoException;

    public void update(int id) throws DaoException;
}
