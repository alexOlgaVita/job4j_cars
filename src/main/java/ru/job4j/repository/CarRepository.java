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
        return crudRepository.runBoolean(session -> session.persist(car)) ? car : null;
    }

    /**
     * Обновить в базе машины.
     *
     * @param car машина.
     */
    public boolean update(Car car) {
        return crudRepository.queryBoolean(

                "UPDATE Car SET name = :fName, engine = :fEngine, brand = :fBrand, carBody = :fCarBody"
                        + "  WHERE id = :fId",
                Map.of("fId", car.getId(), "fName", car.getName(),
                        "fEngine", car.getEngine(), "fBrand", car.getBrand(), "fCarBody", car.getCarBody()));
    }

    /**
     * Удалить машины по id.
     *
     * @param carId ID
     */
    public boolean delete(int carId) {
        return crudRepository.queryBoolean(
                "delete from Car where id = :fId",
                Map.of("fId", carId));
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