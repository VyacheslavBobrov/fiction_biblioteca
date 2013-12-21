/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Логгеры классов, пул для управления и выдачи классам
 * @author Vyacheslav Bobrov
 */
public class Loggers implements ConfigListenerIf {
	static final Loggers INSTANCE=new Loggers();
	public static Loggers getInstance(){
		return INSTANCE;
	}
	Loggers() {		
	}
	
	/**Таблица используемых логгеров*/
	@SuppressWarnings("rawtypes")
	private Map<Class, Logger> loggers=new HashMap<Class, Logger>();
	
	String logConfig;
	
	Level logLevel=null;
	
	public void setLogConfig(String newLogConfig){
		if(!newLogConfig.equals(logConfig)){
			URL url=Loggers.class.getResource(newLogConfig);
			if(url==null){
				String err=
					"Ошибка конфигурации! " +
					"Невозможно загрузить настройки подсистемы логгирования:"+newLogConfig;
				
				getLogger(Loggers.class).error(err);
				System.err.println(err);
				
				return;
			}
			DOMConfigurator.configure(url);
			logConfig=newLogConfig;
		}
	}

	/**
	 * Получить логгер {@link Logger}. <br/> 
	 * Все объекты системы должны получать логгер у Loggers
	 * @param cl класс, который запрашивает логгер
	 * @return {@link Logger}
	 */
	public Logger getLogger(@SuppressWarnings("rawtypes") Class cl){		
		Logger logger=loggers.get(cl);		
		if(logger==null){
			logger=Logger.getLogger(cl);
			loggers.put(cl, logger);
		}
		return logger;
	}
	
	/**
	 * Обновить уровень логгирования у всех используемых системой логгеров
	 */
	private void updateLogLevels(){	
		for(Logger logger:loggers.values())
			logger.setLevel(logLevel);	
	}
	
	/**
	 * Установить уровень логгирования для всей системы
	 * @param logLevel {@link Level}
	 */
	public void setLogLevel(Level logLevel){	
		if(this.logLevel!=null && this.logLevel.equals(logLevel))
			return;
		
		this.logLevel=logLevel;
		
		Logger logger=getLogger(Loggers.class);
		
		logger.setLevel(Level.INFO);
		logger.info("Уровень логгирования изменен на:"+logLevel);
		
		logger.setLevel(logLevel);
		updateLogLevels();
	}
	
	/**
	 * Установить уровень логгирования для всей системы
	 * @param logLevel {@link String}
	 */
	public void setLogLevel(String logLevel){
		Level newLevel=Level.toLevel(logLevel);
		setLogLevel(newLevel);
	}
	@Override
	public void onConfigChange() {
		BiblConfig config=BiblConfig.getInstance();
		
		setLogConfig(config.getBibl_log_config());
		setLogLevel(config.getBibl_log_level());		
	}
}
