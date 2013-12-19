/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider;

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
}
