package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class TaskRepositoryTest {
    SessionFactory sf = new MetadataSources(new StandardServiceRegistryBuilder()
            .configure().build()).buildMetadata().buildSessionFactory();
    Session session = sf.openSession();

}