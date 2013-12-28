/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;

import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Book;

/**
 * @author Vyacheslav Bobrov
 */
public class DBProviderImpl implements DBproviderIf {

	/**
	 * 
	 */
	public DBProviderImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.DBproviderIf#update(java.util.List)
	 */
	@Override
	public void update(List<Book> books) {	
		Session session=null;		
		
		try{
			session=HibernateUtil.getSessionFactory().openSession();
			
			for(Book book:books){
				session.beginTransaction();				
				session.saveOrUpdate(book);
				session.getTransaction().commit();
			}
		} finally {
			if(session!=null && session.isOpen())
				session.close();
		}
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.DBproviderIf#load()
	 */
	@Override
	public List<Book> load() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.DBproviderIf#getLastModified(java.lang.String)
	 */
	@Override
	public long getLastModified(String dir) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.DBproviderIf#setLastModified(java.lang.String, long)
	 */
	@Override
	public void setLastModified(String dir, long lastModified) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Book getBookByID(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

}
