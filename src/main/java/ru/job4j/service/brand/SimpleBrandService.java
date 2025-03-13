package ru.job4j.service.brand;

import org.springframework.stereotype.Service;
import ru.job4j.model.Brand;
import ru.job4j.repository.BrandRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleBrandService implements BrandService {

    private final BrandRepository brandRepository;

    public SimpleBrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Collection<Brand> findAll() {
        return brandRepository.findAllOrderById();
    }

    @Override
    public Optional<Brand> findById(int id) {
        return brandRepository.findById(id);
    }

    @Override
    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name);
    }
}