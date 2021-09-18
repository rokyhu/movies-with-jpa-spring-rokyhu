package com.codecool.roky_jpa_movies.entity;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Episode {

    @Id
    @SequenceGenerator(
            name = "episode_sequence",
            sequenceName = "episode_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "episode_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "episode_number",
            nullable = false
    )
    private Integer episodeNumber;

    @Column(
            name="air_date",
            nullable = false
    )
    private LocalDate airDate;

    @ManyToOne
    private Season season;

}
