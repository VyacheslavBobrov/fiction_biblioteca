/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes;

/**
 * @author Vyacheslav Bobrov
 *
 */
public class Author {
	String firstName;
	String lastName;
	String patrName;
	
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
	public String getPatrName() {
		return patrName;
	}
	public void setPatrName(String patrName) {
		this.patrName = patrName;
	}
	
	public Author() {
		super();
		// TODO Auto-generated constructor stub
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
		return "Author [firstName=" + firstName + ", lastName=" + lastName
				+ ", patrName=" + patrName + "]";
	}
	
	
}
