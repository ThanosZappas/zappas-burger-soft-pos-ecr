package gr.android.cineflix.domain.repoInterfaces;

import androidx.lifecycle.LiveData;

import java.util.List;

import gr.android.cineflix.data.model.home.MovieRemote;
import gr.android.cineflix.data.database.MovieEntity;
import io.reactivex.rxjava3.core.Observable;

public interface MovieRepository {

    Observable<List<MovieRemote>> getPopularMovies();
    void toggleFavorite(MovieEntity movie);
    LiveData<List<MovieEntity>> getAllMovies();
}

