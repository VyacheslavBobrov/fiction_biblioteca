/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.tests;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.bobrov.vyacheslav.fiction_biblioteca.Loggers;
import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Book;
import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Author;
import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Sequence;

/**
 * Тестирование класса Book
 * @author Vyacheslav Bobrov
 */
public class BookTest {	
	static Logger logger=Loggers.getInstance().getLogger(BookTest.class);
	
	private static final String GENRE = "detective";
	private static final Author AUTHOR3 = new Author("Тибор", "Череш", null);
	private static final Author AUTHOR2 = new Author("Ласло", "Андраш", null);
	private static final Author AUTHOR1 = new Author("Андраш", "Беркеши", null);
	private static final String BOOK_ANNOTATION = 
		"Три произведения трех писателей соседствуют в одной книге. " +
		"Их объединяет общность жанра и общий подход авторов к явлениям, отражающим " +
		"те или иные стороны жизни современной Венгрии. И Андраша Беркеши, и Тибора Череша, " +
		"и Ласло Андраша волнуют, в общем-то, очень схожие проблемы. Какие положительные и " +
		"отрицательные влияния испытывает на себе венгерская молодежь? " +
		"Что способствует воспитанию в ней моральной и политической стойкости, " +
		"целеустремленности? " +
		"Авторы сборника касаются и проблемы взаимоотношения поколений, проблемы \"отцов и " +
		"детей\". \n" +
		"Другой круг проблем вызван расширением контактов Венгрии с зарубежными странами. " +
		"\nДостаточно разнообразна и география представленных в сборнике произведении: " +
		"читателю предлагается побывать и в столице Венгрии — Будапеште, и в одном из " +
		"глубинных сел страны, и в небольшом курортном городке на берегу озера Балатон. " +
		"Соответственно читатель познакомится и с представителями различных слоев населения, " +
		"окунется в современную венгерскую действительность — и в этом, думается, " +
		"несомненное познавательное значение книги. \n" +
		"Но, разумеется, любое произведение детективного жанра должно, прежде всего, " +
		"увлечь читателя и не отпускать до тех пор; пока он не закроет последнюю страницу. " +
		"Надеемся, что и в этом смысле сборник \"Современный венгерский детектив\" не " +
		"обманет его ожиданий. ";
	private static final String BOOK_TITLE = "Современный венгерский детектив";
	private static final String FILE_BOOK_FB2="Berkeshi_Sovremennyiy_vengerskiy_detektiv.222582.fb2";
	private static String COVER_MD5="9e3036b787a51e1e072e410088aa4dd2";
	private static String SEQUENCE_NAME="На прорыв времени!";
	private static Integer SEQUENCE_NUMBER=1;

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
	
	String getMd5(byte[] bytes) throws NoSuchAlgorithmException{
		MessageDigest md5=MessageDigest.getInstance("MD5");
		
		byte[] digest=md5.digest(bytes);
		
		String md5s="";
		for(byte b:digest)
			md5s+=String.format("%02x", b);
		
		return md5s;
	}

	/**
	 * Тестирование корректности загрузки книги из потока. Проверка на тестовых данных.
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Book#loadFromInputStream(java.io.InputStream)}.
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void testLoadFromInputStream() throws IOException, NoSuchAlgorithmException {
		InputStream is=BookTest.class.getResourceAsStream(FILE_BOOK_FB2);
		Book actualBook=Book.loadFromInputStream(is);
		is.close();
		
		ArrayList<Author> actualAuthors=actualBook.getAuthors();
		
		Assert.assertEquals(3, actualAuthors.size());
		Assert.assertTrue(actualAuthors.contains(AUTHOR1));
		Assert.assertTrue(actualAuthors.contains(AUTHOR2));
		Assert.assertTrue(actualAuthors.contains(AUTHOR3));
				
		ArrayList<String> actualGenres=actualBook.getGenres();
		Assert.assertEquals(1, actualGenres.size());
		Assert.assertTrue(actualGenres.contains(GENRE));
		
		Assert.assertEquals(BOOK_TITLE, actualBook.getTitle());
		Assert.assertEquals(BOOK_ANNOTATION, actualBook.getAnnotation());
		
		Sequence sequence=actualBook.getSequence();
		Assert.assertEquals(SEQUENCE_NAME, sequence.getName());
		Assert.assertEquals(SEQUENCE_NUMBER, sequence.getNumber());
		
		/*FileOutputStream os = new FileOutputStream("cover.png");
		os.write(actualBook.getCover());
		os.close();*/		
		String actualCoverMD5=getMd5(actualBook.getCover());

		Assert.assertEquals(COVER_MD5, actualCoverMD5);
	}

}
