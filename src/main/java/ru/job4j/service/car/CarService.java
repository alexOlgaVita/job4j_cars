package ru.job4j.service.car;

import ru.job4j.model.Car;

import java.util.Collection;
import java.util.Optional;

public interface CarService {

    Car create(Car car);

    boolean update(Car car);

    boolean delete(int id);

    Collection<Car> findAll();

    Optional<Car> findById(int id);
}