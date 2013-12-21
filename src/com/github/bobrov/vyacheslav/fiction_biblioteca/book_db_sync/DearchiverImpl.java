/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Vyacheslav Bobrov
 */
public class DearchiverImpl implements DearchiverIf {
	ZipInputStream zis;
	ZipEntry currEntry;
	
	@Override
	public void setArchive(File arch) throws FileNotFoundException {		
		FileInputStream fis=new FileInputStream(arch);
		zis=new ZipInputStream(fis);
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync.DearchiverIf#haveNextEntry()
	 */
	@Override
	public boolean haveNextEntry() throws IOException {
		currEntry=zis.getNextEntry();
		return currEntry!=null;
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync.DearchiverIf#getEntry()
	 */
	@Override
	public InputStream getEntry() {		
		return zis;
	}

	@Override
	public String[] getSupportedTypes() {
		String[] types={"zip"};
		return types;
	}
}
