package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Car;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
/* @Table(name = "cars") */
@Repository
@AllArgsConstructor
public class CarRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param car машина.
     * @return машина с id.
     */
    public Car create(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    /**
     * Обновить в базе машины.
     *
     * @param car машина.
     */
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    /**
     * Удалить машины по id.
     *
     * @param carId ID
     */
    public void delete(int carId) {
        crudRepository.run(
                "delete from Car where id = :fId",
                Map.of("fId", carId)
        );
    }

    /**
     * Список машина отсортированных по id.
     *
     * @return список машин.
     */
    public List<Car> findAllOrderById() {
        return crudRepository.query("from Car order by id asc", Car.class);
    }

    /**
     * Найти машины по ID
     *
     * @return машина.
     */
    public Optional<Car> findById(int carId) {
        return crudRepository.optional(
                "from Car where id = :fId", Car.class,
                Map.of("fId", carId)
        );
    }

    /**
     * Список машин по name LIKE %key%
     *
     * @param key key
     * @return список машин.
     */
    public List<Car> findByLikeName(String key) {
        return crudRepository.query(
                "from Car where name like :fKey", Car.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти машины по name.
     *
     * @param name name.
     * @return Optional or car.
     */
    public Optional<Car> findByName(String name) {
        return crudRepository.optional(
                "from Car where name = :fName", Car.class,
                Map.of("fName", name)
        );
    }
}