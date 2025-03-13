package ru.job4j.service.post;

import org.springframework.stereotype.Service;
import ru.job4j.dto.PhotoDto;
import ru.job4j.dto.PostDto;
import ru.job4j.mapper.PostMapper;
import ru.job4j.model.Car;
import ru.job4j.model.Photo;
import ru.job4j.model.Post;
import ru.job4j.repository.PostRepository;
import ru.job4j.service.car.CarService;
import ru.job4j.service.photo.PhotoService;

import java.util.*;

@Service
public class SimplePostService implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PhotoService photoService;
    private final CarService carService;

    public SimplePostService(PostRepository postRepository, PostMapper postMapper, PhotoService photoService, CarService carService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.photoService = photoService;
        this.carService = carService;
    }

    @Override
    public PostDto create(PostDto postDto, List<PhotoDto> images, Car car) {
        Post post = postMapper.getEntityFromModelCustom(postDto);
        saveNewFile(post, images);
        saveNewCar(post, car);
        return postMapper.getModelFromEntityCustom(postRepository.create(post));
    }

    private void saveNewFile(Post post, List<PhotoDto> images) {
        Set<Photo> savedPhotos = new HashSet<>();
        List<Photo> savedPhotosList = new ArrayList<>();
        for (PhotoDto image : images) {
            savedPhotos.add(photoService.save(image));
            savedPhotosList.add(photoService.save(image));
        }
        post.setPhotos(savedPhotos);
        /*       post.setPhotos(savedPhotosList); */
    }

    private void saveNewCar(Post post, Car car) {
        var savedCars = carService.create(car);
        post.setCar(car);
    }

    @Override
    public boolean update(PostDto postDto) {

        return postRepository.update(postMapper.getEntityFromModelCustom(postDto));
    }

    @Override
    public boolean setDone(int id) {
        return postRepository.setDone(findById(id).get().getId());
    }

    @Override
    public boolean delete(int id) {
        return postRepository.delete(id);
    }

    @Override
    public Collection<PostDto> findAll() {
        List<PostDto> result = new ArrayList<>();
        if (postRepository.findAllOrderById() != null) {
            result = postRepository.findAllOrderById().stream()
                    .map(e -> postMapper.getModelFromEntityCustom(e))
                    .map(e -> new PostDto(e.getId(),
                            e.getDescription(),
                            e.getCreated(),
                            e.isDone(),
                            e.getUser(),
                            e.getCar(),
                            null,
                            e.getPhotos()
                    ))
                    .toList();
        }
        return result;
    }

    @Override
    public Collection<PostDto> findAllDone() {
        List<PostDto> result = new ArrayList<>();
        if (postRepository.findAllDone() != null) {
            result = postRepository.findAllDone().stream()
                    .map(e -> postMapper.getModelFromEntityCustom(e)).toList();
        }
        return result;
    }

    @Override
    public Collection<PostDto> findAllNew() {
        List<PostDto> result = new ArrayList<>();
        if (postRepository.findAllNew() != null) {
            result = postRepository.findAllNew().stream()
                    .map(e -> postMapper.getModelFromEntityCustom(e)).toList();
        }
        return result;
    }

    @Override
    public Optional<PostDto> findById(int id) {
        return (postRepository.findById(id).isPresent())
                ? Optional.ofNullable(postMapper.getModelFromEntityCustom(postRepository.findById(id).get())) : Optional.empty();
    }
}