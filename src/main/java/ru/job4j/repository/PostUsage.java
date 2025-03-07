package ru.job4j.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

public class PostUsage {

    /* Проверка работы фильтров */
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            var userRepository = new UserRepository(new CrudRepository(sf));
            var engineRepository = new EngineRepository(new CrudRepository(sf));
            var brandRepository = new BrandRepository(new CrudRepository(sf));
            var photoRepository = new PhotoRepository(new CrudRepository(sf));
            var carBodyRepository = new CarBodyRepository(new CrudRepository(sf));
            var historyRepository = new HistoryRepository(new CrudRepository(sf));
            var postRepository = new PostRepository(new CrudRepository(sf));
            var carRepository = new CarRepository(new CrudRepository(sf));
            var posts = postRepository.findAllOrderById();
            for (var post : posts) {
                postRepository.delete(post.getId().intValue());
            }
            var histories = historyRepository.findAllOrderById();
            for (var history : histories) {
                historyRepository.delete(history.getId());
            }
            var photos = photoRepository.findAllOrderById();
            for (var photo : photos) {
                photoRepository.delete(photo.getId().intValue());
            }
            var cars = carRepository.findAllOrderById();
            for (var car : cars) {
                carRepository.delete(car.getId().intValue());
            }
            var carBodies = carBodyRepository.findAllOrderById();
            for (var carBody : carBodies) {
                carBodyRepository.delete(carBody.getId());
            }
            var brands = brandRepository.findAllOrderById();
            for (var brand : brands) {
                brandRepository.delete(brand.getId());
            }
            var engines = engineRepository.findAllOrderById();
            for (var engine : engines) {
                engineRepository.delete(engine.getId());
            }
            var users = userRepository.findAllOrderById();
            for (var user : users) {
                userRepository.delete(user.getId());
            }
            var user = new User(null, "olga10", "pass10", null);
            userRepository.create(user);
            var engine = new Engine(null, "Бензиновый супер");
            engineRepository.create(engine);
            var engine2 = new Engine(null, "Дизельный обычный");
            engineRepository.create(engine2);
            var brand = new Brand(null, "superBrand");
            brandRepository.create(brand);
            var brand2 = new Brand(null, "sosoBrand");
            brandRepository.create(brand2);
            var brand3 = new Brand(null, "extraBrand");
            brandRepository.create(brand3);
            var carBody = new CarBody(null, "Универсал универсальный");
            carBodyRepository.create(carBody);
            var carBody2 = new CarBody(null, "Седан");
            carBodyRepository.create(carBody2);
            Car car1 = new Car(null, "Супер машина", engine, brand, carBody, null);
            carRepository.create(car1);
            Car car2 = new Car(null, "Просто машина", engine, brand2, carBody2, null);
            carRepository.create(car2);
            Car car3 = new Car(null, "Не Просто машина", engine2, brand, carBody2, null);
            carRepository.create(car3);
            var history = new History();
            LocalDateTime startDateTime = LocalDateTime.of(2026, Month.FEBRUARY, 28, 19, 30, 59);
            LocalDateTime endDateTime = LocalDateTime.of(2026, Month.MARCH, 31, 23, 59, 59);
            history.setStartAt(startDateTime);
            history.setEndAt(endDateTime);
            historyRepository.create(history);
            var photo1 = new Photo();
            photo1.setName("Общий вид");
            photoRepository.create(photo1);
            var photo2 = new Photo();
            photo2.setName("Вид спереди");
            photoRepository.create(photo2);
            var photo3 = new Photo();
            photo3.setName("Вид сбоку");
            photoRepository.create(photo3);
            Set<Photo> photoSet = Set.of(photo1, photo2);
            Set<Photo> photoSet2 = Set.of(photo3);
            var post = new Post(null, "Объявление 1", LocalDateTime.now(), user, car1, history, new HashSet<Photo>());
            postRepository.create(post);
            var post2 = new Post(null, "Объявление 2", LocalDateTime.now(), user, car2, history, photoSet);
            postRepository.create(post2);
            var post3 = new Post(null, "Объявление 3", LocalDateTime.now(), user, car3, history, photoSet2);
            postRepository.create(post3);
            userRepository.findAllOrderById()
                    .forEach(System.out::println);

            var postsByBrand = postRepository.findByBrand("superBrand");
            postsByBrand.forEach(System.out::println);

            System.out.println("Объявления с фото:");
            var postsWithPhoto = postRepository.findWithPhoto();
            postsWithPhoto.forEach(System.out::println);
            System.out.println("Объявления за последний день:");
            var postsForLastDay = postRepository.findForLastDay();
            postsForLastDay.forEach(System.out::println);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
