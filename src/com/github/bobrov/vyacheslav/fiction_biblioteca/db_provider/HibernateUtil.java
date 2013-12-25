/**
 * 
 */
package com.github.bobrov.vyacheslav.fiction_biblioteca.db_provider;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * @author Vyacheslav Bobrov
 */

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry;
    
    static {
        try {
        	Configuration configuration = new Configuration();
        	configuration.configure(); // при необходимости нужно указать путь к hibernate.cfg.xml
        	serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).getBootstrapServiceRegistry();     
        	sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
              e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
