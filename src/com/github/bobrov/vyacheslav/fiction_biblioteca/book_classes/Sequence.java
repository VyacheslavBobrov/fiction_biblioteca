/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes;

/**
 * Серия книг
 * @author Vyacheslav Bobrov
 */
public class Sequence {
	String name;
	int number=1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sequence(String name) {
		super();
		this.name = name;
	}

	public Sequence() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Sequence other = (Sequence) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Sequence [name=" + name + ", number=" + number + "]";
	}
}
