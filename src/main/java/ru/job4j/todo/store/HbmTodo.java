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
//            list = session.createQuery("from ru.job4j.todo.model.Item").list();
            list = session.createQuery("select distinct i from Item i left join fetch i.categories c").list();
//            for (Item item:list) {
//                for (Category category: item.getCategories()) {
//                    System.out.println(category.getName());
//                }
//            }
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


//                    Acaunt rsl = session.get(Acaunt.class, login);
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

    public static void main(String[] args) {

//        HbmTodo hbmTodo = new HbmTodo();


//        CarBrend toyota = new CarBrend("toyota");
//        hbmTodo.tx(session -> session.save(toyota));
//
//        CarModel crown = new CarModel("crown", toyota);
//        CarModel windom = new CarModel("windom", toyota);
//        CarModel camry = new CarModel("camry", toyota);
//        CarModel mark2 = new CarModel("mark2", toyota);
//
//        hbmTodo.tx(session -> {
//            session.save(crown);
//            return crown;
//        });
//
//        hbmTodo.tx(session -> {
//            session.save(windom);
//            return windom;
//        });
//
//        hbmTodo.tx(session -> {
//            session.save(camry);
//            return camry;
//        });
//
//        hbmTodo.tx(session -> {
//            session.save(mark2);
//            return mark2;
//        });


//        CarBrend toyota = hbmTodo.tx(session -> {
//            CarBrend rsl = (CarBrend) session.createQuery("from  ru.job4j.todo.model.CarBrend where id=:id"
//            ).setParameter("id", 1).uniqueResult();
//            return rsl;
//        });
//
//
//        CarModel crown = hbmTodo.tx(session -> {
//            CarModel rsl = (CarModel) session.createQuery("from ru.job4j.todo.model.CarModel where id=:id"
//            ).setParameter("id", 1).uniqueResult();
//            return rsl;
//        });
//
//        CarModel windom = hbmTodo.tx(session -> {
//            CarModel rsl = (CarModel) session.createQuery("from ru.job4j.todo.model.CarModel where id=:id"
//            ).setParameter("id", 2).uniqueResult();
//            return rsl;
//        });
//
//        CarModel camry = hbmTodo.tx(session -> {
//            CarModel rsl = (CarModel) session.createQuery("from ru.job4j.todo.model.CarModel where id=:id"
//            ).setParameter("id", 3).uniqueResult();
//            return rsl;
//        });
//
//        CarModel mark2 = hbmTodo.tx(session -> {
//            CarModel rsl = (CarModel) session.createQuery("from ru.job4j.todo.model.CarModel where id=:id"
//            ).setParameter("id", 4).uniqueResult();
//            return rsl;
//        });
//
//        toyota.addModel(crown);
//        toyota.addModel(windom);
//        toyota.addModel(camry);
//        toyota.addModel(mark2);
//
//        hbmTodo.tx(session -> {
//            session.save(toyota);
//            return toyota;
//        });


//        List<CarBrend> listCar = new ArrayList<>();
//        listCar = hbmTodo.tx(session -> session.createQuery("from CarBrend").list());
//        for (CarBrend brend : listCar) {
//            for (CarModel model : brend.getModels()) {
//                System.out.println(model);
//            }
//        }


        List<CarBrend> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
//            list = session.createQuery("from CarBrend").list();
//            for (CarBrend brend : list) {
//                for (CarModel model : brend.getModels()) {
//                    System.out.println(model);
//                }
//            }

            list = session.createQuery("select distinct c from CarBrend c join fetch c.models").list();
            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (CarModel model : list.get(0).getModels()) {
            System.out.println(model);
        }

    }

}
