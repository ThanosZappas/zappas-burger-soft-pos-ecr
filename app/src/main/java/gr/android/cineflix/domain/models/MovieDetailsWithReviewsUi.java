package gr.android.cineflix.domain.models;


import java.util.List;

public class MovieDetailsWithReviewsUi {

    private final DetailsBasicUi movieDetails;
    private final List<ReviewsUi> reviews;
    private final List<CastUi> cast;

    public MovieDetailsWithReviewsUi(DetailsBasicUi movieDetails, List<ReviewsUi> reviews, List<CastUi> cast) {
        this.movieDetails = movieDetails;
        this.reviews = reviews;
        this.cast = cast;
    }

    public DetailsBasicUi getMovieDetails() {
        return movieDetails;
    }

    public List<CastUi> getCastList() {
        return cast;
    }

    public List<ReviewsUi> getReviews() {
        return reviews;
    }
}
