package net.imshenik.university.dao;

import java.util.List;

interface CommonDao<T> {
    public List<T> findAll() throws DaoException;

    public T findOne(int id) throws DaoException;

    public T create(T t) throws DaoException;

    public void update(T t) throws DaoException;

    public void delete(int id) throws DaoException;
}
