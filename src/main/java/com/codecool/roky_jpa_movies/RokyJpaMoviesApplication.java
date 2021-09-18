package com.codecool.roky_jpa_movies;

import com.codecool.roky_jpa_movies.entity.*;
import com.codecool.roky_jpa_movies.repository.SeasonRepository;
import com.codecool.roky_jpa_movies.repository.SeriesRepository;
import com.google.inject.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;

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

			Episode bbS1E01 = Episode.builder().episodeNumber(1).airDate(LocalDate.of(2008, 1, 20)).build();
			Episode bbS1E02 = Episode.builder().episodeNumber(2).airDate(LocalDate.of(2008, 1, 27)).build();
			Episode bbS1E03 = Episode.builder().episodeNumber(3).airDate(LocalDate.of(2008, 2, 10)).build();
			Episode bbS1E04 = Episode.builder().episodeNumber(4).airDate(LocalDate.of(2008, 2, 17)).build();
			Episode bbS1E05 = Episode.builder().episodeNumber(5).airDate(LocalDate.of(2008, 2, 24)).build();
			Episode bbS1E06 = Episode.builder().episodeNumber(6).airDate(LocalDate.of(2008, 3, 2)).build();
			Episode bbS1E07 = Episode.builder().episodeNumber(7).airDate(LocalDate.of(2008, 3, 9)).build();


			Season bbS1 = Season.builder()
					.seasonNumber(1)
					.firstAired(LocalDate.of(2008, 1, 20))
					.episodes(Arrays.asList(bbS1E01, bbS1E02, bbS1E03, bbS1E04, bbS1E05, bbS1E06, bbS1E07))
					.build();
			Season bbS2 = Season.builder().seasonNumber(2).firstAired(LocalDate.of(2009, 3, 8)).build();
			Season bbS3 = Season.builder().seasonNumber(3).firstAired(LocalDate.of(2010, 3, 21)).build();
			Season bbS4 = Season.builder().seasonNumber(4).firstAired(LocalDate.of(2011, 7, 17)).build();
			Season bbS5 = Season.builder().seasonNumber(5).firstAired(LocalDate.of(2012, 7, 15)).build();

			bbS1E01.setSeason(bbS1);
			bbS1E02.setSeason(bbS1);
			bbS1E03.setSeason(bbS1);
			bbS1E04.setSeason(bbS1);
			bbS1E05.setSeason(bbS1);
			bbS1E06.setSeason(bbS1);
			bbS1E07.setSeason(bbS1);

			Series breaking_bad = Series.builder()
					.title("Breaking Bad")
					.genre(Genre.DRAMA)
					.genre(Genre.CRIME)
					.genre(Genre.THRILLER)
					.seasons(Arrays.asList(bbS1, bbS2, bbS3, bbS4, bbS5))
					.firstAired(LocalDate.of(2008, 1, 20))
					.rating(Rating.FIVE)
					.personalRating(Rating.FIVE)
					.description("Breaking Bad follows Walter White, a meek high school chemistry teacher who transforms into a ruthless player in the local methamphetamine drug trade.")
					.build();

			bbS1.setSeries(breaking_bad);  // TODO Roky Very important to set the Series for the Season otherwise it won't know who it belongs to
			bbS2.setSeries(breaking_bad);
			bbS3.setSeries(breaking_bad);
			bbS4.setSeries(breaking_bad);
			bbS5.setSeries(breaking_bad);

			seriesRepository.save(breaking_bad);

			Season bcsS1 = Season.builder().seasonNumber(1).firstAired(LocalDate.of(2015, 2, 8)).build();
			Season bcsS2 = Season.builder().seasonNumber(2).firstAired(LocalDate.of(2016, 2, 15)).build();
			Season bcsS3 = Season.builder().seasonNumber(3).firstAired(LocalDate.of(2017, 4, 10)).build();
			Season bcsS4 = Season.builder().seasonNumber(4).firstAired(LocalDate.of(2018, 8, 6)).build();
			Season bcsS5 = Season.builder().seasonNumber(5).firstAired(LocalDate.of(2020, 2, 23)).build();

			Series better_call_saul = Series.builder()
					.title("Better Call Saul")
					.genre(Genre.DRAMA)
					.genre(Genre.COMEDY)
					.seasons(Arrays.asList(bcsS1, bcsS2, bcsS3, bcsS4, bcsS5))
					.firstAired(LocalDate.of(2015, 2, 8))
					.rating(Rating.FIVE)
					.personalRating(Rating.FOUR)
					.description("Better Call Saul follows the transformation of James \"Jimmy\" McGill into the personality of the flamboyant criminal lawyer Saul Goodman from Breaking Bad")
					.build();

			bcsS1.setSeries(better_call_saul);
			bcsS2.setSeries(better_call_saul);
			bcsS3.setSeries(better_call_saul);
			bcsS4.setSeries(better_call_saul);
			bcsS5.setSeries(better_call_saul);

			seriesRepository.save(better_call_saul);
		};
	}

}
