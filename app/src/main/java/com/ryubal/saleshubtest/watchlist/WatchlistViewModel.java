package com.ryubal.saleshubtest.watchlist;

import android.app.Application;

import com.ryubal.saleshubtest.db.entities.Watching;
import com.ryubal.saleshubtest.db.entities.WatchingAndMovie;
import com.ryubal.saleshubtest.db.repositories.WatchingRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WatchlistViewModel extends AndroidViewModel {
	private final WatchingRepository repository;

	private final LiveData<List<WatchingAndMovie>> movies;

	public WatchlistViewModel(Application application) {
		super(application);
		repository = new WatchingRepository(application);
		movies = repository.getWatchlist();
	}

	public LiveData<List<WatchingAndMovie>> getMovies() {
		return movies;
	}

	public LiveData<Boolean> doesMovieExist(String movie) {
		return repository.doesMovieExist(movie);
	}

	public void insert(Watching movie) {
		repository.insert(movie);
	}

	public void delete(WatchingAndMovie movie) {
		repository.delete(movie);
	}
}