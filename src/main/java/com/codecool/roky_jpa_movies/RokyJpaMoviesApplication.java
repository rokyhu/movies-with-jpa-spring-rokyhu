package com.codecool.roky_jpa_movies;

import com.codecool.roky_jpa_movies.entity.Genre;
import com.codecool.roky_jpa_movies.entity.Series;
import com.codecool.roky_jpa_movies.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@SpringBootApplication
public class RokyJpaMoviesApplication {

	@Autowired
	private SeriesRepository seriesRepository;

	public static void main(String[] args) {
		SpringApplication.run(RokyJpaMoviesApplication.class, args);
	}

	@Bean
	@Profile("prod")
	public CommandLineRunner init() {
		return args -> {
			Series breaking_bad = Series.builder()
					.title("Breaking Bad")
					.genre(Genre.DRAMA)
					.genre(Genre.CRIME)
					.genre(Genre.THRILLER)
					.airDate(LocalDate.of(2008, 1, 20))
					.description("Breaking Bad follows Walter White, a meek high school chemistry teacher who transforms into a ruthless player in the local methamphetamine drug trade.")
					.build();
			seriesRepository.save(breaking_bad);
		};
	}

}
