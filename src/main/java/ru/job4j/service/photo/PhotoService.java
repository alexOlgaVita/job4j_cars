package ru.job4j.service.photo;

import ru.job4j.dto.PhotoDto;
import ru.job4j.model.Photo;

import java.util.Optional;

public interface PhotoService {

    Photo save(PhotoDto photoDto);

    boolean update(PhotoDto photoDto);

    Optional<PhotoDto> getFileById(int id);

    void deleteById(int id);

    Optional<Photo> findById(int id);
}
