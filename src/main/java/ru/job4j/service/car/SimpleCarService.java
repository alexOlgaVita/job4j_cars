package ru.job4j.service.car;

import org.springframework.stereotype.Service;
import ru.job4j.model.Car;
import ru.job4j.repository.CarRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleCarService implements CarService {

    private final CarRepository carRepository;

    public SimpleCarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car create(Car car) {
        return carRepository.create(car);
    }

    @Override
    public boolean update(Car car) {
        return carRepository.update(car);
    }

    @Override
    public boolean delete(int id) {
        return carRepository.delete(id);
    }

    @Override
    public Collection<Car> findAll() {
        return carRepository.findAllOrderById();
    }

    @Override
    public Optional<Car> findById(int id) {
        return (carRepository.findById(id).isPresent())
                ? Optional.ofNullable(carRepository.findById(id).get()) : Optional.empty();
    }
}