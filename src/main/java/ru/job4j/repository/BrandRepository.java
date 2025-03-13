package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Brand;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
/* @Table(name = "brands") */
@Repository
@AllArgsConstructor
public class BrandRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param brand марка.
     * @return марка с id.
     */
    public Brand create(Brand brand) {
        crudRepository.run(session -> session.persist(brand));
        return brand;
    }

    /**
     * Обновить в базе марку.
     *
     * @param brand марка.
     */
    public void update(Brand brand) {
        crudRepository.run(session -> session.merge(brand));
    }

    /**
     * Удалить марку по id.
     *
     * @param brandId ID
     */
    public void delete(int brandId) {
        crudRepository.run(
                "delete from Brand where id = :fId",
                Map.of("fId", brandId)
        );
    }

    /**
     * Список марок отсортированных по id.
     *
     * @return список двигателей.
     */
    public List<Brand> findAllOrderById() {
        return crudRepository.query("from Brand order by name asc", Brand.class);
    }

    /**
     * Найти марку по ID
     *
     * @return двигатель.
     */
    public Optional<Brand> findById(int brandId) {
        return crudRepository.optional(
                "from Brand where id = :fId", Brand.class,
                Map.of("fId", brandId)
        );
    }

    /**
     * Список марок по name LIKE %key%
     *
     * @param key key
     * @return список двигателей.
     */
    public List<Brand> findByLikeName(String key) {
        return crudRepository.query(
                "from Brand where name like :fKey", Brand.class,
                Map.of("fKey", "%" + key + "%")
        );
    }

    /**
     * Найти марку по name.
     *
     * @param name name.
     * @return Optional or brand.
     */
    public Optional<Brand> findByName(String name) {
        return crudRepository.optional(
                "from Brand where name = :fName", Brand.class,
                Map.of("fName", name)
        );
    }
}