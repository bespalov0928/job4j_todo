package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import ru.job4j.todo.model.*;

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
            session.close();
            return rsl;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public <T> T create(T item) {
        return this.tx(session -> {
            session.save(item);
            return item;
        });
    }

    public boolean edit(int id, Item item) {

        return this.tx(
                session -> {
                    item.setId(id);
                    session.update(item);
                    return true;
                });
    }

    public Item finfItemId(int id) {
        return this.tx(session -> {
            Item rsl = (Item) session.createQuery("from ru.job4j.todo.model.Item where id=:id"
            ).setParameter("id", id).uniqueResult();
            return rsl;
        });
    }

    public List<Item> findAll() {

        List<Item> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try (SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory()) {
            Session session = sf.openSession();
            session.beginTransaction();
            list = session.createQuery("select distinct i from Item i left join fetch i.categories c").list();
            session.getTransaction().commit();
            session.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public Acaunt findAcaunt(String login) {
        return this.tx(
                session -> {
                    Acaunt rsl = (Acaunt) session.createQuery("from ru.job4j.todo.model.Acaunt where login = :login"
                    ).setParameter("login", login).uniqueResult();
                    return rsl;
                });
    }

    public List<Category> finfAllCategories() {
        return this.tx(session -> session.createQuery("from ru.job4j.todo.model.Category").list());
    }

    public Category findCategoryId(int id){
        return this.tx(session -> {
            Category rsl = (Category) session.createQuery("from ru.job4j.todo.model.Category where id=:id"
            ).setParameter("id", id).uniqueResult();
            return rsl;
        });
    }
}
