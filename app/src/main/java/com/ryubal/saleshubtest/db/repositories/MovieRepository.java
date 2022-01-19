package com.ryubal.saleshubtest.db.repositories;

import android.app.Application;

import com.ryubal.saleshubtest.db.SalesHubDB;
import com.ryubal.saleshubtest.db.daos.MovieDao;
import com.ryubal.saleshubtest.db.entities.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieRepository {
	private final MovieDao dao;

	public MovieRepository(Application application) {
		SalesHubDB db = SalesHubDB.getDB(application);
		dao = db.movieDao();
	}

	public LiveData<List<Movie>> getMovies() {
		return dao.getAll();
	}

	public LiveData<List<Movie>> getWatchedMovies() {
		return dao.getAllWatched();
	}

	public void insert(Movie movie) {
		SalesHubDB.dbWriteExecutor.execute(() -> dao.insert(movie));
	}

	public void setIsWatched(Movie movie, boolean isWatched) {
		SalesHubDB.dbWriteExecutor.execute(() -> {
			dao.setIsWatched(isWatched ? 1 : 0, movie.getId());
		});
	}

	public void delete(Movie movie) {
		SalesHubDB.dbWriteExecutor.execute(() -> dao.delete(movie.getId()));
	}

	public LiveData<Boolean> doesMovieExist(String movie) {
		MutableLiveData<Boolean> data = new MutableLiveData<>();
		SalesHubDB.dbWriteExecutor.execute(() -> {
			boolean doesMovieExist = dao.doesMovieExist(movie);
			data.postValue(doesMovieExist);
		});

		return data;
	}
}