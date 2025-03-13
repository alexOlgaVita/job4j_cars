package ru.job4j.service.brand;

import ru.job4j.model.Brand;

import java.util.Collection;
import java.util.Optional;

public interface BrandService {

    Collection<Brand> findAll();

    Optional<Brand> findById(int id);

    Optional<Brand> findByName(String name);
}