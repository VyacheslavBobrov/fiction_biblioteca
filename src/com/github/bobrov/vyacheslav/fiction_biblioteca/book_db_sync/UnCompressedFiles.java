/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author vyacheslav
 *
 */
public class UnCompressedFiles implements DearchiverIf {
	InputStream is;
	int entryCount=1;

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync.DearchiverIf#getSupportedTypes()
	 */
	@Override
	public String[] getSupportedTypes() {
		String[] ret={"fb2"};
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync.DearchiverIf#setArchive(java.io.File)
	 */
	@Override
	public void setArchive(File arch) throws FileNotFoundException {
		is=new FileInputStream(arch);
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync.DearchiverIf#haveNextEntry()
	 */
	@Override
	public boolean haveNextEntry() throws IOException {
		return --entryCount>=0;
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync.DearchiverIf#getEntry()
	 */
	@Override
	public InputStream getEntry() {		
		return is;
	}

}
