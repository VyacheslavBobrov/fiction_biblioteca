/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync;

/**
 * @author Vyacheslav Bobrov
 */
public class BookSyncFactory {
	public static final BookSyncFactory INSTANCE=new BookSyncFactory();
	public static BookSyncFactory getInstance(){
		return INSTANCE;
	}

	/**
	 * 
	 */
	public BookSyncFactory() {		
	}
	
	final BookSyncImpl bookSyncImpl=new BookSyncImpl();  

	public BookSyncIf getBookSync(){
		return bookSyncImpl;
	}

}
