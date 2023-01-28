package ru.job4j.todo.repository;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * CRUDRepositoryInterface - интерфейс инкапсуляции запроса
 */
public interface CRUDRepositoryInterface {
    <T> Optional<T> optional(Consumer<Session> com);
    <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> map);
    <T> Optional<T> optional(Class<T> cl, int id);
    boolean total(String query, Map<String, Object> map);
    <T> boolean total(Function<Session, Boolean> function);
    <T> List<T> list(String query, Class<T> cl);
    <T> List<T> list(String query, Class<T> cl, Map<String, Object> map);
}
