/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.bobrov.vyacheslav.fiction_biblioteca.BiblConfig;

/**
 * Тестирование работы системы настроек приложения
 * @author Vyacheslav Bobrov
 */
public class BiblConfigTest {	
	BiblConfig biblConfig=BiblConfig.getInstance();
	
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
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.BiblConfig#setBibl_log_config(java.lang.String)}.
	 */
	@Test
	public void testSetBibl_log_config() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.BiblConfig#setBibl_log_level(java.lang.String)}.
	 */
	@Test
	public void testSetBibl_log_level() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.Config#watchConfig(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testWatchConfig() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.github.bobrov.vyacheslav.fiction_biblioteca.Config#notifiListeners()}.
	 */
	@Test
	public void testNotifiListeners() {
		fail("Not yet implemented");
	}

}
