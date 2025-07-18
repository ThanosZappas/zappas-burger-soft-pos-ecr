package gr.android.cineflix.data.repo;

import java.util.List;
import java.util.stream.Collectors;

import gr.android.cineflix.common.MovieMapper;
import gr.android.cineflix.data.model.details.reviews.MovieDetailsReviewApiResponse;
import gr.android.cineflix.data.networkServices.MovieApiService;
import gr.android.cineflix.domain.models.CastUi;
import gr.android.cineflix.domain.models.DetailsBasicUi;
import gr.android.cineflix.domain.models.ReviewsUi;
import gr.android.cineflix.domain.models.MovieUi;
import gr.android.cineflix.domain.repoInterfaces.MovieDetailsRepository;
import io.reactivex.rxjava3.core.Observable;

public class MovieDetailsRepositoryImpl implements MovieDetailsRepository {

    private MovieApiService apiService;

    public MovieDetailsRepositoryImpl(MovieApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<DetailsBasicUi> getMovieById(Integer movie_id) {
        return apiService.getMovieById(movie_id)
                .map(MovieMapper::mapToUiModel);
    }

    @Override
    public Observable<List<ReviewsUi>> getReviewsById(Integer movie_id) {
        return apiService.getReviewsById(movie_id)
                .map(MovieDetailsReviewApiResponse::getResults)
                .map(movies ->
                        movies.stream()
                                .map(MovieMapper::mapToUiModel)
                                .collect(Collectors.toList())
                );
    }

    @Override
    public Observable<List<CastUi>> getMovieCredits(Integer movie_id) {
        return apiService.getMovieCredits(movie_id)
                .map(response -> response.getCast().stream()
                        .map(cast -> new CastUi(cast.getId(), cast.getName()))
                        .collect(Collectors.toList()));
    }

    @Override
    public Observable<List<MovieUi>> getSimilarMovies(Integer movie_id) {
        return apiService.getSimilarMovies(movie_id)
                .map(response -> response.getResults().stream()
                        .map(movie -> new MovieUi(
                                movie.getId(),
                                movie.getTitle(),
                                movie.getOverview(),
                                movie.getPosterPath(),
                                movie.getVoteAverage(),
                                false,
                                movie.getBackdropPath(),
                                movie.getReleaseDate()
                        ))
                        .collect(Collectors.toList()));
    }
}
