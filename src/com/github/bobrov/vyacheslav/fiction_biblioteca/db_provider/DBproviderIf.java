/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider;

import java.math.BigInteger;
import java.util.List;

import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Book;

/**
 * Интерфейс провайдера БД 
 * @author Vyacheslav Bobrov
 */
public interface DBproviderIf {
	/**
	 * Обновить информацию о книгах в БД
	 * @param books список книг для обновления
	 */
	public void update(List<Book> books);
	
	/**
	 * Загрузить список книг из БД
	 * @return список книг
	 */
	public List<Book> load();
	
	/**
	 * Загрузить кигу по ID
	 * @param id идентификатор книги
	 * @return книга
	 */
	public Book getBookByID(BigInteger id);
	
	/**
	 * Получить время последней модификации файла в каталоге с книгами.
	 * Нужно для быстрого поиска добавленных или измененных книг.
	 * @param dir каталог с книгами
	 * @return время последней модификации
	 */
	public long getLastModified(String dir);
	
	/**
	 * Сохранить ремя последней модификации файла в каталоге с книгами.
	 * @param dir каталог с книгами
	 * @param lastModified время последней модификации
	 */
	public void setLastModified(String dir, long lastModified);
}
