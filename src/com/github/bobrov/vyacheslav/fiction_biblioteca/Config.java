/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Конфигурация системы.
 * @author Vyacheslav Bobrov
 */
public abstract class Config {
	Logger logger=Loggers.getInstance().getLogger(Config.class);

	public static final String COULD_NOT_OPEN_CONFIG				=	"Невозможно загрузить конфигурацию, " +
																		"будут использованы значения по умолчанию";
	
	ArrayList<ConfigListenerIf> listeners=new ArrayList<>();
	
	/**Время последней модификации конфигурационного файла*/
	private long lastModified=0l;
	
	/**Время между проверками изменения конфигурационного файла, в миллисекундах*/
	final long TIME_TO_WAIT=500;
	
	/**
	 * Переписать конфигурацию	
	 * @param configFile имя файла конфигурации
	 * @param fieldPref префикс имен полей конфигурации
	 */
	public void saveConfig(/*Object child, */String configFile, String fieldPref){
		Properties props = new Properties();
		try {			
			File file=new File(configFile);			
			
			FileOutputStream os = new FileOutputStream(file);
						
			Field[] fields=this.getClass().getDeclaredFields();			
			for(Field field:fields){
				String fieldName=field.getName();
				if(fieldName.startsWith(fieldPref)){					
					fieldName=fieldName.replace("_", ".");
					props.setProperty(fieldName, (String) field.get(this));
				}
			}
			
			props.storeToXML(os, "", "UTF-8");
			lastModified=System.currentTimeMillis();
			
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {			
			logger.error(Config.COULD_NOT_OPEN_CONFIG, e);
			return;
		}
		
		notifiListeners();
	}
	
	/**
	 * Перечитать конфигурацию	
	 * @param configFile имя файла конфигурации
	 * @param lastModified время модификации файла
	 * @param fieldPref префикс имен полей конфигурации
	 */
	public void reloadConfig(String configFile, String fieldPref){
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
			
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {			
			logger.error(Config.COULD_NOT_OPEN_CONFIG, e);
			return;
		}		
		
		notifiListeners();
	}
	
	public void watchConfig(final String configFile, final String fieldPref) {
		//TODO Переписать на Concurrent
		new Runnable() {
			public void run() {
				while(true){
					reloadConfig(configFile, fieldPref);
					try {
						Thread.sleep(TIME_TO_WAIT);
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}
				}
			}
		}.run();
	}
	
	/**
	 * Добавить слушателя изменений конфигурации
	 * @param listener слушатель изменений конфигурации
	 */
	public void addListener(ConfigListenerIf listener){
		listeners.add(listener);
	}
	
	/**
	 * Удалить слушателя изменений конфигурации
	 * @param listener слушатель изменений конфигурации
	 */
	public void removeListener(ConfigListenerIf listener){
		listeners.remove(listener);
	}
	
	/**
	 * Оповестить всех слушателей об изменении конфигурации
	 */
	void notifiListeners(){
		for(ConfigListenerIf listener:listeners)
			listener.onConfigChange();
	}
}
