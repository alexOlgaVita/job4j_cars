package ru.job4j.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;

public class PostUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            var userRepository = new UserRepository(new CrudRepository(sf));
            var user = new User();
            user.setLogin("olga10");
            user.setPassword("pass10");
            userRepository.create(user);

            var engineRepository = new EngineRepository(new CrudRepository(sf));
            var engine = new Engine();
            engine.setName("Бензиновый10");
            engineRepository.create(engine);

            var brandRepository = new BrandRepository(new CrudRepository(sf));
            var brand = new Brand();
            brand.setName("superBrand");
            brandRepository.create(brand);
            var brand2 = new Brand();
            brand2.setName("sosoBrand");
            brandRepository.create(brand2);
            var brand3 = new Brand();
            brand3.setName("extraBrand");
            brandRepository.create(brand3);

            var historyRepository = new HistoryRepository(new CrudRepository(sf));
            var history = new History();
            LocalDateTime startDateTime = LocalDateTime.of(2026, Month.FEBRUARY, 28, 19, 30, 59);
            LocalDateTime endDateTime = LocalDateTime.of(2026, Month.MARCH, 31, 23, 59, 59);
            history.setStartAt(startDateTime);
            history.setEndAt(endDateTime);
            historyRepository.create(history);

            var photoRepository = new PhotoRepository(new CrudRepository(sf));
            var photo1 = new Photo();
            photo1.setName("Общий вид10");
            photoRepository.create(photo1);
            var photo2 = new Photo();
            photo2.setName("Вид сбоку10");
            photoRepository.create(photo2);
            Set<Photo> photos = Set.of(photo1, photo2);

            var postRepository = new PostRepository(new CrudRepository(sf));
            var post = new Post(null, "Объявление 1", LocalDateTime.now(), user, brand, history, null);
            postRepository.create(post);

            var post2 = new Post(null, "Объявление 2", LocalDateTime.now(), user, brand2, history, photos);
            postRepository.create(post2);

            var post3 = new Post(null, "Объявление 3", LocalDateTime.now(), user, brand, history, photos);
            postRepository.create(post3);

            userRepository.findAllOrderById()
                    .forEach(System.out::println);

            var postsByBrand = postRepository.findByBrand("superBrand");
            postsByBrand.forEach(System.out::println);

            var postsWithPhoto = postRepository.findWithPhoto();
            postsWithPhoto.forEach(System.out::println);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}