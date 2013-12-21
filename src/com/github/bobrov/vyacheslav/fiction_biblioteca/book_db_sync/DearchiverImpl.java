/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync;

import java.io.File;
import java.io.InputStream;

/**
 * @author vyacheslav
 *
 */
public class DearchiverImpl implements DearchiverIf {
	File arch;

	@Override
	public void setArchive(File arch) {
		this.arch=arch;
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync.DearchiverIf#haveNextEntry()
	 */
	@Override
	public boolean haveNextEntry() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync.DearchiverIf#getEntry()
	 */
	@Override
	public InputStream getEntry() {
		// TODO Auto-generated method stub
		return null;
	}
}
