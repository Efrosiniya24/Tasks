package com.example.Tasks.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.PostgreSQLDialect;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();
    private static StandardServiceRegistry registry;

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
            Map<String, Object> settings = new HashMap<>();
            settings.put(AvailableSettings.DRIVER, "org.postgresql.Driver");
            settings.put(AvailableSettings.URL, "jdbc:postgresql://localhost:5432/Tasks");
            settings.put(AvailableSettings.USER, "postgres");
            settings.put(AvailableSettings.PASS, "1111");
            settings.put(AvailableSettings.DIALECT, PostgreSQLDialect.class.getName());
            settings.put(AvailableSettings.SHOW_SQL, "true");
            settings.put(AvailableSettings.HBM2DDL_AUTO, "update");

            registryBuilder.applySettings(settings);

            registry = registryBuilder.build();

            MetadataSources sources = new MetadataSources(registry);

            Metadata metadata = sources.getMetadataBuilder().build();

            return metadata.getSessionFactoryBuilder().build();

        } catch (Exception e) {
            e.printStackTrace();
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed." + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
