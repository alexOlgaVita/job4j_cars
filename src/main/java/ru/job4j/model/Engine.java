package ru.job4j.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "engines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "Engine{"
                + "id=" + id
                + ", name='" + name + '}';
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
        Engine engine = (Engine) obj;
        return (id == engine.id);
    }
}