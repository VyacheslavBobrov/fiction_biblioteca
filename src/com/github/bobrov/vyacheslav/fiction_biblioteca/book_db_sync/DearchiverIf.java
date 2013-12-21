/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Vyacheslav Bobrov
 */
public interface DearchiverIf {
	public String[] getSupportedTypes();
	public void setArchive(File arch) throws FileNotFoundException;
	public boolean haveNextEntry() throws IOException;
	public InputStream getEntry();
}
