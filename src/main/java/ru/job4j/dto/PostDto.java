package ru.job4j.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.model.Car;
import ru.job4j.model.Photo;
import ru.job4j.model.User;

import java.time.LocalDateTime;
import java.util.Set;

import static java.time.LocalDateTime.now;

@Data
@NoArgsConstructor
public class PostDto {

    private int id;
    private String description;
    private boolean done = false;
    private LocalDateTime created = now();
    private User user;
    private Car car;
    private String zonedDateTime;
    private Set<Photo> photos;
    /*    private List<Photo> photos;*/

    public PostDto(int id,
                   String description,
                   LocalDateTime created,
                   boolean done,
                   User user,
                   Car car,
                   String zonedDateTime,
                   Set<Photo> photos) {
        /*        List<Photo> photos) { */
        this.id = id;
        this.description = description;
        this.created = created;
        this.done = done;
        this.user = user;
        this.car = car;
        this.zonedDateTime = zonedDateTime;
        this.photos = photos;
    }
}
