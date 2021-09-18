package com.codecool.roky_jpa_movies.entity;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Season {

    @Id
    @SequenceGenerator(
            name = "season_sequence",
            sequenceName = "season_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "season_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "season_number",
            nullable = false
    )
    private Integer seasonNumber;

    @Column(
            name="first_aired",
            nullable = false
    )
    private LocalDate firstAired;

    @ManyToOne
    private Series series;

    @OneToMany(mappedBy = "season", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    @Singular
    @ToString.Exclude
    private Set<Episode> episodes;
}
