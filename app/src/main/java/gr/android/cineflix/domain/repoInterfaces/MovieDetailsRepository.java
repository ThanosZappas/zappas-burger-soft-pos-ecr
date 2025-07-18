package gr.android.cineflix.domain.repoInterfaces;

import java.util.List;

import gr.android.cineflix.domain.models.CastUi;
import gr.android.cineflix.domain.models.DetailsBasicUi;
import gr.android.cineflix.domain.models.ReviewsUi;
import gr.android.cineflix.domain.models.MovieUi;
import io.reactivex.rxjava3.core.Observable;

public interface MovieDetailsRepository {

    Observable<DetailsBasicUi> getMovieById(Integer movie_id);
    Observable<List<ReviewsUi>> getReviewsById(Integer movie_id);
    Observable<List<CastUi>> getMovieCredits(Integer movie_id);
    Observable<List<MovieUi>> getSimilarMovies(Integer movie_id);
}
