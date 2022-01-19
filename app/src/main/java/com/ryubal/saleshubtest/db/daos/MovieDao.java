package com.ryubal.saleshubtest.db.daos;

import com.ryubal.saleshubtest.db.entities.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

public interface MovieDao {
	@Insert
	void insert(Movie movie);

	@Query("SELECT * FROM movies")
	LiveData<List<Movie>> getAll();
}