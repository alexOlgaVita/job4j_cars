package ru.job4j.model;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;

    private String description;
    private Timestamp created;

    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<Post> posts = new ArrayList<>();
}
