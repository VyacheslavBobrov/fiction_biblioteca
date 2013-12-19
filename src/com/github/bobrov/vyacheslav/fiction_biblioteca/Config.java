/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Конфигурация системы.
 * @author Vyacheslav Bobrov
 */
public class Config {
	Logger logger=Loggers.getInstance().getLogger(Config.class);

	public static final String COULD_NOT_OPEN_CONFIG				=	"Невозможно загрузить конфигурацию, " +
																		"будут использованы значения по умолчанию";
	
	/**
	 * Перечитать конфигурацию	
	 * @param loadByTimer 
	 * <br/><b>{@code true}</b> - запускается из-под таймера, проверка конфигурации
	 * через {@code CONFIG_CHECK_FREQ} вызовов
	 * <br/><b>{@code false}</b> - проверка по первому вызову, аналогично вызову без параметров
	 */
	public void reloadConfig(String configFile, long lastModified, String fieldPref){
		Properties props = new Properties();
		try {			
			File file=new File(configFile);
			
			if(lastModified>=file.lastModified())
				return;
			lastModified=file.lastModified();
			
			FileInputStream is = new FileInputStream(file);
			props.loadFromXML(is);					
						
			Field[] fields=this.getClass().getDeclaredFields();			
			for(Field field:fields){
				String fieldName=field.getName();				
				if(fieldName.startsWith(fieldPref)){
					fieldName=fieldName.replace("_", ".");					
					field.set(this, props.getProperty(fieldName, (String) field.get(this)));					
				}
			}			
			return;
			
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {			
			logger.error(Config.COULD_NOT_OPEN_CONFIG, e);
		}		
	}
}
