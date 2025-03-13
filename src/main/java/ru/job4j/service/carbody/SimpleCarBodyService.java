package ru.job4j.service.carbody;

import org.springframework.stereotype.Service;
import ru.job4j.model.CarBody;
import ru.job4j.repository.CarBodyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleCarBodyService implements CarBodyService {

    private final CarBodyRepository carBodyRepository;

    public SimpleCarBodyService(CarBodyRepository carBodyRepository) {
        this.carBodyRepository = carBodyRepository;
    }

    @Override
    public Collection<CarBody> findAll() {
        return carBodyRepository.findAllOrderById();
    }

    @Override
    public Optional<CarBody> findById(int id) {
        return carBodyRepository.findById(id);
    }

    @Override
    public Optional<CarBody> findByName(String name) {
        return carBodyRepository.findByName(name);
    }
}