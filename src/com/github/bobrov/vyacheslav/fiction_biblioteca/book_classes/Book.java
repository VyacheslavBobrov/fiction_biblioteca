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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
	
	/**Название книги*/
	String title;
	/**Аннотация*/
	String annotation;
	
	/**Автор(ы) книги*/
	ArrayList<Author> authors;
	/**Серия книги*/
	Series series;
	ArrayList<String> genres;
		
	
	/**Обложка книги*/
	byte[] cover;
	
	static class BookParser extends DefaultHandler {
		Book book;
		
		public BookParser(Book book) {
			this.book=book;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			super.characters(ch, start, length);
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
		}

		@Override
		public void startDocument() throws SAXException {
			logger.trace("Start document");
			super.startDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if(qName.equals("description"))
				logger.trace("Start element "+localName+ " qName="+qName);
			if(qName.equals("title-info"))
				logger.trace("Start element "+localName+ " qName="+qName);
			if(qName.equals("author"))
				logger.trace("Start element "+localName+ " qName="+qName);
			if(qName.equals("first-name"))
				logger.trace("Start element "+localName+ " qName="+qName);
			if(qName.equals("last-name"))
				logger.trace("Start element "+localName+ " qName="+qName);
			if(qName.equals("book-title"))
				logger.trace("Start element "+localName+ " qName="+qName);
			if(qName.equals("annotation"))
				logger.trace("Start element "+localName+ " qName="+qName);
			if(qName.equals("coverpage"))
				logger.trace("Start element "+localName+ " qName="+qName);
			if(qName.equals("image"))
				logger.trace("Start element "+localName+ " qName="+qName);
				
			super.startElement(uri, localName, qName, attributes);
		}
		
	}
	
	static public Book loadFromInputStream(InputStream is){
		Book book=new Book();
		
		SAXParserFactory factory = SAXParserFactory.newInstance();

        factory.setValidating(true);
        factory.setNamespaceAware(false);
        SAXParser parser;
        
        try {
			parser=factory.newSAXParser();
			parser.parse(is, new BookParser(book));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error(PARSING_ERROR, e);
		}
        
        return book;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Book(String title, String annotation, ArrayList<Author> authors,
			Series series, ArrayList<String> genres, byte[] cover) {
		super();
		this.title = title;
		this.annotation = annotation;
		this.authors = authors;
		this.series = series;
		this.genres = genres;
		this.cover = cover;
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
		result = prime * result + ((series == null) ? 0 : series.hashCode());
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
		if (series == null) {
			if (other.series != null)
				return false;
		} else if (!series.equals(other.series))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
