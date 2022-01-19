package com.ryubal.saleshubtest.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
	@PrimaryKey(autoGenerate = true)
	@NonNull
	private int id;

	private String name;

	@ColumnInfo(defaultValue = "0")
	private int isWatched;

	public Movie(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsWatched() {
		return isWatched;
	}

	public void setIsWatched(int isWatched) {
		this.isWatched = isWatched;
	}
}
