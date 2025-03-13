package ru.job4j.service.post;

import ru.job4j.dto.PhotoDto;
import ru.job4j.dto.PostDto;
import ru.job4j.model.Car;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostService {

    PostDto create(PostDto post, List<PhotoDto> images, Car car);

    boolean update(PostDto post);

    boolean setDone(int id);

    boolean delete(int id);

    Collection<PostDto> findAll();

    Collection<PostDto> findAllDone();

    Collection<PostDto> findAllNew();

    Optional<PostDto> findById(int id);
}