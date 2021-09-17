package com.codecool.roky_jpa_movies.repository;


import com.codecool.roky_jpa_movies.entity.Series;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;

public interface SeriesRepository extends JpaRepository<Series, Long> {


}
