package com.ryubal.saleshubtest.db;

import android.content.Context;

import com.ryubal.saleshubtest.db.daos.MovieDao;
import com.ryubal.saleshubtest.db.daos.WatchingDao;
import com.ryubal.saleshubtest.db.entities.Movie;
import com.ryubal.saleshubtest.db.entities.Watching;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
	entities = {
		Movie.class,
		Watching.class
	},
	version = 1,
	exportSchema = false
)
public abstract class SalesHubDB extends RoomDatabase {
	public abstract MovieDao movieDao();
	public abstract WatchingDao watchingDao();

	private static volatile SalesHubDB INSTANCE;
	private static final int NUMBER_OF_THREADS = 4;
	public static final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public static SalesHubDB getDB(final Context context) {
		if(INSTANCE == null) {
			synchronized(SalesHubDB.class) {
				if(INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(
							context.getApplicationContext(),
							SalesHubDB.class,
							"saleshub_db"
					).build();
				}
			}
		}

		return INSTANCE;
	}
}
