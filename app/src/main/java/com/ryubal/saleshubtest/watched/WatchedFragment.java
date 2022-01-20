package com.ryubal.saleshubtest.watched;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryubal.saleshubtest.R;
import com.ryubal.saleshubtest.databinding.FragmentWatchedBinding;
import com.ryubal.saleshubtest.db.entities.Movie;
import com.ryubal.saleshubtest.watched.adapters.WatchedAdapter;

import java.util.List;

public class WatchedFragment extends Fragment implements WatchedAdapter.WatchedListener {

	private FragmentWatchedBinding binding;
	private WatchedViewModel viewModel;
	private WatchedAdapter adapter;

	public static WatchedFragment newInstance() {
		return new WatchedFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = FragmentWatchedBinding.inflate(inflater, container, false);

		setupUI();

		return binding.getRoot();
	}

	private void setupUI() {
		// Set adapter
		adapter = new WatchedAdapter(new WatchedAdapter.MoviesDiff(), this);

		// Assign adapter to our recyclerview
		binding.recyclerView.setAdapter(adapter);
		binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		// Get viewmodel
		viewModel = new ViewModelProvider(requireActivity()).get(WatchedViewModel.class);

		// Start observing watched movies
		viewModel.getWatchedMovies().observe(requireActivity(), this::onMoviesChange);
	}

	private void onMoviesChange(List<Movie> movies) {
		binding.textViewEmpty.setVisibility(movies.size() == 0 ? View.VISIBLE : View.GONE);
		binding.recyclerView.setVisibility(movies.size() == 0 ? View.GONE : View.VISIBLE);

		// Update list
		adapter.submitList(movies);
	}

	@Override
	public void onDelete(Movie movie) {
		viewModel.delete(movie);
	}
}