/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync;

import java.io.File;
import java.io.InputStream;

/**
 * @author Vyacheslav Bobrov
 */
public interface DearchiverIf {
	public void setArchive(File arch);
	public boolean haveNextEntry();
	public InputStream getEntry();
}
