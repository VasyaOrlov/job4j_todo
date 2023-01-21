package ru.job4j.todo.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HibernateConfiguration - конфигурационный класс приложения
 */
@Configuration
public class HibernateConfiguration {

    /**
     * метод создания объекта конфигуратора.
     * В нем происходит создания пулов, загрузка кешей, проверка моделей
     * @return - объект SessionFactory
     */
    @Bean(destroyMethod = "close")
    public SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

}
