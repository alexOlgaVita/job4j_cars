package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Post;

import java.util.List;

public class PostRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            var stored = listOf("FROM Post f JOIN FETCH f.car", Post.class, sf);
            for (Post post : stored) {
                System.out.println(post.getCar());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> List<T> listOf(String query, Class<T> model, SessionFactory sf) {
        Session session = sf.openSession();
        var rsl = session.createQuery(query, model)
                .getResultList();
        session.close();
        return rsl;
    }
}