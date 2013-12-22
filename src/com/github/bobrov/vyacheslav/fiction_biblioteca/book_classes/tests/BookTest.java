/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.tests;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Book;
import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Author;

/**
 * Тестирование класса Book
 * @author Vyacheslav Bobrov
 */
public class BookTest {
	static final String FILE_BOOK_FB2="Berkeshi_Sovremennyiy_vengerskiy_detektiv.222582.fb2";

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
	 * Тестирование корректности загрузки книги из потока. Проверка на тестовых данных.
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Book#loadFromInputStream(java.io.InputStream)}.
	 */
	@Test
	public void testLoadFromInputStream() {
		InputStream is=BookTest.class.getResourceAsStream(FILE_BOOK_FB2);
		Book actualBook=Book.loadFromInputStream(is);
		
		ArrayList<Author> expectAuthors=new ArrayList<>();
		expectAuthors.add(new Author("Андраш", "Беркеши", ""));
		expectAuthors.add(new Author("Ласло", "Беркеши", ""));
		expectAuthors.add(new Author("Тибор", "Череш", ""));
		
		ArrayList<String> expectGenres=new ArrayList<>();
		expectGenres.add("detective");
						
		Book expectBook=new Book(
			"Современный венгерский детектив", 
			  "Три произведения трех писателей соседствуют в одной книге. "
			+ "Их объединяет общность жанра и общий подход авторов к явлениям, отражающим "
			+ "те или иные стороны жизни современной Венгрии. И Андраша Беркеши, и Тибора Череша, "
			+ "и Ласло Андраша волнуют, в общем-то, очень схожие проблемы. Какие положительные и "
			+ "отрицательные влияния испытывает на себе венгерская молодежь? "
			+ "Что способствует воспитанию в ней моральной и политической стойкости, "
			+ "целеустремленности? "
			+ "Авторы сборника касаются и проблемы взаимоотношения поколений, проблемы отцов и "
			+ "детей. \n"
			+ "Другой круг проблем вызван расширением контактов Венгрии с зарубежными странами. "
			+ "\nДостаточно разнообразна и география представленных в сборнике произведении: "
			+ "читателю предлагается побывать и в столице Венгрии — Будапеште, и в одном из "
			+ "глубинных сел страны, и в небольшом курортном городке на берегу озера Балатон. "
			+ "Соответственно читатель познакомится и с представителями различных слоев населения, "
			+ "окунется в современную венгерскую действительность — и в этом, думается, "
			+ "несомненное познавательное значение книги. \n"
			+ "Но, разумеется, любое произведение детективного жанра должно, прежде всего, "
			+ "увлечь читателя и не отпускать до тех пор; пока он не закроет последнюю страницу. "
			+ "Надеемся, что и в этом смысле сборник \"Современный венгерский детектив\" не "
			+ "обманет его ожиданий. \n", 
			expectAuthors, null, expectGenres, null);
		//TODO cover пока не проверяем
		
		Assert.assertEquals(expectBook, actualBook);
		//Assert.assertTrue(expectBook.equals(actualBook));
					
	}

}
