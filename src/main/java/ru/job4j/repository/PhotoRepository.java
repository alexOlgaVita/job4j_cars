package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Photo;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
/* @Table(name = "photo") */
@Repository
@AllArgsConstructor
public class PhotoRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param photo фото.
     * @return фото с id.
     */
    public Photo create(Photo photo) {
        crudRepository.run(session -> session.persist(photo));
        return photo;
    }

    /**
     * Обновить в базе фото.
     *
     * @param photo фото.
     */
    public void update(Photo photo) {
        crudRepository.run(session -> session.merge(photo));
    }

    /**
     * Удалить фото по id.
     *
     * @param photoId ID
     */
    public void delete(int photoId) {
        crudRepository.run(
                "delete from Photo where id = :fId",
                Map.of("fId", photoId)
        );
    }

    /**
     * Список фото отсортированных по id.
     *
     * @return список фото.
     */
    public List<Photo> findAllOrderById() {
        return crudRepository.query("from Photo order by id asc", Photo.class);
    }

    /**
     * Найти фото по ID
     *
     * @return фото.
     */
    public Optional<Photo> findById(int photoId) {
        return crudRepository.optional(
                "from Photo where id = :fId", Photo.class,
                Map.of("fId", photoId)
        );
    }

    /**
     * Список фото по name LIKE %key%
     *
     * @param key key
     * @return список фото.
     */
    public List<Photo> findByLikeName(String key) {
        return crudRepository.query(
                "from Photo where name like :fKey", Photo.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти фото по name.
     *
     * @param name name.
     * @return Optional or photo.
     */
    public Optional<Photo> findByName(String name) {
        return crudRepository.optional(
                "from Photo where name = :fName", Photo.class,
                Map.of("fName", name)
        );
    }
}