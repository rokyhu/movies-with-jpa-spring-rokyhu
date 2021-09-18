package com.codecool.roky_jpa_movies.repository;

import com.codecool.roky_jpa_movies.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class AllRepositoryTest {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addNewSimple_persistedToDatabase() {
        Series breaking_bad = Series.builder()
                .title("Breaking Bad")
                .firstAired(LocalDate.of(2008, 1, 20))
                .build();

        seriesRepository.save(breaking_bad);

        List<Series> seriesList = seriesRepository.findAll();

        assertThat(seriesList).hasSize(1);
    }

    @Test
    public void addTwoWithTheSameUniqueField_throwsException() {
        Series breaking_bad = Series.builder()
                .title("Breaking Bad")
                .firstAired(LocalDate.of(2008, 1, 20))
                .build();

        seriesRepository.save(breaking_bad);

        Series better_call_saul = Series.builder()
                .title("Breaking Bad")  // Title should be unique!
                .firstAired(LocalDate.of(2015, 2, 8))
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            seriesRepository.saveAndFlush(better_call_saul);  // TODO Roky Understand save & flush better!
        });
    }

    @Test
    public void addWithoutRequiredField_throwsException() {
        Series breaking_bad = Series.builder()
                .title("Breaking Bad") // Missing firstAired!
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> seriesRepository.save(breaking_bad));
    }

    @Test
    public void addGenresWithSeries_genresArePersisted() {

        Series breaking_bad = Series.builder()
                .title("Breaking Bad")
                .firstAired(LocalDate.of(2008, 1, 20))
                .genres(Arrays.asList(Genre.DRAMA, Genre.CRIME))
                .build();

        seriesRepository.save(breaking_bad);

        // entityManager.clear();  // TODO Ask why entityManager.clear() is NOT needed here? (see below example)

        List<Series> seriesList = seriesRepository.findAll();

        assertThat(seriesList).anyMatch(series -> series.getGenres().contains(Genre.DRAMA) && series.getGenres().contains(Genre.CRIME));
    }

    @Test
    public void addObjectWithTransientField_transientFieldShouldNotBePersisted() {

        Series breaking_bad = Series.builder()
                .title("Breaking Bad")
                .firstAired(LocalDate.of(2008, 1, 20))
                .rating(Rating.FIVE)
                .personalRating(Rating.ONE)
                .build();

        seriesRepository.save(breaking_bad);

        entityManager.clear();  // TODO Ask why entityManager.clear() is needed here?

        List<Series> seriesList = seriesRepository.findAll();

        assertThat(seriesList).allMatch(series -> series.getPersonalRating() == null);

    }

    @Test
    public void addEpisodesViaSeasonsAndSeries_episodesArePersistedAndDeletedWithSeries() {

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

        bbS1.setSeries(breaking_bad);
        bbS2.setSeries(breaking_bad);
        bbS3.setSeries(breaking_bad);
        bbS4.setSeries(breaking_bad);
        bbS5.setSeries(breaking_bad);

        seriesRepository.saveAndFlush(breaking_bad);
//        entityManager.clear();

        assertThat(seasonRepository.findAll())
                .hasSize(5)
                .anyMatch(season -> season.getSeasonNumber().equals(5));

        assertThat(episodeRepository.findAll())
                .hasSize(7)
                .anyMatch(episode -> episode.getEpisodeNumber().equals(7));

        seriesRepository.deleteAll();
//        entityManager.clear();

        assertThat(seasonRepository.findAll())
                .hasSize(0);

        assertThat(episodeRepository.findAll())
                .hasSize(0);

    }

}