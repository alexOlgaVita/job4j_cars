package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.CarBody;
import ru.job4j.model.CarBody;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
/* @Table(name = "car_bodies") */
@Repository
@AllArgsConstructor
public class CarBodyRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param carBody марка.
     * @return марка с id.
     */
    public CarBody create(CarBody carBody) {
        crudRepository.run(session -> session.persist(carBody));
        return carBody;
    }

    /**
     * Обновить в базе марку.
     *
     * @param carBody марка.
     */
    public void update(CarBody carBody) {
        crudRepository.run(session -> session.merge(carBody));
    }

    /**
     * Удалить марку по id.
     *
     * @param carBodyId ID
     */
    public void delete(int carBodyId) {
        crudRepository.run(
                "delete from CarBody where id = :fId",
                Map.of("fId", carBodyId)
        );
    }

    /**
     * Список марок отсортированных по id.
     *
     * @return список двигателей.
     */
    public List<CarBody> findAllOrderById() {
        return crudRepository.query("from CarBody order by id asc", CarBody.class);
    }

    /**
     * Найти марку по ID
     *
     * @return двигатель.
     */
    public Optional<CarBody> findById(int carBodyId) {
        return crudRepository.optional(
                "from CarBody where id = :fId", CarBody.class,
                Map.of("fId", carBodyId)
        );
    }

    /**
     * Список марок по name LIKE %key%
     *
     * @param key key
     * @return список двигателей.
     */
    public List<CarBody> findByLikeName(String key) {
        return crudRepository.query(
                "from CarBody where name like :fKey", CarBody.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти марку по name.
     *
     * @param name name.
     * @return Optional or carBody.
     */
    public Optional<CarBody> findByName(String name) {
        return crudRepository.optional(
                "from CarBody where name = :fName", CarBody.class,
                Map.of("fName", name)
        );
    }
}