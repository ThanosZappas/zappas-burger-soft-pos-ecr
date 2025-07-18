package gr.android.cineflix.common;

import java.util.List;
import java.util.stream.Collectors;

import gr.android.cineflix.data.database.MovieEntity;
import gr.android.cineflix.domain.models.MovieUi;

public class MovieEntityMapper {

    public static MovieEntity mapToMovieEntity(MovieUi movie) {
        return new MovieEntity(
                movie.getId(),
                movie.getTitle(),
                movie.getOverview(),
                movie.getPosterPath(),
                movie.getVoteAverage(),
                movie.isFavorite(),
                movie.getBackdropPath(),
                movie.getReleaseDate()
        );
    }

    public static MovieUi mapToMovieUi(MovieEntity movie) {
        return new MovieUi(
                movie.getId(),
                movie.getTitle(),
                movie.getOverview(),
                movie.getPosterPath(),
                movie.getVoteAverage(),
                movie.isFavorite(),
                movie.getBackdropPath(),
                movie.getReleaseDate()
        );
    }

    public static List<MovieUi> mapToUiMovieList(List<MovieEntity> movieEntities) {
        return movieEntities.stream()
                .map(MovieEntityMapper::mapToMovieUi)
                .collect(Collectors.toList());
    }
}
