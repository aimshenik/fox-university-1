package net.imshenik.university.dao;

import java.io.Serializable;
import java.util.List;

interface CommonDao<T extends Serializable, K extends Serializable> {
    public List<T> findAll() throws DaoException;
    
    public T findOne(K id) throws DaoException;
    
    public T create(T t) throws DaoException;
    
    public void update(T t) throws DaoException;
    
    public void delete(K id) throws DaoException;
}
