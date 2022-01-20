package com.ryubal.saleshubtest.db.daos;

import com.ryubal.saleshubtest.db.entities.Watching;
import com.ryubal.saleshubtest.db.entities.WatchingAndMovie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WatchingDao {
	@Insert
	void insert(Watching watching);

	@Query("SELECT EXISTS(SELECT * FROM watchlist WHERE LOWER(name) = LOWER(:name))")
	boolean doesMovieExist(String name);

	@Query("SELECT watchlist.id AS id, watchlist.name AS name, _movies.id AS movie_id FROM watchlist LEFT JOIN movies AS _movies ON LOWER(_movies.name) = LOWER(watchlist.name)")
	LiveData<List<WatchingAndMovie>> getAll();

	@Query("DELETE FROM watchlist WHERE id=:id")
	void delete(int id);
}