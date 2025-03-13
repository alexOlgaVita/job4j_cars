package ru.job4j.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String name;

    @Override
    public String toString() {
        return "Brand{"
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
        Brand brand = (Brand) obj;
        return (id == brand.id);
    }
}
