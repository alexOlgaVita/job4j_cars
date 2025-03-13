package ru.job4j.service.carbody;

import ru.job4j.model.CarBody;

import java.util.Collection;
import java.util.Optional;

public interface CarBodyService {

    Collection<CarBody> findAll();

    Optional<CarBody> findById(int id);

    Optional<CarBody> findByName(String name);
}