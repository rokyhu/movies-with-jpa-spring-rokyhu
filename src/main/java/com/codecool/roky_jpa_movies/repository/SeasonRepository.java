package com.codecool.roky_jpa_movies.repository;

import com.codecool.roky_jpa_movies.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
}
