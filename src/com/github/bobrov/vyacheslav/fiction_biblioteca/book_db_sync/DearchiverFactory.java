/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync;

/**
 * @author Vyacheslav Bobrov
 *
 */
public class DearchiverFactory {
	static final DearchiverFactory INSTANCE=new DearchiverFactory();
	public static DearchiverFactory getInstance(){
		return INSTANCE;
	}
	DearchiverFactory() {	
	}
	
	final DearchiverImpl dearchiver=new DearchiverImpl();
	
	public DearchiverIf getDearchiver(){
		return dearchiver;
	}
}
