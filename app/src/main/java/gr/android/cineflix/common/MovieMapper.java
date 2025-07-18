package gr.android.cineflix.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gr.android.cineflix.data.model.details.basicDetails.GenreRemote;
import gr.android.cineflix.data.model.details.basicDetails.MovieDetailsRemote;
import gr.android.cineflix.data.model.details.reviews.ReviewsRemote;
import gr.android.cineflix.data.model.home.MovieRemote;
import gr.android.cineflix.domain.models.DetailsBasicUi;
import gr.android.cineflix.domain.models.GenreUi;
import gr.android.cineflix.domain.models.MovieUi;
import gr.android.cineflix.domain.models.ReviewsUi;

public class MovieMapper {
    public static MovieUi mapToUiMovie(MovieRemote movie) {
        return new MovieUi(
                movie.getId(),
                movie.getTitle(),
                movie.getOverview(),
                movie.getPosterPath(),
                movie.getVoteAverage(),
                movie.isFavorite(),
                movie.getBackdropPath(),
                Helper.formatDate(movie.getReleaseDate())
        );
    }

    public static List<MovieUi> mapToUiMovieList(List<MovieRemote> movies) {
        List<MovieUi> uiMovies = new ArrayList<>();
        for (MovieRemote movie : movies) {
            uiMovies.add(mapToUiMovie(movie));
        }
        return uiMovies;
    }

    public static ReviewsUi mapToUiModel(ReviewsRemote remote) {
        return new ReviewsUi(
                remote.getAuthor(),
                remote.getContent(),
                remote.getCreatedAt(),
                remote.getId(),
                remote.getUpdatedAt(),
                remote.getUrl()
        );
    }

    public static GenreUi mapToUiModel(GenreRemote remote) {
        return new GenreUi(
                remote.getId(),
                remote.getName()
        );
    }

    public static DetailsBasicUi mapToUiModel(MovieDetailsRemote remote) {
        return new DetailsBasicUi(
                remote.getBackdropPath(),
                remote.getGenres().stream()
                        .map(MovieMapper::mapToUiModel)
                        .collect(Collectors.toList()),
                remote.getId(),
                remote.getOverview(),
                remote.getPosterPath() != null ? remote.getPosterPath().toString() : null,
                Helper.formatDate(remote.getReleaseDate()),
                remote.getTitle(),
                remote.getVoteAverage(),
                remote.getHomepage(),
                remote.getRuntime()
        );
    }
}
