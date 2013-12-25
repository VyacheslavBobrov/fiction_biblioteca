/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

/**
 * @author Vyacheslav Bobrov
 */
@Entity
@Table(appliesTo = "authors")
public class Author {
	private long id;
	
	String firstName;
	String lastName;
	String patrName;
	
	@Column(name="first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name="last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column(name="patr_name")
	public String getPatrName() {
		return patrName;
	}
	public void setPatrName(String patrName) {
		this.patrName = patrName;
	}
	
	public Author() {
		super();
	}
	public Author(String firstName, String lastName, String patrName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.patrName = patrName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((patrName == null) ? 0 : patrName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (patrName == null) {
			if (other.patrName != null)
				return false;
		} else if (!patrName.equals(other.patrName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Author [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", patrName=" + patrName + "]";
	}
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	@Column(name="ID")
	public long getId() {
		return id;
	}	
	
}
