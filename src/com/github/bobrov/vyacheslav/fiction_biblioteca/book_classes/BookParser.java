package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class BookParser extends DefaultHandler {
	
	//Названия элементов fb2
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
	
	Author currAuthor;
	
	public BookParser(Book book) {
		this.book=book;
	}

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
			Book.logger.trace("genre="+new String(ch, start, length));
		}
		
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
		
		if(qName.equals(DESCRIPTION)){
			inDescription=false;
			Book.logger.trace("End element "+localName+ " qName="+qName);
		}
		
		if(qName.equals(TITLE_INFO)){
			inTitleInfo=false;
			Book.logger.trace("End element "+localName+ " qName="+qName);
		}
		
		if(qName.equals(GENRE)){
			inGenre=false;
			Book.logger.trace("End element "+localName+ " qName="+qName);
		}
		
		if(inAuthor && qName.equals(AUTHOR)){				
			inAuthor=false;
			book.authors.add(currAuthor);
			Book.logger.trace("Автор="+currAuthor);
			Book.logger.trace("End element "+localName+ " qName="+qName);
		}
		
		if(inFirstName && qName.equals(FIRST_NAME)){
			inFirstName=false;
			Book.logger.trace("End element "+localName+ " qName="+qName);
		}
		
		if(inMiddleName && qName.equals(MIDDLE_NAME)){
			inMiddleName=false;
			Book.logger.trace("End element "+localName+ " qName="+qName);
		}
		
		if(inLastName && qName.equals(LAST_NAME)){
			inLastName=false;
			Book.logger.trace("End element "+localName+ " qName="+qName);
		}
		
		if(qName.equals(BOOK_TITLE)){
			inBookTitle=false;
			Book.logger.trace("book.title="+book.title);
			Book.logger.trace("End element "+localName+ " qName="+qName);
		}
		
		if(qName.equals(ANNOTATION)){
			inAnnotation=false;
			Book.logger.trace("book.annotation="+book.annotation);
			Book.logger.trace("End element "+localName+ " qName="+qName);
		}
		
		if(inP && qName.equals(P)){
			inP=false;
			Book.logger.trace("End element "+localName+ " qName="+qName);
		}
		
		super.endElement(uri, localName, qName);
	}

	@Override
	public void startDocument() throws SAXException {
		Book.logger.trace("Start document");
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		if(qName.equals(DESCRIPTION)){
			inDescription=true;
			Book.logger.trace("Start element "+localName+ " qName="+qName);
		}
		
		if(inDescription){			
			if(qName.equals(TITLE_INFO)){
				inTitleInfo=true;
				Book.logger.trace("Start element "+localName+ " qName="+qName);					
			}
			
			if(inTitleInfo){
				if(qName.equals(AUTHOR)){
					currAuthor=new Author();
					inAuthor=true;
					Book.logger.trace("Start element "+localName+ " qName="+qName);
				}
				
				if(qName.equals(GENRE)){
					inGenre=true;
					Book.logger.trace("Start element "+localName+ " qName="+qName);
				}
				
				if(inAuthor){
					if(qName.equals(FIRST_NAME)){
						inFirstName=true;
						Book.logger.trace("Start element "+localName+ " qName="+qName);
					}
					if(qName.equals(MIDDLE_NAME)){
						inMiddleName=true;
						Book.logger.trace("Start element "+localName+ " qName="+qName);
					}
					if(qName.equals(LAST_NAME)){
						inLastName=true;
						Book.logger.trace("Start element "+localName+ " qName="+qName);
					}
				}
				
				if(qName.equals(BOOK_TITLE)){
					inBookTitle=true;
					Book.logger.trace("Start element "+localName+ " qName="+qName);
				}
				if(qName.equals(ANNOTATION)){
					inAnnotation=true;
					Book.logger.trace("Start element "+localName+ " qName="+qName);
				}
				
				if(inAnnotation && qName.equals(P)){
					inP=true;
					Book.logger.trace("Start element "+localName+ " qName="+qName);
				}
				
				if(qName.equals("coverpage"))
					Book.logger.trace("Start element "+localName+ " qName="+qName);
				if(qName.equals("image"))
					Book.logger.trace("Start element "+localName+ " qName="+qName);
			}
		}
			
		super.startElement(uri, localName, qName, attributes);
	}
	
}