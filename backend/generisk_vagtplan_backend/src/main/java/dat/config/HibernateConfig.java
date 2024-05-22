package dat.config;

import dat.model.*;

import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HibernateConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateConfig.class);

    private static EntityManagerFactory entityManagerFactory;
    private static boolean isTest = false;

    private static EntityManagerFactory buildDevEntityManagerFactory() {
        try {
            Properties properties = new Properties();
            properties.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
            properties.put("hibernate.connection.username", "postgres");
            properties.put("hibernate.connection.password", "postgres");
            properties.put("hibernate.show_sql", "false"); // show sql in console
            properties.put("hibernate.format_sql", "true"); // format sql in console
            properties.put("hibernate.use_sql_comments", "true"); // show sql comments in console

            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"); // dialect for postgresql
            properties.put("hibernate.connection.driver_class", "org.postgresql.Driver"); // driver class for postgresql
            properties.put("hibernate.archive.autodetection", "class"); // hibernate scans for annotated classes
            properties.put("hibernate.current_session_context_class", "thread"); // hibernate current session context
            properties.put("hibernate.hbm2ddl.auto", "update"); // hibernate creates tables based on entities

            // Hibernate Default Pool Configuration
            // https://www.mastertheboss.com/hibernate-jpa/hibernate-configuration/configure-a-connection-pool-with-hibernate/
            properties.put("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");// Maximum waiting time for a connection from the pool
            properties.put("hibernate.hikari.connectionTimeout", "10000"); // Minimum number of ideal connections in the pool
            properties.put("hibernate.hikari.minimumIdle", "5"); // Maximum number of actual connection in the pool
            properties.put("hibernate.hikari.maximumPoolSize", "20"); // Maximum time that a connection is allowed to sit ideal in the pool
            properties.put("hibernate.hikari.idleTimeout", "200000"); // Maximum size of statements that has been prepared
            return buildEntityManagerFactory(properties);
        } catch (Throwable ex) {
            LOGGER.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static EntityManagerFactory buildTestEntityManagerFactory() {
        try {
            Properties properties = new Properties();
            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            properties.put("hibernate.connection.driver_class", "org.testcontainers.jdbc.ContainerDatabaseDriver");
            properties.put("hibernate.connection.url", "jdbc:tc:postgresql:15.3-alpine3.18:///test_db");
            properties.put("hibernate.connection.username", "postgres");
            properties.put("hibernate.connection.password", "postgres");
            properties.put("hibernate.archive.autodetection", "class");
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.hbm2ddl.auto", "create-drop");
            return buildEntityManagerFactory(properties);
        } catch (Throwable ex) {
            LOGGER.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static EntityManagerFactory buildEntityManagerFactory(Properties properties) {
        Configuration config = new Configuration();
        config.setProperties(properties);

        // TODO: asList(X.class, Y.class, Z.class)
        Arrays.asList(
                User.class,
                Role.class,
                ExampleEntity.class,
                Company.class,
                Review.class,
                Shift.class,
                Department.class
        ).forEach(config::addAnnotatedClass);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        SessionFactory sf = config.buildSessionFactory(serviceRegistry);
        return sf.unwrap(EntityManagerFactory.class);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = isTest ? buildTestEntityManagerFactory() : buildDevEntityManagerFactory();
        }

        return entityManagerFactory;
    }

    public static void setTestStatus(boolean isTest) {
        HibernateConfig.isTest = isTest;
    }
}