/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.book_db_sync;

import java.util.HashMap;

import org.apache.log4j.Logger;
import com.github.bobrov.vyacheslav.fiction_biblioteca.Loggers;

/**
 * @author Vyacheslav Bobrov
 *
 */
public class DearchiverFactory {
	static final DearchiverFactory INSTANCE=new DearchiverFactory();
	public static DearchiverFactory getInstance(){
		return INSTANCE;
	}
	DearchiverFactory() {
		DearchiverIf arch=getDearchiver();
		
		for(String type:arch.getSupportedTypes())
			archivers.put(type, arch);
		
		UnCompressedFiles unCompressed=getUncompressed();
		for(String type:unCompressed.getSupportedTypes())
			archivers.put(type, unCompressed);
		
	}
	
	static final String TYPE_NOT_SUPPORTED="Не найден архиватор для типа файла: %s";
	
	static Logger logger=Loggers.getInstance().getLogger(DearchiverFactory.class);
	
	HashMap<String, DearchiverIf> archivers=new HashMap<>();
		
	final DearchiverImpl dearchiver=new DearchiverImpl();
	final UnCompressedFiles uncompressed=new UnCompressedFiles();
	
	public UnCompressedFiles getUncompressed() {
		return uncompressed;
	}
	public DearchiverIf getDearchiver(){
		return dearchiver;
	}
	
	public DearchiverIf getDearchiverForFileType(String type){
		if(type==null)
			return getUncompressed();
		
		DearchiverIf arch=archivers.get(type);
		if(arch==null)
			logger.error(String.format(TYPE_NOT_SUPPORTED, type));
		
		return arch;		
	}
}
