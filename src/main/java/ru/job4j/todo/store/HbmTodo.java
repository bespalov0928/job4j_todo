package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Acaunt;
import ru.job4j.todo.model.CarBrend;
import ru.job4j.todo.model.CarModel;
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


    public List<Item> findAll() {
        return this.tx(session -> session.createQuery("from ru.job4j.todo.model.Item").list());
    }

    public Acaunt findAcaunt(String login) {
        return this.tx(
                session -> {
                    Acaunt rsl = (Acaunt) session.createQuery("from ru.job4j.todo.model.Acaunt where login = :login"
                    ).setParameter("login", login).uniqueResult();;

//                    Acaunt rsl = session.get(Acaunt.class, login);
                    return rsl;
                });
    }

    public static void main(String[] args) {

        HbmTodo hbmTodo = new HbmTodo();

        CarModel crown = new CarModel("crown");
        CarModel windom = new CarModel("windom");
        CarModel camry = new CarModel("camry");
        CarModel mark2 = new CarModel("mark2");

        hbmTodo.tx(session -> {
            session.save(crown);
            return crown;
        });

        hbmTodo.tx(session -> {
            session.save(windom);
            return windom;
        });

        hbmTodo.tx(session -> {
            session.save(camry);
            return camry;
        });

        hbmTodo.tx(session -> {
            session.save(mark2);
            return mark2;
        });

        CarBrend toyota = new CarBrend("toyota");

        toyota.addModel(crown);
        toyota.addModel(windom);
        toyota.addModel(camry);
        toyota.addModel(mark2);

        hbmTodo.tx(session -> session.save(toyota));
    }

}
