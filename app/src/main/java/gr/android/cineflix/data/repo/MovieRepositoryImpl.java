package gr.android.cineflix.data.repo;

import androidx.lifecycle.LiveData;

import java.util.List;

import gr.android.cineflix.data.model.home.MovieRemote;
import gr.android.cineflix.data.model.home.MovieApiResponse;
import gr.android.cineflix.data.networkServices.MovieApiService;
import gr.android.cineflix.data.database.MovieDao;
import gr.android.cineflix.data.database.MovieEntity;
import gr.android.cineflix.domain.repoInterfaces.MovieRepository;
import io.reactivex.rxjava3.core.Observable;

public class MovieRepositoryImpl implements MovieRepository {
    private MovieApiService apiService;
    private MovieDao movieDao;


    public MovieRepositoryImpl(MovieApiService apiService, MovieDao movieDao) {
        this.apiService = apiService;
        this.movieDao = movieDao;
    }

    @Override
    public Observable<List<MovieRemote>> getPopularMovies() {
        return apiService.getPopularMovies()
                .map(MovieApiResponse::getResults)
                .doOnNext(movies -> {
                    for (MovieRemote movie : movies) {
                        // Check if movie is already in local database and mark as favorite
                        MovieEntity localMovie = movieDao.getMovieById(movie.getId());
                        if (localMovie != null) {
                            movie.setFavorite(true);
                        }
                    }
                });
    }

    @Override
    public LiveData<List<MovieEntity>> getAllMovies() {
        return movieDao.getAll();
    }

    @Override
    public void toggleFavorite(MovieEntity movie) {
        if (movie.isFavorite()) {
            movieDao.delete(movie);
        } else {
            movie.setFavorite(!movie.isFavorite());
            movieDao.insert(movie);
        }
    }
}
