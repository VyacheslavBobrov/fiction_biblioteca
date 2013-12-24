package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes;

import java.util.Stack;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.apache.commons.codec.binary.Base64;

import com.github.bobrov.vyacheslav.fiction_biblioteca.Loggers;

class BookFb2Parser extends DefaultHandler {	
	static Logger logger= Loggers.getInstance().getLogger(BookFb2Parser.class);
	
	private static final String NUMBER = "number";
	private static final String NAME = "name";
	private static final String ID = "id";
	private static final String XLINK_HREF = "xlink:href";
	
	enum Element {
		SEQUENCE,
		BINARY,
		IMAGE,
		COVERPAGE,
		P,
		ANNOTATION,
		BOOK_TITLE,
		LAST_NAME,
		MIDDLE_NAME,
		FIRST_NAME,
		AUTHOR,
		GENRE,
		TITLE_INFO,
		DESCRIPTION;
		
		static public Element fromString(String val){
			if (val.equals("sequence")) 
				return SEQUENCE;
			else if (val.equals("binary")) 
				return BINARY;
			else if (val.equals("image")) 
				return IMAGE;
			else if (val.equals("coverpage")) 
				return COVERPAGE;
			else if (val.equals("p")) 
				return P;
			else if (val.equals("annotation")) 
				return ANNOTATION;
			else if (val.equals("book-title")) 
				return BOOK_TITLE;
			else if (val.equals("last-name")) 
				return LAST_NAME;
			else if (val.equals("middle-name")) 
				return MIDDLE_NAME;
			else if (val.equals("first-name")) 
				return FIRST_NAME;
			else if (val.equals("author")) 
				return AUTHOR;
			else if (val.equals("genre")) 
				return GENRE;
			else if (val.equals("title-info")) 
				return TITLE_INFO;
			else if (val.equals("description")) 
				return DESCRIPTION;
			
			return null;
		}
	};
		
	Book book;
	
	Stack<Element> stackElements=new Stack<>();
		
	String imageId;
	
	Author currAuthor;
	
	public BookFb2Parser(Book book) {
		this.book=book;
	}
	
	byte[] decodeB64(String b64){
		return Base64.decodeBase64(b64.getBytes());
	}

	StringBuilder coverBuilder=new StringBuilder();
	
	/**
	 * Загрузить данные при необходимости
	 * @param element текущий элемент
	 * @param ch массив символов
	 * @param start начало
	 * @param length длина
	 */
	void loadChars(Element element, char[] ch, int start, int length){
		if(element==null)
			return;		
		
		switch(element){
			case BINARY:
				coverBuilder.append(ch, start, length);
				break;
			case P:
				book.addAnnotationLine(new String(ch, start, length));
				break;
			case BOOK_TITLE:
				book.setTitle(new String(ch, start, length));
				break;
			case LAST_NAME:
				currAuthor.lastName=new String(ch, start, length);
				break;
			case MIDDLE_NAME:
				currAuthor.patrName=new String(ch, start, length);
				break;
			case FIRST_NAME:
				currAuthor.firstName=new String(ch, start, length);
				break;
			case GENRE:
				book.addGenre(new String(ch, start, length));
				break;
		default:
			break;
		}		
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		Element currElement;
		if(stackElements.isEmpty())
			currElement=null;
		else 
			currElement=stackElements.peek();
		
		loadChars(currElement, ch, start, length);
		
		super.characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		Element newElement=Element.fromString(qName);
		Element currElement=null;
		if(!stackElements.isEmpty())
			currElement=stackElements.peek();
		
		if(newElement!=null && currElement==newElement) {
			switch(newElement){
				case AUTHOR:
					book.authors.add(currAuthor);
					break;					
				case BINARY:
					byte[] cover=decodeB64(coverBuilder.toString());			
					book.setCover(cover);					
					break;
				default:
					break;	
			}		
			stackElements.pop();
		}
		
		super.endElement(uri, localName, qName);
	}
	
	/**
	 * Проверка корректности родителя элемента 
	 * (в терминах необходимых нам данных, а не схемы данных xml!)
	 * @param element открываемый элемент
	 * @param parent родитель элемента
	 * @return true - элемент нас интересует, false - нет
	 */
	boolean isCorrectParent(Element element, Element parent){
		
		if(element==null)
			return false;
		
		boolean isCorrect=false;
		
		switch(element){
			case DESCRIPTION:
			case BINARY:
				if(parent==null)
					isCorrect=true;
				break;
			case TITLE_INFO:
				if (parent==Element.DESCRIPTION)
					isCorrect=true;
				break;				
			case SEQUENCE:
			case COVERPAGE:
			case ANNOTATION:				
			case GENRE:
			case BOOK_TITLE:
			case AUTHOR:
				if (parent==Element.TITLE_INFO)
					isCorrect=true;
				break;				
			case IMAGE:
				if(parent==Element.COVERPAGE)
					isCorrect=true;
				break;				
			case P:
				if (parent==Element.ANNOTATION)
					isCorrect=true;
				break;				
	
			case LAST_NAME:
			case MIDDLE_NAME:
			case FIRST_NAME:
				if (parent==Element.AUTHOR)
					isCorrect=true;
				break;			
			default:				
				break;
			}
		
		return isCorrect;
	}
	
	/**
	 * Загрузка серии книги из аттрибутов элемента
	 * @param attributes аттрибуты
	 */
	void loadSequence(Attributes attributes){
		Sequence sequence=book.getSequence();
		
		String name=attributes.getValue(NAME);
		if(name!=null)
			sequence.setName(name);
		
		String number=attributes.getValue(NUMBER);
		if(number!=null){					
			Integer num=Integer.valueOf(number);
			sequence.setNumber(num);
		}
	}
	
	/**
	 * Настройка элемента перед стартом 
	 * @param element стартующий элемент
	 * @param attributes аттрибуты элемента
	 * @return true - старт разрешен, false - старт отменяется
	 */
	boolean tuneElementBeforeStart(Element element, Attributes attributes){
		boolean tuneOK=true;
		
		switch(element){
			case SEQUENCE:
				loadSequence(attributes);
				break;					
			case BINARY:
				if(imageId.equals(attributes.getValue(ID)))				
					book.setCover(null);
				else
					tuneOK=false;
				break;					
			case IMAGE:
				imageId=attributes.getValue(XLINK_HREF).substring(1);
				break;
			case AUTHOR:					
				currAuthor=new Author();
				break;
			default:
				break;
		}
		
		return tuneOK;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		Element newElement=Element.fromString(qName);
		Element currElement=null;
		if(!stackElements.isEmpty())
			currElement=stackElements.peek();		
		
		if(
			isCorrectParent(newElement, currElement) && 
			tuneElementBeforeStart(newElement, attributes))
			stackElements.push(newElement);
				
		super.startElement(uri, localName, qName, attributes);
	}
	
}