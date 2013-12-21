/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.bobrov.vyacheslav.fiction_biblioteca.Loggers;
import com.github.bobrov.vyacheslav.fiction_biblioteca.book_classes.Book;
import com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.DBproviderIf;

/**
 * @author Vyacheslav Bobrov
 */
public class BookSyncImpl implements BookSyncIf {
	Logger logger=Loggers.getInstance().getLogger(BookSyncImpl.class);
	
	static final String DIR_NOT_FOUND="Каталог с книгами (%s) не существует!";

	public BookSyncImpl() {
	}
	
	File[] getModifiedFiles(final long fromModTime, String dirName){
		File dir=new File(dirName);
		if(!dir.exists()){
			logger.error(String.format(DIR_NOT_FOUND, dirName));
			return null;
		}
		
		File[] newFiles=dir.listFiles(new FileFilter() {			
			@Override
			public boolean accept(File pathname) {
				if(pathname.lastModified()<=fromModTime)					
					return false;
				
				return true;
			}
		});
		
		return newFiles;
	}	
	
	List<Book> loadBooksFromFile(File bookFile){
		return null; //TODO заглушка
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync.BookSyncIf#sync(com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.DBproviderIf, java.util.List)
	 */
	@Override
	public void sync(DBproviderIf provider, List<String> dirs) {
		for(String dir:dirs){
			long lastModified=provider.getLastModified(dir);			
			File[] newFiles=getModifiedFiles(lastModified, dir);
			
			if(newFiles==null || newFiles.length==0)
				continue;
			
			ArrayList<Book> books=new ArrayList<>();
			for(File bookFile:newFiles)
				books.addAll(loadBooksFromFile(bookFile));			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
}
