package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class HbmTodo implements AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final HbmTodo INST = new HbmTodo();
    }

    public static HbmTodo instOf() {
        return Lazy.INST;
    }

    public Item add_old(Item item) {
        Item rsl = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            rsl = item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public boolean edit_old(int id, Item item) {
        boolean rsl = false;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            item.setId(id);
            session.update(item);
            session.getTransaction().commit();
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public List<Item> findAll_old() {
        List<Item> list = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            list = session.createQuery("from ru.job4j.todo.model.Item").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public Item add_new(Item item) {
        return this.tx(session -> {
            session.save(item);
            return item;
        });

    }

    public boolean edit_new(int id, Item item) {
        return this.tx(
                session -> {
                    item.setId(id);
                    session.update(item);
                    return true;
                });
    }


    public List<Item> findAll_new() {
        return this.tx(session -> session.createQuery("from ru.job4j.todo.model.Item").list());
    }

}
