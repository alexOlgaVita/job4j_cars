package ru.job4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "histories")
@Data
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private int id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
