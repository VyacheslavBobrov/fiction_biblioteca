package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes;

import java.util.Stack;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.apache.commons.codec.binary.Base64;

import com.github.bobrov.vyacheslav.fiction_biblioteca.Loggers;

class BookParser extends DefaultHandler {	
	static Logger logger= Loggers.getInstance().getLogger(BookParser.class);
	
	private static final String NUMBER = "number";
	private static final String NAME = "name";
	private static final String ID = "id";
	private static final String XLINK_HREF = "xlink:href";
	
	enum Elements {
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
		
		static public Elements fromString(String val){
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
	
	Stack<Elements> stackElements=new Stack<>();
		
	String imageId;
	
	Author currAuthor;
	
	public BookParser(Book book) {
		this.book=book;
	}
	
	byte[] decodeB64(String b64){
		return Base64.decodeBase64(b64.getBytes());
	}

	StringBuilder coverBuilder=new StringBuilder();
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		Elements currElement;
		if(stackElements.isEmpty())
			currElement=null;
		else 
			currElement=stackElements.peek();
		
		if(currElement!=null){
			switch(currElement){
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
			default:
				break;
			}
		}
		
		super.characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		Elements newElement=Elements.fromString(qName);
		Elements currElement=null;
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
	
	boolean isCorrectParent(Elements element, Elements parent){
		
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
				if (parent==Elements.DESCRIPTION)
					isCorrect=true;
				break;				
			case SEQUENCE:
			case COVERPAGE:
			case ANNOTATION:				
			case GENRE:
			case BOOK_TITLE:
			case AUTHOR:
				if (parent==Elements.TITLE_INFO)
					isCorrect=true;
				break;				
			case IMAGE:
				if(parent==Elements.COVERPAGE)
					isCorrect=true;
				break;				
			case P:
				if (parent==Elements.ANNOTATION)
					isCorrect=true;
				break;				
	
			case LAST_NAME:
			case MIDDLE_NAME:
			case FIRST_NAME:
				if (parent==Elements.AUTHOR)
					isCorrect=true;
				break;			
			default:				
				break;
			}
		
		return isCorrect;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		Elements newElement=Elements.fromString(qName);
		Elements currElement=null;
		if(!stackElements.isEmpty())
			currElement=stackElements.peek();		
		
		if(isCorrectParent(newElement, currElement)){
			switch(newElement){
				case SEQUENCE:{
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
					break;					
				case BINARY:
					if(imageId.equals(attributes.getValue(ID)))				
						book.setCover(null);
					else
						newElement=null;
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
					
			if(newElement!=null)
				stackElements.push(newElement);
		}
		
		super.startElement(uri, localName, qName, attributes);
	}
	
}