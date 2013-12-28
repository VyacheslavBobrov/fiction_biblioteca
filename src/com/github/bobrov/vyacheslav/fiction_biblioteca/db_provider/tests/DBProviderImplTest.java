/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.tests;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Author;
import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Book;
import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Sequence;
import com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.DBProviderImpl;

/**
 * @author Vyacheslav Bobrov
 */
public class DBProviderImplTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.DBProviderImpl#update(java.util.List)}.
	 */
	@Test
	public void testUpdate() {
		ArrayList<Author> authors=new ArrayList<>();
		
		authors.add(new Author("Тест1", "Тестович1", "Тестов1"));		
		authors.add(new Author("Тест2", "Тестович2", "Тестов2"));
		authors.add(new Author("Тест3", "Тестович3", "Тестов3"));
		
		Sequence sequence=new Sequence("Тестовая серия");
		
		ArrayList<String> genres=new ArrayList<>();
		genres.add("детектив");
		genres.add("комедия");
		genres.add("треш");
		genres.add("угар");
		
		Book book=new Book("Test book title", "annotatin", authors, sequence, genres, null);
		ArrayList<Book> books=new ArrayList<>();
		books.add(book);
		
		DBProviderImpl dbProviderImpl=new DBProviderImpl();
		
		dbProviderImpl.update(books);
		//dbProviderImpl.load();
		
	}

}
