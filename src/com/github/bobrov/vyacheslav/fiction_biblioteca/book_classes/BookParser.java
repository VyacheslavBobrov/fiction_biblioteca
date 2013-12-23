package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.apache.commons.codec.binary.Base64;

import com.github.bobrov.vyacheslav.fiction_biblioteca.Loggers;

class BookParser extends DefaultHandler {	
	static Logger logger= Loggers.getInstance().getLogger(BookParser.class);
	
	//Названия элементов fb2	
	private static final String NUMBER = "number";
	private static final String NAME = "name";
	private static final String SEQUENCE = "sequence";
	private static final String ID = "id";
	private static final String BINARY = "binary";
	private static final String XLINK_HREF = "xlink:href";
	private static final String IMAGE = "image";
	private static final String COVERPAGE = "coverpage";
	private static final String P = "p";
	private static final String ANNOTATION = "annotation";
	private static final String BOOK_TITLE = "book-title";
	private static final String LAST_NAME = "last-name";
	private static final String MIDDLE_NAME = "middle-name";
	private static final String FIRST_NAME = "first-name";
	private static final String AUTHOR = "author";
	private static final String GENRE = "genre";
	private static final String TITLE_INFO = "title-info";
	private static final String DESCRIPTION = "description";
	
	Book book;
	
	boolean inDescription=false;
	boolean inTitleInfo=false;
	
	boolean inGenre=false;
	
	boolean inAuthor=false;
	boolean inFirstName=false;
	boolean inMiddleName=false;
	boolean inLastName=false;
	
	boolean inBookTitle=false;
	
	boolean inAnnotation=false;
	
	boolean inP=false;
	
	boolean inCoverPage=false;
	boolean inImage=false;
	
	boolean inBinary=false;
	boolean inCoverBynary=false;
	
	boolean inSequence=false;
	
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
		
		if(inAuthor){
			if(inFirstName)
				currAuthor.firstName=new String(ch, start, length);
			if(inMiddleName)
				currAuthor.patrName=new String(ch, start, length);
			if(inLastName)
				currAuthor.lastName=new String(ch, start, length);
		}
		
		if(inBookTitle)
			book.setTitle(new String(ch, start, length));
		
		if(inAnnotation && inP)
			book.addAnnotationLine(new String(ch, start, length));
		
		if(inGenre){
			book.addGenre(new String(ch, start, length));
			logger.trace("genre="+new String(ch, start, length));
		}
		
		if(inBinary && inCoverBynary)
			coverBuilder.append(ch, start, length);
		
		super.characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals(DESCRIPTION))
			inDescription=false;
		
		if(qName.equals(TITLE_INFO))
			inTitleInfo=false;
		
		if(qName.equals(GENRE))
			inGenre=false;
		
		if(inAuthor && qName.equals(AUTHOR)){				
			inAuthor=false;
			book.authors.add(currAuthor);
			logger.trace("Автор="+currAuthor);			
		}
		
		if(inFirstName && qName.equals(FIRST_NAME))
			inFirstName=false;
		
		if(inMiddleName && qName.equals(MIDDLE_NAME))
			inMiddleName=false;
		
		if(inLastName && qName.equals(LAST_NAME))
			inLastName=false;
		
		if(qName.equals(BOOK_TITLE)){
			inBookTitle=false;
			logger.trace("book.title="+book.title);			
		}
		
		if(qName.equals(ANNOTATION)){
			inAnnotation=false;
			logger.trace("book.annotation="+book.annotation);			
		}
		
		if(inP && qName.equals(P))
			inP=false;
		
		if(inCoverPage && qName.equals(COVERPAGE))
			inCoverPage=false;
		
		if(inImage && qName.equals(IMAGE))
			inImage=false;
		
		if(inBinary && inCoverBynary && qName.equals(BINARY)){
			inBinary=false;
			inCoverBynary=false;
			
			byte[] cover=decodeB64(coverBuilder.toString());
			
			book.setCover(cover);
			
			logger.trace("Load cover: "+cover.length+" bytes");						
		}
		
		if(inSequence)
			inSequence=false;
		
		super.endElement(uri, localName, qName);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
				
		if(qName.equals(DESCRIPTION))
			inDescription=true;
		
		if(inDescription){			
			if(qName.equals(TITLE_INFO))
				inTitleInfo=true;
			
			if(inTitleInfo){
				if(qName.equals(AUTHOR)){
					currAuthor=new Author();
					inAuthor=true;
				}
				
				if(qName.equals(GENRE))
					inGenre=true;
				
				if(inAuthor){
					if(qName.equals(FIRST_NAME))
						inFirstName=true;
					if(qName.equals(MIDDLE_NAME))
						inMiddleName=true;
					if(qName.equals(LAST_NAME))
						inLastName=true;
				}
				
				if(qName.equals(BOOK_TITLE))
					inBookTitle=true;
				if(qName.equals(ANNOTATION))
					inAnnotation=true;
				
				if(inAnnotation && qName.equals(P))
					inP=true;
				
				if(qName.equals(COVERPAGE))
					inCoverPage=true;
				
				if(inCoverPage && qName.equals(IMAGE)){
					inImage=true;					
					
					imageId=attributes.getValue(XLINK_HREF).substring(1);
					logger.trace("imageId="+imageId);
				}				
				
				if(qName.equals(SEQUENCE)){
					inSequence=true;
					
					Sequence sequence=book.getSequence();
					
					String name=attributes.getValue(NAME);
					if(name!=null)
						sequence.setName(name);
					
					String number=attributes.getValue(NUMBER);
					if(number!=null){					
						Integer num=Integer.valueOf(number);
						sequence.setNumber(num);
					}
					
					logger.trace("Sequence="+sequence);
				}
			}
		}
		
		if(qName.equals(BINARY)){
			inBinary=true;			
			
			if(imageId.equals(attributes.getValue(ID))){
				inCoverBynary=true;				
				book.setCover(null);
			}
		}
			
		super.startElement(uri, localName, qName, attributes);
	}
	
}