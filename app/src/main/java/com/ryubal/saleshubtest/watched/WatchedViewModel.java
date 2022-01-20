package com.ryubal.saleshubtest.watched;

import android.app.Application;

import com.ryubal.saleshubtest.db.entities.Movie;
import com.ryubal.saleshubtest.db.repositories.MovieRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WatchedViewModel extends AndroidViewModel {

	private final MovieRepository repository;

	private final LiveData<List<Movie>> watchedMovies;

	public WatchedViewModel(Application application) {
		super(application);
		repository = new MovieRepository(application);
		watchedMovies = repository.getWatchedMovies();
	}

	public LiveData<List<Movie>> getWatchedMovies() {
		return watchedMovies;
	}

	public void delete(Movie movie) {
		repository.setIsWatched(movie, false);
	}
}