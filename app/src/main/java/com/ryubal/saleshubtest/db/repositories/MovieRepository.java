package com.ryubal.saleshubtest.db.repositories;

import android.app.Application;

import com.ryubal.saleshubtest.db.SalesHubDB;
import com.ryubal.saleshubtest.db.daos.MovieDao;
import com.ryubal.saleshubtest.db.entities.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MovieRepository {
	private MovieDao dao;
	private LiveData<List<Movie>> movies;

	public MovieRepository(Application application) {
		SalesHubDB db = SalesHubDB.getDB(application);
		dao = db.movieDao();
		movies = dao.getAll();
	}

	public LiveData<List<Movie>> getMovies() {
		return movies;
	}

	public void insert(Movie movie) {
		SalesHubDB.dbWriteExecutor.execute(() -> dao.insert(movie));
	}
}