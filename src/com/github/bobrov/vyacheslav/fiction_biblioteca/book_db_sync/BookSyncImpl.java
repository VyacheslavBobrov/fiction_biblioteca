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
	
	ArrayList<File> getModifiedFiles(final long fromModTime, File dir){		
		
		File[] newFiles=dir.listFiles(new FileFilter() {			
			@Override
			public boolean accept(File pathname) {
				if(pathname.lastModified()<=fromModTime)					
					return false;
				
				return true;
			}
		});
		
		ArrayList<File> retFiles=new ArrayList<>(newFiles.length);
		for(File file:newFiles)
			if(file.isDirectory())
				retFiles.addAll(getModifiedFiles(fromModTime, file));
			else
				retFiles.add(file);		
			
		return retFiles;
	}
	
	ArrayList<File> getModifiedFiles(final long fromModTime, String dirName){
		File dir=new File(dirName);
		if(!dir.exists()){
			logger.error(String.format(DIR_NOT_FOUND, dirName));
			return null;
		}
		
		if(dir.lastModified()<=fromModTime)
			return null;
		
		return getModifiedFiles(fromModTime, dir);
	}	
	
	String getFileType(File file){
		String fileName=file.toString();
		
		int lastDot=fileName.lastIndexOf(".");
		if(lastDot==-1)
			return null;
		
		String type=fileName.substring(lastDot);
		return type;
	}
	
	List<Book> loadBooksFromFile(File bookFile){		
		String type=getFileType(bookFile);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync.BookSyncIf#sync(com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider.DBproviderIf, java.util.List)
	 */
	@Override
	public void sync(DBproviderIf provider, List<String> dirs) {
		for(String dir:dirs){
			long lastModified=provider.getLastModified(dir);			
			ArrayList<File> newFiles=getModifiedFiles(lastModified, dir);
			
			if(newFiles==null || newFiles.isEmpty())
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
