package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.History;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
/* @Table(name = "history") */
@Repository
@AllArgsConstructor
public class HistoryRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param history история.
     * @return история с id.
     */
    public History create(History history) {
        crudRepository.run(session -> session.persist(history));
        return history;
    }

    /**
     * Обновить в базе историю.
     *
     * @param history история.
     */
    public void update(History history) {
        crudRepository.run(session -> session.merge(history));
    }

    /**
     * Удалить историю по id.
     *
     * @param historyId ID
     */
    public void delete(int historyId) {
        crudRepository.run(
                "delete from History where id = :fId",
                Map.of("fId", historyId)
        );
    }

    /**
     * Список историй отсортированных по id.
     *
     * @return список историй.
     */
    public List<History> findAllOrderById() {
        return crudRepository.query("from History order by id asc", History.class);
    }

    /**
     * Найти историю по ID
     *
     * @return история.
     */
    public Optional<History> findById(int historyId) {
        return crudRepository.optional(
                "from History where id = :fId", History.class,
                Map.of("fId", historyId)
        );
    }

    /**
     * Список историй по name LIKE %key%
     *
     * @param key key
     * @return список историй.
     */
    public List<History> findByLikeName(String key) {
        return crudRepository.query(
                "from History where name like :fKey", History.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти историю по name.
     *
     * @param name name.
     * @return Optional or history.
     */
    public Optional<History> findByName(String name) {
        return crudRepository.optional(
                "from History where name = :fName", History.class,
                Map.of("fName", name)
        );
    }
}