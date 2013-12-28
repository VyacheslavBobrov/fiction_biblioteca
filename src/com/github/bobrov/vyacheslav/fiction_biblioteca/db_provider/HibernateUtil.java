/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Vyacheslav Bobrov
 */

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    
    static {
        try {
        	sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
              e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
