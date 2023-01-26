package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * класс CRUDRepository реализует интерфейс CRUDRepositoryInterface
 * класс реадизует инкапсуляцию запросов действий хранилищ объектов
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class CRUDRepository implements CRUDRepositoryInterface {

    private final SessionFactory sf;

    @Override
    public <T> Optional<T> optional(Consumer<Session> com) {
        return tx(e -> {
            com.accept(e);
            return Optional.empty();
        });
    }

    @Override
    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> map) {
        Function<Session, Optional<T>> command = session -> {
            Query<T> sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : map.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResultOptional();
        };
        return tx(command);
    }

    @Override
    public <T> Optional<T> optional(Class<T> cl, int id) {
        return tx(e -> Optional.ofNullable(e.get(cl, id)));
    }

    @Override
    public boolean total(String query, Map<String, Object> map) {
        Function<Session, Boolean> command = session -> {
            Query sq = session
                    .createQuery(query);
            for (Map.Entry<String, Object> arg : map.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.executeUpdate() != 0;
        };
        return tx(command);
    }

    @Override
    public <T> List<T> list(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            return sq.list();
        };
        return tx(command);
    }

    @Override
    public <T> List<T> list(String query, Class<T> cl, Map<String, Object> map) {
        Function<Session, List<T>> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : map.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return tx(command);
    }

    public <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            Transaction tx = session.getTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}
