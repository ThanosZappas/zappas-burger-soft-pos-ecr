package gr.android.cineflix.ui.detailsScreen;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import gr.android.cineflix.domain.models.MovieDetailsWithReviewsUi;
import gr.android.cineflix.domain.models.MovieUi;
import gr.android.cineflix.domain.usecase.DetailsUseCase;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DetailsViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final DetailsUseCase detailsUseCase;

    @Inject
    public DetailsViewModel(
            DetailsUseCase detailsUseCase
    ) {
        this.detailsUseCase = detailsUseCase;
    }

    private final MutableLiveData<MovieDetailsWithReviewsUi> movieDetailsWithReviewsLiveData = new MutableLiveData<>();
    public LiveData<MovieDetailsWithReviewsUi> movieDetailsLiveData = movieDetailsWithReviewsLiveData;

    private final MutableLiveData<List<MovieUi>> similarMoviesLiveData = new MutableLiveData<>();

    public LiveData<List<MovieUi>> getSimilarMovies() {
        return similarMoviesLiveData;
    }

    public void fetchMovieDetailsWithReviews(Integer movieId) {
        disposable.add(detailsUseCase.execute(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieDetailsWithReviewsLiveData::postValue,
                        throwable -> Log.e("DetailsViewModel", "Error fetching movie details with reviews", throwable)
                ));
    }

    public void fetchSimilarMovies(Integer movieId) {
        disposable.add(detailsUseCase.getSimilarMovies(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> similarMoviesLiveData.postValue(new ArrayList<>(movies)),
                        throwable -> Log.e("DetailsViewModel", "Error fetching similar movies", throwable)
                ));
    }

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }
}
