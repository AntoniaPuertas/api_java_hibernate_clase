package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        try {
            Configuration configuration = new Configuration().configure();

            // Add debug logs
            System.out.println("Connection URL: " + configuration.getProperty("hibernate.connection.url"));
            System.out.println("Dialect: " + configuration.getProperty("hibernate.dialect"));

            return configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

/*
        if (sessionFactory == null) {
            try {
                //crear registro
                registry = new StandardServiceRegistryBuilder().build();
                //crear MetadataSources
                MetadataSources sources = new MetadataSources(registry);
                //crear Metadata
                Metadata metadata = sources.getMetadataBuilder().build();
                //crear SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;


 */
    }

    public static void shutdown(){
        if(registry != null){
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
