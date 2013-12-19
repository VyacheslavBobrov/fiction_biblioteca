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
	static public BiblConfig getInstance(){
		return CONFIG;
	}
	BiblConfig(){
		/**Подпишемся на получение сообщений об изменении конфигурации
		 * в виде исключения тут, что-бы исключить зацикливание при инициализации
		 * конфигурации и логгера
		 * */
		addListener(Loggers.getInstance());
		
		watchConfig(CONFIG_FILE_NAME, FIELD_PREF);
	}
	
	Logger logger=Loggers.getInstance().getLogger(BiblConfig.class);
	
	final String FIELD_PREF="bibl_";
	/**Имя конфигурационного файла*/
	public static final String CONFIG_FILE_NAME					=	"bibl.config.xml";
	
	final String LOG_LEVEL_PATT="(ALL|TRACE|WARN|INFO|DEBUG|ERROR|FATAL|OFF)";
	final String ERROR_LOG_LEVEL="Ошибка в конфигурации - заданный уровень логгирования (%s),"
			+ " не совпадает ни с одним из допустимых значений: "
			+ LOG_LEVEL_PATT;	
	
	/**Имя файла с настройками системы логгирования*/
	private String bibl_log_config			="/log4j.xml";
	/**Уровень логгирования, одно из значений: (ALL|TRACE|WARN|INFO|DEBUG|ERROR|FATAL|OFF)*/ 
	private String bibl_log_level			="TRACE";
	
	/**
	 * Список каталогов с книгами, разделенный точкой с запятой ';'
	 */
	private String bibl_lib_dirs="~/books";
		
	/**
	 * Получить список каталогов с книгами
	 * @return список каталогов с книгами
	 */
	public String[] getBibl_lib_dirs() {
		return bibl_lib_dirs.split(";");
	}
	/**
	 * Задать список каталогов с книгами
	 * @param bibl_lib_dirs
	 */
	public void setBibl_lib_dirs(String[] bibl_lib_dirs) {
		String dirs="";
		for(String dir:bibl_lib_dirs)
			dirs+=dir+";";
			
		this.bibl_lib_dirs = dirs;
		saveConfig(CONFIG_FILE_NAME, FIELD_PREF);
	}
	/**
	 * Получить имя файла настроек лога
	 * @return имя файла настроек лога
	 */
	public String getBibl_log_config() {
		return bibl_log_config;
	}
	/**
	 * Задать имя файла настроек лога
	 * @param имя файла настроек лога
	 */
	public void setBibl_log_config(String bibl_log_config) {
		this.bibl_log_config = bibl_log_config;
		saveConfig(CONFIG_FILE_NAME, FIELD_PREF);
	}
	/**
	 * Получить уровень логгирования
	 * @return уровень логгирования
	 */
	public String getBibl_log_level() {
		return bibl_log_level;
	}
	/**
	 * Задать уровень логгирования
	 * @param уровень логгирования
	 */
	public void setBibl_log_level(String bibl_log_level) {
		if(!bibl_log_level.matches(LOG_LEVEL_PATT))
			logger.error(String.format(ERROR_LOG_LEVEL, bibl_log_level));
		this.bibl_log_level = bibl_log_level;
		saveConfig(CONFIG_FILE_NAME, FIELD_PREF);
	}
}
