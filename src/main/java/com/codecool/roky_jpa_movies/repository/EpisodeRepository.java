package com.codecool.roky_jpa_movies.repository;

import com.codecool.roky_jpa_movies.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
