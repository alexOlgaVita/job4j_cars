package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Owner;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
/* @Table(name = "owners") */
@Repository
@AllArgsConstructor
public class OwnerRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param owner владелец.
     * @return владелец с id.
     */
    public Owner create(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
        return owner;
    }

    /**
     * Обновить в базе владельцевы.
     *
     * @param owner владелец.
     */
    public void update(Owner owner) {
        crudRepository.run(session -> session.merge(owner));
    }

    /**
     * Удалить владельцевы по id.
     *
     * @param ownerId ID
     */
    public void delete(int ownerId) {
        crudRepository.run(
                "delete from Owner where id = :fId",
                Map.of("fId", ownerId)
        );
    }

    /**
     * Список владелец отсортированных по id.
     *
     * @return список владельцев.
     */
    public List<Owner> findAllOrderById() {
        return crudRepository.query("from Owner order by id asc", Owner.class);
    }

    /**
     * Найти владельцевы по ID
     *
     * @return владелец.
     */
    public Optional<Owner> findById(int ownerId) {
        return crudRepository.optional(
                "from Owner where id = :fId", Owner.class,
                Map.of("fId", ownerId)
        );
    }

    /**
     * Список владельцев по name LIKE %key%
     *
     * @param key key
     * @return список владельцев.
     */
    public List<Owner> findByLikeName(String key) {
        return crudRepository.query(
                "from Owner where name like :fKey", Owner.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти владельцевы по name.
     *
     * @param name name.
     * @return Optional or owner.
     */
    public Optional<Owner> findByName(String name) {
        return crudRepository.optional(
                "from Owner where name = :fName", Owner.class,
                Map.of("fName", name)
        );
    }
}