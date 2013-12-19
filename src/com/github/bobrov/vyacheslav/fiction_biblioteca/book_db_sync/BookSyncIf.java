/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync;

import java.util.List;

import com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.DBproviderIf;

/**
 * Интерфейс подсистемы синхронизации списка книг в ФС и БД
 * @author Vyacheslav Bobrov
 */
public interface BookSyncIf {
	/**
	 * Синхронизировать список книг в каталогах dirs со списком книг в базе данных
	 * @param provider провайдер БД
	 * @param dirs список каталогов с книгами
	 */
	public void sync(DBproviderIf provider, List<String> dirs);
}
