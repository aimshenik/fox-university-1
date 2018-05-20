package net.imshenik.university.domain;

import java.io.Serializable;

public class Student implements Serializable {
	private int id;
	private int groupId;
	private String firstName;
	private String lastName;

	public Student() {
	}

	public Student(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(int id, String firstName, String lastName, int group_id) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.groupId = group_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Student student = (Student) o;
		return id == student.id;
	}

	@Override
	public int hashCode() {
		return id * 31;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", groupId=" + groupId + ", firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}
}
