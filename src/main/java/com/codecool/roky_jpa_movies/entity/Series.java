package com.codecool.roky_jpa_movies.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.GenerationType.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@Entity(name = "Series")
@Table(
        name = "series",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_title_unique", columnNames = "title")
        }
)
public class Series {

    @Id
    @SequenceGenerator(
            name = "series_sequence",
            sequenceName = "series_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "series_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false,
            unique = true
    )
    private String title;

    @Column(name="description")
    private String description;

    @Column(
            name="first_air_date",
            nullable = false
    )
    private LocalDate airDate;

    @ElementCollection
    @Singular
    private List<Genre> genres;
}
