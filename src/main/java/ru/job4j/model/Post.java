package ru.job4j.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

//@Data
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private History history;

/*    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)*/
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "posts_photos", joinColumns = {
            @JoinColumn(name = "post_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "photo_id", nullable = false, updatable = false)})
    private Set<Photo> photos = new HashSet<>();

    @Override
    public String toString() {
        return "Task{"
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
