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
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    /**
     * Обновить в базе объявления.
     *
     * @param post объявление.
     */
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    /**
     * Удалить объявления по id.
     *
     * @param postId ID
     */
    public void delete(int postId) {
        crudRepository.run(
                "delete from Post where id = :fId",
                Map.of("fId", postId)
        );
    }

    /**
     * Список объявление отсортированных по id.
     *
     * @return список объявлений.
     */
    public List<Post> findAllOrderById() {
        return crudRepository.query("from Post order by id asc", Post.class);
    }

    /**
     * Найти объявления по ID
     *
     * @return объявление.
     */
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "from Post where id = :fId", Post.class,
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
        return crudRepository.query("FROM Post f JOIN FETCH f.photos a where a is not null", Post.class);
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