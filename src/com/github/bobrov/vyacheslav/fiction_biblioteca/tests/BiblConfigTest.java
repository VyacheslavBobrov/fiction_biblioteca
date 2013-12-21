/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.tests;

import static org.junit.Assert.*;

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
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.BiblConfig#setBibl_lib_dirs(java.lang.String[])}.
	 * Тестирование корректности записи/чтения из конфигурации списка каталогов библиотек.
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

	/**
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.Config#watchConfig(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testWatchConfig() {
		fail("Not yet implemented");
	}

	boolean listenerNotified;
	/**
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.Config#notifiListeners()}.
	 */
	@Test
	public void testNotifiListeners() {
		listenerNotified=false;
		
		biblConfig.addListener(new ConfigListenerIf() {
			
			@Override
			public void onConfigChange() {
				listenerNotified=true;
			}
		});
		
		String[] libDirs=biblConfig.getBibl_lib_dirs();
		try {
			biblConfig.setBibl_lib_dirs("aaa;bbb".split(";"));
			
			Assert.assertTrue(listenerNotified);
		} finally {
			biblConfig.setBibl_lib_dirs(libDirs);
		}
	}
}
