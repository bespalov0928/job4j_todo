package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todo.model.BaseVacancies;
import ru.job4j.todo.model.Candidate;
import ru.job4j.todo.model.Vacancy;

import java.util.List;

public class HbmCandidate implements AutoCloseable {
    @Override
    public void close() throws Exception {

    }

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        hbmCandidate(registry);

        hbmFetch(registry);
    }

    private static void hbmCandidate(StandardServiceRegistry registry) {
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate Igor = new Candidate("Игорь", "без опыта", 30);
            Candidate Alex = new Candidate("Алексей", "средний", 60);
            Candidate Nikolay = new Candidate("Николай", "большой", 100);

            session.save(Igor);
            session.save(Alex);
            session.save(Nikolay);

            //select
            List result = session.createQuery("from ru.job4j.todo.model.Candidate").list();
            for (Object candidate : result) {
                System.out.println(candidate);
            }

            Query query = session.createQuery("from ru.job4j.todo.model.Candidate c where c.id = 4");
            System.out.println(query.uniqueResult());

            //update
            session.createQuery("update Candidate s set s.salary=:newSalary where s.id = :fId")
                    .setParameter("newSalary", 110)
                    .setParameter("fId", 6)
                    .executeUpdate();

            //delete
            session.createQuery("delete from Candidate where id = :fId")
                    .setParameter("fId", 4)
                    .executeUpdate();

            session.createQuery("delete from Candidate where name = :fId")
                    .setParameter("fId", "Алексей")
                    .executeUpdate();

            //insert
            session.createQuery("insert into Candidate (name, experience, salary) "
                    + "select :nameNew, :experienceNew, :salaryNew "
                    + " from Candidate c where c.id = :fId")
                    .setParameter("fId", 5)
                    .setParameter("nameNew", "Анна")
                    .setParameter("experienceNew", "средний")
                    .setParameter("salaryNew", 50)
                    .executeUpdate();

            List result1 = session.createQuery("from ru.job4j.todo.model.Candidate").list();
            for (Object candidate : result1) {
                System.out.println(candidate);
            }

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }

    private static void hbmFetch(StandardServiceRegistry registry) {
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Vacancy vacancyJunior = new Vacancy("java Junior");
            Vacancy vacancyMiddle = new Vacancy("java Middle");
            Vacancy vacancySenior = new Vacancy("java Senior");
            session.save(vacancyJunior);
            session.save(vacancyMiddle);
            session.save(vacancySenior);

            BaseVacancies baseVacancies = new BaseVacancies();
            baseVacancies.setVacance(vacancyJunior);
            baseVacancies.setVacance(vacancyMiddle);
            baseVacancies.setVacance(vacancySenior);
            session.save(baseVacancies);

            Query query = session.createQuery("from ru.job4j.todo.model.Candidate c where c.name = :fName")
                    .setParameter("fName", "Анна");
            Candidate candidate = (Candidate) query.uniqueResult();

            candidate.setBaseVacancies(baseVacancies);


            Candidate rsl = session.createQuery("select distinct c from Candidate c "
                    +" join fetch c.baseVacancies b "
                    +" join fetch b.vacancies "
                    +" where c.name = :fName", Candidate.class)
                    .setParameter("fName", "Анна")
                    .uniqueResult();

            for (Vacancy v:rsl.getBaseVacancies().getVacancies()) {
                System.out.println(v);
            }

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }
}
