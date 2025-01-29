package org.example.util;

import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        try {

            // Cargar variables de entorno
            Dotenv dotenv = Dotenv.load();

            Properties properties = new Properties();

            // Configuración de Hibernate
            properties.put("hibernate.connection.url",
                    System.getenv("DATABASE_URL") != null ?
                            System.getenv("DATABASE_URL") :
                            dotenv.get("DATABASE_URL"));

            properties.put("hibernate.connection.username",
                    System.getenv("DATABASE_USER") != null ?
                            System.getenv("DATABASE_USER") :
                            dotenv.get("DATABASE_USER"));

            properties.put("hibernate.connection.password",
                    System.getenv("DATABASE_PASSWORD") != null ?
                            System.getenv("DATABASE_PASSWORD") :
                            dotenv.get("DATABASE_PASSWORD"));


            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(properties)
                    .build();


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
