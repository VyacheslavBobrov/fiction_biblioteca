/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.tests;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.bobrov.vyacheslav.fiction_biblioteca.BiblConfig;
import com.github.bobrov.vyacheslav.fiction_biblioteca.ConfigListenerIf;
import com.github.bobrov.vyacheslav.fiction_biblioteca.Loggers;

/**
 * Тестирование работы системы настроек приложения
 * @author Vyacheslav Bobrov
 */
public class BiblConfigTest {	
	BiblConfig biblConfig=BiblConfig.getInstance();
	
	Logger logger=Loggers.getInstance().getLogger(BiblConfigTest.class);
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
		
	/**
	 * Тестирование корректности записи/чтения из конфигурации списка каталогов библиотек.
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.BiblConfig#setBibl_lib_dirs(java.lang.String[])}.
	 */
	@Test
	public void testSetBibl_lib_dirs() {		
		String[] oldVal=biblConfig.getBibl_lib_dirs();
		
		try{
			final String[] DIRS="test_lib_dir1;test_lib_dir2;test_lib_dir3".split(";");
			biblConfig.setBibl_lib_dirs(DIRS);
			
			String[] actualDirs=biblConfig.getBibl_lib_dirs();
			
			Assert.assertArrayEquals(DIRS, actualDirs);
		} finally{
			biblConfig.setBibl_lib_dirs(oldVal); //восстановим старое значение
		}
	}

	/**
	 * Тест корректности изменения уровня логгирования системы
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.BiblConfig#setBibl_log_level(java.lang.String)}.
	 */
	@Test
	public void testSetBibl_log_level() {
		Level level=logger.getLevel();
		
		try {
			final String NEW_LEVEL="ERROR";
			biblConfig.setBibl_log_level(NEW_LEVEL);
			
			Assert.assertEquals(NEW_LEVEL, logger.getLevel().toString());
		} finally {
			biblConfig.setBibl_log_level(level.toString());
		}
	}
	
	class TestListener implements ConfigListenerIf {
		boolean listenerNotified=false;
		
		@Override
		public void onConfigChange() {
			logger.trace("Вызван слушатель конфигуратора");
			listenerNotified=true;
		}
		
	}	
	
	/**
	 * Тест вызова слушателя конфигурации
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.Config#notifiListeners()}.
	 */
	@Test
	public void testNotifiListeners() {
		TestListener testListener=new TestListener();
		
		biblConfig.addListener(testListener);
		
		String[] libDirs=biblConfig.getBibl_lib_dirs();
		try {
			biblConfig.setBibl_lib_dirs("aaa;bbb".split(";"));

			biblConfig.removeListener(testListener);
			
			Assert.assertTrue(testListener.listenerNotified);
		} finally {
			biblConfig.setBibl_lib_dirs(libDirs);
		}
	}
	
	/**
	 * Тестирование слежения за файлом конфигурации - принудительное изменение времени модификации
	 * с проверкой вызова слушателя
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.Config#watchConfig(java.lang.String, java.lang.String)}.
	 * @throws InterruptedException 
	 */
	@Test
	public void testWatchConfig() throws InterruptedException {		
		TestListener testListener=new TestListener();		
		biblConfig.addListener(testListener);
		try {
			Thread.sleep(BiblConfig.TIME_TO_WAIT*4);			
			File configFile=new File(BiblConfig.CONFIG_FILE_NAME);
			configFile.setLastModified(System.currentTimeMillis()+BiblConfig.TIME_TO_WAIT);			
			Thread.sleep(BiblConfig.TIME_TO_WAIT*4);
			Assert.assertTrue(testListener.listenerNotified);
		} finally {
			biblConfig.removeListener(testListener);
		}		
	}
}
