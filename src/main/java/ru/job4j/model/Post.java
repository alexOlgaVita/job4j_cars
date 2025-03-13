package ru.job4j.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "auto_posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private Integer id;

    private String description;
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private History history;

    @ManyToMany(fetch = FetchType.LAZY)
    /*            @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) */
    @JoinTable(name = "posts_photos", joinColumns = {
            @JoinColumn(name = "post_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "photo_id", nullable = false, updatable = false)})
    private Set<Photo> photos = new HashSet<>();
    /*    private List<Photo> photos = new ArrayList<>(); */

    private boolean done = false;

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", description='" + description + '\''
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Post post = (Post) obj;
        return (id == post.id);
    }
}