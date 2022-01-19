package com.ryubal.saleshubtest.db.daos;

import com.ryubal.saleshubtest.db.entities.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MovieDao {
	@Insert
	void insert(Movie movie);

	@Query("SELECT * FROM movies")
	LiveData<List<Movie>> getAll();

	@Query("SELECT * FROM movies WHERE isWatched = 1")
	LiveData<List<Movie>> getAllWatched();

	@Query("SELECT EXISTS(SELECT * FROM movies WHERE name = :name)")
	boolean doesMovieExist(String name);

	@Query("UPDATE movies SET isWatched=:isWatched WHERE id=:id")
	void setIsWatched(int isWatched, int id);

	@Query("DELETE FROM movies WHERE id=:id")
	void delete(int id);
}