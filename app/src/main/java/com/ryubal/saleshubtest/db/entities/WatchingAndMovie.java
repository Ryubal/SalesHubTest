package com.ryubal.saleshubtest.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// To figure out which movies in "Watchlist" exist in our library too, we'll
// need to create a "combined" entity, which will let us perform an inner join
// query.
// Then, movies that don't exist in our library will have "movie_id" set to 0
@Entity(tableName = "watchlist")
public class WatchingAndMovie {
	@PrimaryKey(autoGenerate = true)
	@NonNull
	private int id;

	private String name;

	private int movie_id;

	public WatchingAndMovie(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
}
