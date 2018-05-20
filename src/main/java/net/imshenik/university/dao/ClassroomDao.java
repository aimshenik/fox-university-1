package net.imshenik.university.dao;

import net.imshenik.university.domain.Classroom;

public interface ClassroomDao extends CommonDao<Classroom> {
	public Classroom findOne(String building, String number) throws DaoException;
}
