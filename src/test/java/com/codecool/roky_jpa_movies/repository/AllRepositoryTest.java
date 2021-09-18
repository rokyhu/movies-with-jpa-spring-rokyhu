package com.codecool.roky_jpa_movies.repository;

import com.codecool.roky_jpa_movies.entity.Series;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class AllRepositoryTest {

    @Autowired
    private SeriesRepository seriesRepository;

//    @Autowired
//    private SeasonRepository seasonRepository;
//
//    @Autowired
//    private EpisodeRepository episodeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void addNewSimple_persistedToDatabase() {
        Series breaking_bad = Series.builder()
                .title("Breaking Bad")
                .airDate(LocalDate.of(2008, 1, 20))
                .build();

        seriesRepository.save(breaking_bad);

        List<Series> seriesList = seriesRepository.findAll();

        assertThat(seriesList).hasSize(1);
    }

    @Test
    public void addTwoWithTheSameUniqueID_throwsException() {
        Series breaking_bad = Series.builder()
                .title("Breaking Bad")
                .airDate(LocalDate.of(2008, 1, 20))
                .build();

        seriesRepository.save(breaking_bad);

        Series better_call_saul = Series.builder()
                .title("Breaking Bad")
                .airDate(LocalDate.of(2015, 2, 8))
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            seriesRepository.saveAndFlush(better_call_saul);  // TODO Understand save & flush better!
        });
    }

    @Test
    public void addNewSimpleWithoutRequiredField_throwsException() {
        Series breaking_bad = Series.builder()
                .title("Breaking Bad")
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> seriesRepository.save(breaking_bad));
    }

}