package gr.android.cineflix.ui.homeScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import gr.android.cineflix.common.MovieEntityMapper;
import gr.android.cineflix.common.MovieMapper;
import gr.android.cineflix.domain.repoInterfaces.MovieRepository;
import gr.android.cineflix.data.database.MovieEntity;
import gr.android.cineflix.domain.models.MovieUi;
import gr.android.cineflix.common.WifiMonitor;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MovieViewModel extends ViewModel {
    private MovieRepository repository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private final ExecutorService executorService;
    private Boolean hasError = false;
    private WifiMonitor wifiMonitor;

    @Inject
    public MovieViewModel(MovieRepository repository) {
        this.repository = repository;
        this.executorService = Executors.newSingleThreadExecutor();
//        observeDatabaseChanges();
    }

    public void initWifiMonitoring(Context context) {
        wifiMonitor = new WifiMonitor(context, new WifiMonitor.WifiCallback() {
            @Override
            public void onWifiConnected() {
                hasError = false;
                refreshMovies(); // Fetch from server when WiFi becomes available
            }

            @Override
            public void onWifiDisconnected() {
            }

            @Override
            public void onInitialState(boolean isWifiConnected) {
                if (isWifiConnected) {
                    hasError = false;
                    refreshMovies(); // Fetch from server if WiFi is already connected
                } else {
                    hasError = true;
                    observeDatabaseChanges(); // Observe database changes when WiFi is not available
                }
            }

        });
        wifiMonitor.startMonitoring();
    }

    private MutableLiveData<List<MovieUi>> moviesLiveData = new MutableLiveData<>();

    public LiveData<List<MovieUi>> getMovies() {
        return moviesLiveData;
    }

    protected void refreshMovies() {
        disposable.add(repository.getPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MovieMapper::mapToUiMovieList)
                .subscribe(
                        movies -> moviesLiveData.postValue(movies),
                        throwable -> {
                            Log.e("MovieViewModel", "Error fetching movies", throwable);
                            hasError = true;
                            observeDatabaseChanges();
                        }
                ));
    }

    private void observeDatabaseChanges() {
        LiveData<List<MovieEntity>> allMovies = repository.getAllMovies();
        allMovies.observeForever(movieEntities -> {
            List<MovieUi> dbMovies = MovieEntityMapper.mapToUiMovieList(movieEntities);
            List<MovieUi> currentMovies = moviesLiveData.getValue();
            if (currentMovies == null) {
                currentMovies = new ArrayList<>();
            }

            // Create a map for quick lookup of current movies by ID
            Map<Integer, MovieUi> currentMoviesMap = new HashMap<>();
            for (MovieUi movie : currentMovies) {
                currentMoviesMap.put(movie.getId(), movie);
            }

            ArrayList<MovieUi> newMovieList = new ArrayList<>(currentMovies);

            if (!hasError) {
                for (MovieUi dbMovie : dbMovies) {
                    MovieUi currentMovie = currentMoviesMap.get(dbMovie.getId());
                    if (currentMovie != null) {
                        if (dbMovie.isFavorite() != currentMovie.isFavorite()) {
                            // Create a new instance of MovieUi with the updated favorite status
                            MovieUi updatedMovie = new MovieUi(
                                    currentMovie.getId(),
                                    currentMovie.getTitle(),
                                    currentMovie.getOverview(),
                                    currentMovie.getPosterPath(),
                                    currentMovie.getVoteAverage(),
                                    dbMovie.isFavorite(),
                                    dbMovie.getBackdropPath(),
                                    dbMovie.getReleaseDate());
                            int index = newMovieList.indexOf(currentMovie);
                            newMovieList.set(index, updatedMovie);
                        }
                    }
                }
            } else {
                newMovieList = new ArrayList<>(dbMovies);
            }

            moviesLiveData.postValue(newMovieList);
        });
    }


    public void toggleFavorite(MovieUi movie) {
        executorService.execute(() -> {
            repository.toggleFavorite(MovieEntityMapper.mapToMovieEntity(movie));
            refreshMovies();
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
        if (wifiMonitor != null) {
            wifiMonitor.stopMonitoring();
        }
        executorService.shutdown();
    }
}
