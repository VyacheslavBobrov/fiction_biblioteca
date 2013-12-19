/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca;

import org.apache.log4j.Logger;

/**
 * @author Vyacheslav Bobrov
 */
public class BiblConfig extends Config {
	static final BiblConfig CONFIG=new BiblConfig();
	public BiblConfig getInstance(){
		return CONFIG;
	}
	BiblConfig(){
		reloadConfig();
	}
	
	Logger logger=Loggers.getInstance().getLogger(BiblConfig.class);
	
	/**Время последней модификации конфигурационного файла*/
	private long lastModified=0l;
	
	final String FIELD_PREF="bibl_";
	/**Имя конфигурационного файла*/
	public static final String CONFIG_FILE_NAME					=	"bibl.config.xml";
		
	/**Имя файла с настройками системы логгирования*/
	private String bibl_log_config			="/log4j.xml";
	/**Уровень логгирования, одно из значений: (ALL|TRACE|WARN|INFO|DEBUG|ERROR|FATAL|OFF)*/ 
	private String bibl_log_level			="TRACE";
		
	
	/**
	 * Перечитать конфигурацию	
	 * @param loadByTimer 
	 * <br/><b>{@code true}</b> - запускается из-под таймера, проверка конфигурации
	 * через {@code CONFIG_CHECK_FREQ} вызовов
	 * <br/><b>{@code false}</b> - проверка по первому вызову, аналогично вызову без параметров
	 */
	public void reloadConfig(){
		reloadConfig(CONFIG_FILE_NAME, lastModified, FIELD_PREF);
		
		Loggers loggers=Loggers.getInstance();
		loggers.setLogConfig(bibl_log_config);
		loggers.setLogLevel(bibl_log_level);
		
		return;
	}
}
