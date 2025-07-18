package gr.android.cineflix.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import gr.android.cineflix.domain.models.CastUi;
import gr.android.cineflix.domain.repoInterfaces.MovieDetailsRepository;
import gr.android.cineflix.domain.models.DetailsBasicUi;
import gr.android.cineflix.domain.models.MovieDetailsWithReviewsUi;
import gr.android.cineflix.domain.models.ReviewsUi;
import gr.android.cineflix.domain.models.MovieUi;
import io.reactivex.rxjava3.core.Observable;

public class DetailsUseCase {

    private MovieDetailsRepository movieDetailsRepository;

    @Inject
    public DetailsUseCase(MovieDetailsRepository movieDetailsRepository) {
        this.movieDetailsRepository = movieDetailsRepository;
    }

    public Observable<MovieDetailsWithReviewsUi> execute(Integer movieId) {
        Observable<DetailsBasicUi> movieDetailsObservable = movieDetailsRepository.getMovieById(movieId);
        Observable<List<ReviewsUi>> reviewsObservable = movieDetailsRepository.getReviewsById(movieId);
        Observable<List<CastUi>> castObservable = movieDetailsRepository.getMovieCredits(movieId);

        return Observable.combineLatest(
                movieDetailsObservable,
                reviewsObservable,
                castObservable,
                MovieDetailsWithReviewsUi::new
        );
    }

    public Observable<List<MovieUi>> getSimilarMovies(Integer movieId) {
        return movieDetailsRepository.getSimilarMovies(movieId);
    }
}
