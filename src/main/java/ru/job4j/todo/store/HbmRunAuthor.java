package ru.job4j.todo.store;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import ru.job4j.todo.model.Author;
import ru.job4j.todo.model.Book;

public class HbmRunAuthor implements AutoCloseable {


    @Override
    public void close() throws Exception {

    }

    public static void main(String[] args) {

        try (StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build()) {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();


            Book book1 = new Book("book1");
            Book book2 = new Book("book2");
            Book book3 = new Book("book3");

            Author author1 = new Author("author1");
            Author author2 = new Author("author2");
            Author author3 = new Author("author3");
            Author author4 = new Author("author4");

            author1.getBooks().add(book1);
            author2.getBooks().add(book1);
            author3.getBooks().add(book2);
            author4.getBooks().add(book3);


            session.persist(author1);
            session.persist(author2);
            session.persist(author3);
            session.persist(author4);

            Author authorRemove = session.get(Author.class, author4.getId());
            session.remove(authorRemove);
            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
