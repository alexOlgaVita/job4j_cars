package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Post;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
/* @Table(name = "posts") */
@Repository
@AllArgsConstructor
public class PostRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param post объявление.
     * @return объявление с id.
     */
    public Post create(Post post) {
        return crudRepository.runBoolean(session -> session.persist(post)) ? post : null;
    }

    /**
     * Обновить в базе объявления.
     *
     * @param post объявление.
     */
    public boolean update(Post post) {

        return crudRepository.queryBoolean(
                "UPDATE Post p SET p.description = :fDescription, p.car = :fCar"
                        + "  WHERE p.id = :fId",
                Map.of("fId", post.getId(),
                        "fDescription", post.getDescription(),
                        "fCar", post.getCar()));

/*     Доработать реализацию обновления фото
/*
                "UPDATE Post p SET p.description = :fDescription, p.photos = :fImages, p.car = :fCar"
                        + "  WHERE p.id = :fId",
                Map.of("fId", post.getId(),
                        "fDescription", post.getDescription(),
                        "fImages", post.getPhotos(),
                        "fCar", post.getCar()));
*/
/*
        "UPDATE Post p SET p.photos = :fImages"
                + "  WHERE p.id = :fId",
                Map.of("fId", post.getId()),
                                Map.of("fImages", new ArrayList<>(post.getPhotos())));
        //Map.of("fImages", post.getPhotos()));
 */
    }

    /**
     * Установить значение поля done в "true".
     *
     * @param id иденификатор объявления.
     */
    public boolean setDone(int id) {
        return crudRepository.queryBoolean(
                "UPDATE Post SET done = true WHERE id = :fId AND done != true", Map.of("fId", id));
    }

    /**
     * Список объявлений проданных машин.
     *
     * @return список объявлений.
     */
    public List<Post> findAllDone() {
        return crudRepository.query("from Post f INNER JOIN FETCH f.car where f.done = true order by f.id asc",
                Post.class);
    }

    /**
     * Список новых объявлений (машина не продана).
     *
     * @return список объявлений.
     */
    public List<Post> findAllNew() {
        return crudRepository.query("from Post f INNER JOIN FETCH f.car where f.done = false order by f.id asc",
                Post.class);
    }

    /**
     * Удалить объявления по id.
     *
     * @param postId ID
     */
    public boolean delete(int postId) {
        return crudRepository.queryBoolean(
                "delete from Post where id = :fId",
                Map.of("fId", postId));
    }

    /**
     * Список объявление отсортированных по id.
     *
     * @return список объявлений.
     */
    public List<Post> findAllOrderById() {
        return crudRepository.query("from Post f INNER JOIN FETCH f.car order by f.id asc", Post.class);
    }

    /**
     * Найти объявления по ID
     *
     * @return объявление.
     */
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "from Post f INNER JOIN FETCH f.car a "
                        + "INNER JOIN FETCH a.engine "
                        + "INNER JOIN FETCH a.brand "
                        + "INNER JOIN FETCH a.carBody "
                        + "INNER JOIN FETCH f.photos "
                        + "where f.id = :fId", Post.class,
                Map.of("fId", postId)
        );
    }

    /**
     * Найти объявления за последний день
     *
     * @return объявление.
     */
    public List<Post> findForLastDay() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(1);
        return crudRepository.query("from Post p1 where p1.created BETWEEN :fStartDate AND :fEndDate", Post.class,
                Map.of("fStartDate", startDate, "fEndDate", endDate));
    }

    /**
     * Найти объявления с фото
     *
     * @return объявление.
     */
    public List<Post> findWithPhoto() {
/*        альтернатива использованию size()
        return crudRepository.query("FROM Post s where s.id in "
                + "(Select f.id from Post f JOIN f.photos a GROUP BY f.id HAVING COUNT(*) > 0)", Post.class);
 */
        return crudRepository.query("FROM Post f where size(f.photos) > 0", Post.class);

    }

    /**
     * Найти объявления определенной марки
     *
     * @param brand марка
     * @return объявление.
     */
    public List<Post> findByBrand(String brand) {
        return crudRepository.query("FROM Post f INNER JOIN FETCH f.car a where a.brand.name = :fBrand", Post.class,
                Map.of("fBrand", brand));
    }
}