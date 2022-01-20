package com.ryubal.saleshubtest.watchlist;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ryubal.saleshubtest.R;
import com.ryubal.saleshubtest.common.new_movie.NewMovieDialog;
import com.ryubal.saleshubtest.databinding.FragmentWatchlistBinding;
import com.ryubal.saleshubtest.db.entities.Watching;
import com.ryubal.saleshubtest.db.entities.WatchingAndMovie;
import com.ryubal.saleshubtest.watchlist.adapter.WatchlistAdapter;

import java.util.List;

public class WatchlistFragment extends Fragment implements WatchlistAdapter.WatchingListener {

	private FragmentWatchlistBinding binding;
	private WatchlistViewModel viewModel;
	private WatchlistAdapter adapter;

	public static WatchlistFragment newInstance() {
		return new WatchlistFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = FragmentWatchlistBinding.inflate(inflater, container, false);

		setupUI();

		return binding.getRoot();
	}

	private void setupUI() {
		// Set adapter
		adapter = new WatchlistAdapter(new WatchlistAdapter.MoviesDiff(), this);

		// Assign adapter to our RecyclerView
		binding.recyclerView.setAdapter(adapter);
		binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		// Get viewmodel
		viewModel = new ViewModelProvider(requireActivity()).get(WatchlistViewModel.class);

		// Start observing list
		viewModel.getMovies().observe(requireActivity(), this::onMoviesChange);

		// Attach listener for when trying to create a new movie
		getChildFragmentManager().setFragmentResultListener("onNewMovie", this, (requestKey, result) -> {
			String name = result.getString("movieName");

			onAddMovie(name);
		});

		// Attach fab to add a new movie
		binding.fab.setOnClickListener(v -> {
			NewMovieDialog newMovieDialog = new NewMovieDialog();
			newMovieDialog.show(getChildFragmentManager(), "");
		});
	}

	private void onMoviesChange(List<WatchingAndMovie> watchingAndMovies) {
		binding.textViewEmpty.setVisibility(watchingAndMovies.size() == 0 ? View.VISIBLE : View.GONE);
		binding.recyclerView.setVisibility(watchingAndMovies.size() == 0 ? View.GONE : View.VISIBLE);

		// Update list
		adapter.submitList(watchingAndMovies);
	}

	// When we want to add a new item
	private void onAddClick() {
		NewMovieDialog newMovieDialog = new NewMovieDialog();
		newMovieDialog.show(getChildFragmentManager(), "");
	}

	// When we submitted the form to add a new item
	private void onAddMovie(String name) {
		// Make sure this movie doesn't exist already
		viewModel.doesMovieExist(name).observe(requireActivity(), doesMovieExist -> {
			if(doesMovieExist) {
				new AlertDialog.Builder(getContext())
						.setTitle(getString(R.string.misc_error))
						.setMessage(getString(R.string.watchlist_new_error_exists))
						.setPositiveButton(android.R.string.ok, null)
						.show();
			}else{
				// Movie doesn't exist, let's insert it
				viewModel.insert(new Watching(name));

				Toast.makeText(getContext(), getString(R.string.watchlist_new_success), Toast.LENGTH_SHORT).show();
			}
		});
	}

	// When clicking on "delete" to delete a movie
	@Override
	public void onDelete(WatchingAndMovie movie) {
		viewModel.delete(movie);
	}
}