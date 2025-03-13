package ru.job4j.service.engine;

import org.springframework.stereotype.Service;
import ru.job4j.model.Engine;
import ru.job4j.repository.EngineRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleEngineService implements EngineService {

    private final EngineRepository engineRepository;

    public SimpleEngineService(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    @Override
    public Collection<Engine> findAll() {
        return engineRepository.findAllOrderById();
    }

    @Override
    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }

    @Override
    public Optional<Engine> findByName(String name) {
        return engineRepository.findByName(name);
    }
}