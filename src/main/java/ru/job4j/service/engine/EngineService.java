package ru.job4j.service.engine;

import ru.job4j.model.Engine;

import java.util.Collection;
import java.util.Optional;

public interface EngineService {

    Collection<Engine> findAll();

    Optional<Engine> findById(int id);

    Optional<Engine> findByName(String name);
}