package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.CarBrend;
import ru.job4j.todo.model.CarModel;

import java.util.ArrayList;
import java.util.List;


public class HmmRunCar implements AutoCloseable {

    @Override
    public void close() throws Exception {

    }

    public static void main(String[] args) {

        try (StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build()) {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CarBrend toyota = new CarBrend("toyota");
            session.save(toyota);

            CarModel crown = new CarModel("crown", toyota);
            CarModel windom = new CarModel("windom", toyota);
            CarModel camry = new CarModel("camry", toyota);
            CarModel mark2 = new CarModel("mark2", toyota);

            session.save(crown);
            session.save(windom);
            session.save(camry);
            session.save(mark2);

            toyota = (CarBrend) session.createQuery("from  ru.job4j.todo.model.CarBrend where id=:id"
            ).setParameter("id", 1).uniqueResult();

            crown = (CarModel) session.createQuery("from ru.job4j.todo.model.CarModel where id=:id"
            ).setParameter("id", 1).uniqueResult();

            windom = (CarModel) session.createQuery("from ru.job4j.todo.model.CarModel where id=:id"
            ).setParameter("id", 2).uniqueResult();

            camry = (CarModel) session.createQuery("from ru.job4j.todo.model.CarModel where id=:id"
            ).setParameter("id", 3).uniqueResult();

            mark2 = (CarModel) session.createQuery("from ru.job4j.todo.model.CarModel where id=:id"
            ).setParameter("id", 4).uniqueResult();

            toyota.addModel(crown);
            toyota.addModel(windom);
            toyota.addModel(camry);
            toyota.addModel(mark2);

            session.save(toyota);

            List<CarBrend> listCar = new ArrayList<>();
            listCar = session.createQuery("from CarBrend").list();
            for (CarBrend brend : listCar) {
                for (CarModel model : brend.getModels()) {
                    System.out.println(model);
                }
            }

            List<CarBrend> list = new ArrayList<>();
            list = session.createQuery("select distinct c from CarBrend c join fetch c.models").list();
            for (CarModel model : list.get(0).getModels()) {
                System.out.println(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
