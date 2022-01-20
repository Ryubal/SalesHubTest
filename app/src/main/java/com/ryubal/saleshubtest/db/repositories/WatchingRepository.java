package com.ryubal.saleshubtest.db.repositories;

import android.app.Application;

import com.ryubal.saleshubtest.db.SalesHubDB;
import com.ryubal.saleshubtest.db.daos.WatchingDao;
import com.ryubal.saleshubtest.db.entities.Watching;
import com.ryubal.saleshubtest.db.entities.WatchingAndMovie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WatchingRepository {
	private final WatchingDao dao;

	public WatchingRepository(Application application) {
		SalesHubDB db = SalesHubDB.getDB(application);
		dao = db.watchingDao();
	}

	public LiveData<List<WatchingAndMovie>> getWatchlist() {
		return dao.getAll();
	}

	public void insert(Watching movie) {
		SalesHubDB.dbWriteExecutor.execute(() -> dao.insert(movie));
	}

	public void delete(WatchingAndMovie watching) {
		SalesHubDB.dbWriteExecutor.execute(() -> {
			dao.delete(watching.getId());
		});
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