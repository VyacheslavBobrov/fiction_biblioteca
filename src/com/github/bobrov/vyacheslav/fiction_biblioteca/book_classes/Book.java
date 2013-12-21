/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes;

import java.util.ArrayList;

/**
 * Класс для работы с книгами библиотеки
 * @author Vyacheslav Bobrov
 */
public class Book {

	/**
	 * 
	 */
	public Book() {
	}
	
	/**Название книги*/
	String name;
	/**Аннотация*/
	String annotation;
	
	/**Автор(ы) книги*/
	ArrayList<Author> authors;
	/**Серия книги*/
	Series series;
	ArrayList<String> genres;
	
	/**Обложка книги*/
	byte[] cover;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public byte[] getCover() {
		return cover;
	}

	public void setCover(byte[] cover) {
		this.cover = cover;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}
}
