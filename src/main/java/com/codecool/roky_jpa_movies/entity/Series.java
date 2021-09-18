package com.codecool.roky_jpa_movies.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
            allocationSize = 1
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
            name="first_aired",
            nullable = false
    )
    private LocalDate firstAired;

    @Column(name="rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Transient
    @Enumerated(EnumType.STRING)
    private Rating personalRating;

    @ElementCollection
    @Singular
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Exclude
    private List<Genre> genres;


    @OneToMany(mappedBy = "series", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    @Singular
    @ToString.Exclude
    private Set<Season> seasons;
}
