package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dto.PhotoDto;
import ru.job4j.dto.PostDto;
import ru.job4j.model.Car;
import ru.job4j.model.Photo;
import ru.job4j.model.User;
import ru.job4j.service.brand.BrandService;
import ru.job4j.service.car.CarService;
import ru.job4j.service.carbody.CarBodyService;
import ru.job4j.service.engine.EngineService;
import ru.job4j.service.photo.PhotoService;
import ru.job4j.service.post.PostService;
import ru.job4j.service.user.UserService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ThreadSafe
@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final EngineService engineService;
    private final BrandService brandService;
    private final CarBodyService carBodyService;
    private final CarService carService;
    private final UserService userService;
    private final PhotoService photoService;

    public PostController(PostService postService,
                          EngineService engineService,
                          BrandService brandService,
                          CarBodyService carBodyService, CarService carService, UserService userService, PhotoService photoService) {

        this.postService = postService;
        this.engineService = engineService;
        this.brandService = brandService;
        this.carBodyService = carBodyService;
        this.carService = carService;
        this.userService = userService;
        this.photoService = photoService;
    }

    @GetMapping
    public String getAll(Model model) {
        Collection<PostDto> allWithByTimeZone = getPreparedDataList(postService.findAll());
        model.addAttribute("posts", allWithByTimeZone);
        model.addAttribute("addVisible", true);
        return "posts/list";
    }

    @GetMapping("/done")
    public String getAllDone(Model model) {
        Collection<PostDto> allDoneWithByTimeZone = getPreparedDataList(postService.findAllDone());
        model.addAttribute("posts", allDoneWithByTimeZone);
        model.addAttribute("addVisible", false);
        return "posts/list";
    }

    @GetMapping("/new")
    public String getAllNew(Model model) {
        Collection<PostDto> allNewWithByTimeZone = getPreparedDataList(postService.findAllNew());
        model.addAttribute("posts", allNewWithByTimeZone);
        model.addAttribute("addVisible", false);
        return "posts/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("carBodies", carBodyService.findAll());
        return "posts/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute PostDto post,
                         @RequestParam(value = "car.name", required = false) String carName,
                         @RequestParam(value = "car.brand.id", required = false) String carBrandId,
                         @RequestParam(value = "car.engine.id", required = false) String carEngineId,
                         @RequestParam(value = "car.carBody.id", required = false) String carBodyId,
                         @RequestParam MultipartFile photo,
                         HttpSession session, Model model) throws IOException {
        var user = (User) session.getAttribute("user");
        post.setUser(user);

        Car newCar = new Car(null, carName,
                engineService.findById(Integer.parseInt(carEngineId)).get(),
                brandService.findById(Integer.parseInt(carBrandId)).get(),
                carBodyService.findById(Integer.parseInt(carBodyId)).get(),
                null);

        PostDto createdPost = postService.create(post, List.of(new PhotoDto(photo.getOriginalFilename(), photo.getBytes())), newCar);
        if (createdPost == null) {
            model.addAttribute("message", "Возникла ошибка при создании объявления");
            return "errors/404";
        }
        model.addAttribute("post", getPreparedDataOne(Optional.of(createdPost)));
        model.addAttribute("message", "Объявление было успешно создано");
        return "posts/success";
    }

    @GetMapping("/{id}")
    public String getCreationPage(Model model, @PathVariable int id, HttpSession session) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление с указанным идентификатором не найдено");
            return "errors/404";
        }
        var user = (User) session.getAttribute("user");
        model.addAttribute("post", getPreparedDataOne(postOptional));
        model.addAttribute("userSess", user);
        return "posts/one";
    }

    @PostMapping("/{id}")
    public String setDonePage(@ModelAttribute PostDto post, @PathVariable int id, Model model) {
        var isSet = postService.setDone(id);
        if (!isSet) {
            model.addAttribute("message", "При переводе в статус 'Продано' объявления с указанным идентификатором '"
                    + id + "' произошла ошибка");
            return "errors/404";
        }
        model.addAttribute("post", post);
        model.addAttribute("message", "Объявление было успешно переведено в статус 'Продано'");
        return "posts/success";
    }

    @GetMapping("/update/{id}")
    public String getUpdatingPage(Model model, @PathVariable int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("postPhotos", postOptional.get().getPhotos().stream().map(e -> e.getId()).toList());
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("carBodies", carBodyService.findAll());
        model.addAttribute("post", getPreparedDataOne(postOptional));
        return "posts/oneEdit";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute PostDto post,
                         @RequestParam(value = "postPhotos", required = false) int[] postPhotos,
                         BindingResult bindingResult, @PathVariable int id,
                         Model model,
                         @RequestParam(value = "car.name", required = false) String carName,
                         @RequestParam(value = "car.brand.id", required = false) String carBrandId,
                         @RequestParam(value = "car.engine.id", required = false) String carEngineId,
                         @RequestParam(value = "car.carBody.id", required = false) String carBodyId,
                         @RequestParam MultipartFile photo
    ) throws IOException {

        Car updatedCar = post.getCar();
        updatedCar.setCarBody(carBodyService.findById(Integer.parseInt(carBodyId)).get());
        updatedCar.setBrand(brandService.findById(Integer.parseInt(carBrandId)).get());
        updatedCar.setEngine(engineService.findById(Integer.parseInt(carEngineId)).get());
        updatedCar.setName(carName);
        var isUpdated = carService.update(updatedCar);
        if (!isUpdated) {
            model.addAttribute("message", "При обновлении объявления с указанным идентификатором '"
                    + id + "' произошла ошибка");
            return "errors/404";
        }
        post.setCar(updatedCar);

        /* в изменении фото пока в реализации только добавление новой фотографии к существующим */
        Set<Photo> photosUp = new HashSet<>();
        /*List<Photo> photosUList = new ArrayList<>();*/
        for (var postPhoto : postPhotos) {
            photosUp.add(photoService.findById(postPhoto).get());
            /*   photosUList.add(photoService.findById(postPhoto).get());*/
        }

        Photo photoSaved = photoService.save(new PhotoDto(photo.getOriginalFilename(), photo.getBytes()));
        photosUp.add(photoSaved);
        /*                photosUList.add(photoSaved);*/

        post.setPhotos(photosUp);
        /*        post.setPhotos(photosUList);*/

        isUpdated = postService.update(post);

        if (!isUpdated) {
            model.addAttribute("message", "При обновлении объявления с указанным идентификатором '"
                    + id + "' произошла ошибка");
            return "errors/404";
        }
        model.addAttribute("post", post);
        model.addAttribute("message", "Объявление было успешно обновлено");
        return "posts/success";
    }

    @PostMapping("/delete/{id}")
    public String delete(@ModelAttribute PostDto post, @PathVariable int id, Model model) {
        var isDeleted = postService.delete(id);
        if (!isDeleted) {
            model.addAttribute("message", "Объявление с указанным идентификатором не найдено");
            return "errors/404";
        }
        return "redirect:/posts";
    }

    private PostDto getPreparedDataOne(Optional<PostDto> postOptional) {
        return List.of(postOptional.get()).stream().map(e -> new PostDto(e.getId(),
                e.getDescription(),
                e.getCreated(),
                e.isDone(),
                e.getUser(),
                e.getCar(),
                e.getCreated().atZone(ZoneId.of(e.getUser().getTimezone())).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z")),
                e.getPhotos()
        )).toList().get(0);
    }

    private List<PostDto> getPreparedDataList(Collection<PostDto> list) {
        return list.stream().map(e -> new PostDto(e.getId(),
                e.getDescription(),
                e.getCreated(),
                e.isDone(),
                e.getUser(),
                e.getCar(),
                e.getCreated().atZone(ZoneId.of(e.getUser().getTimezone())).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z")),
                e.getPhotos()
        )).toList();
    }
}