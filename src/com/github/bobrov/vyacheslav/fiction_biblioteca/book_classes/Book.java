/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.github.bobrov.vyacheslav.fiction_biblioteca.Loggers;

/**
 * Класс для работы с книгами библиотеки
 * @author Vyacheslav Bobrov
 */
public class Book {
	static final String PARSING_ERROR="Ошибка парсинга книги";

	static Logger logger=Loggers.getInstance().getLogger(Book.class);
	
	/**
	 * 
	 */
	public Book() {
	}
	
	public Book(String title, String annotation, ArrayList<Author> authors,
			Sequence series, ArrayList<String> genres, byte[] cover) {
		super();
		this.title = title;
		this.annotation = annotation;
		this.authors = authors;
		this.sequence = series;
		this.genres = genres;
		this.cover = cover;
	}
	
	/**Название книги*/
	String title;
	/**Аннотация*/
	String annotation;
	
	/**Автор(ы) книги*/
	ArrayList<Author> authors=new ArrayList<>();
	/**Серия книги*/
	Sequence sequence=new Sequence();
	ArrayList<String> genres=new ArrayList<>();
		
	
	/**Обложка книги*/
	byte[] cover=null;
	
	static public Book loadFromInputStream(InputStream is){
		Book book=new Book();
		
		SAXParserFactory factory = SAXParserFactory.newInstance();

        factory.setValidating(true);
        factory.setNamespaceAware(false);
        SAXParser parser;
        
        try {
			parser=factory.newSAXParser();
			parser.parse(is, new BookFb2Parser(book));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error(PARSING_ERROR, e);
		}
        
        return book;
	}	
		
	public String getAnnotation() {
		return annotation;
	}

	public void addAnnotationLine(String line){
		if(annotation==null)
			annotation=line;
		else
			annotation+="\n"+line;
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

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence series) {
		this.sequence = series;
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
	
	public void addGenre(String genre){
		genres.add(genre);
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((annotation == null) ? 0 : annotation.hashCode());
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + Arrays.hashCode(cover);
		result = prime * result + ((genres == null) ? 0 : genres.hashCode());
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Book other = (Book) obj;
		if (annotation == null) {
			if (other.annotation != null)
				return false;
		} else if (!annotation.equals(other.annotation))
			return false;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (!Arrays.equals(cover, other.cover))
			return false;
		if (genres == null) {
			if (other.genres != null)
				return false;
		} else if (!genres.equals(other.genres))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
