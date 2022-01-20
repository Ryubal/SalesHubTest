package com.ryubal.saleshubtest.library;

import android.app.Application;

import com.ryubal.saleshubtest.db.entities.Movie;
import com.ryubal.saleshubtest.db.repositories.MovieRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LibraryViewModel extends AndroidViewModel {

	private final MovieRepository repository;

	private final LiveData<List<Movie>> movies;

	public LibraryViewModel(Application application) {
		super(application);
		repository = new MovieRepository(application);
		movies = repository.getMovies();
	}

	public LiveData<List<Movie>> getMovies() {
		return movies;
	}

	public LiveData<Boolean> doesMovieExist(String movie) {
		return repository.doesMovieExist(movie);
	}

	public void insert(Movie movie) {
		repository.insert(movie);
	}

	public void setIsWatched(Movie movie, boolean isWatched) {
		repository.setIsWatched(movie, isWatched);
	}

	public void delete(Movie movie) {
		repository.delete(movie);
	}
}