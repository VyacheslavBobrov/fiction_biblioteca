/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
			// TODO Auto-generated method stub
			super.startDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
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
