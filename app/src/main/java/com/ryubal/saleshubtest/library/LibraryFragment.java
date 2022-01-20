package com.ryubal.saleshubtest.library;

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
import com.ryubal.saleshubtest.databinding.FragmentLibraryBinding;
import com.ryubal.saleshubtest.db.entities.Movie;
import com.ryubal.saleshubtest.library.adapters.MoviesAdapter;
import com.ryubal.saleshubtest.common.new_movie.NewMovieDialog;

import java.util.List;

public class LibraryFragment extends Fragment implements MoviesAdapter.MovieListener {

	private FragmentLibraryBinding binding;
	private LibraryViewModel viewModel;
	private MoviesAdapter adapter;

	public static LibraryFragment newInstance() {
		return new LibraryFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = FragmentLibraryBinding.inflate(inflater, container, false);

		setupUI();

		return binding.getRoot();
	}

	private void setupUI() {
		// Set adapter
		adapter = new MoviesAdapter(new MoviesAdapter.MoviesDiff(), this);

		// Assign adapter to our RecyclerView
		binding.recyclerView.setAdapter(adapter);
		binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		// Get ViewModel
		viewModel = new ViewModelProvider(requireActivity()).get(LibraryViewModel.class);

		// Start observing movies list
		viewModel.getMovies().observe(requireActivity(), this::onMoviesChange);

		// Attach listener for when trying to create a new movie
		getChildFragmentManager().setFragmentResultListener("onNewMovie", this, (requestKey, result) -> {
			String name = result.getString("movieName");

			onAddMovie(name);
		});

		// Attach fab to add a new movie
		binding.fab.setOnClickListener(v -> onAddClick());
	}

	// When there's a change in the movies list
	private void onMoviesChange(List<Movie> movies) {
		binding.textViewEmpty.setVisibility(movies.size() == 0 ? View.VISIBLE : View.GONE);
		binding.recyclerView.setVisibility(movies.size() == 0 ? View.GONE : View.VISIBLE);

		// Update list
		adapter.submitList(movies);
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
						.setMessage(getString(R.string.new_movie_error_already_exists))
						.setPositiveButton(android.R.string.ok, null)
						.show();
			}else{
				// Movie doesn't exist, let's insert it
				viewModel.insert(new Movie(name));

				Toast.makeText(getContext(), getString(R.string.new_movie_success), Toast.LENGTH_SHORT).show();
			}
		});
	}

	// When clicking on "delete" to delete a movie
	@Override
	public void onDelete(Movie movie) {
		viewModel.delete(movie);
	}

	// When changing the "watched" status of a movie
	@Override
	public void onWatchStatusChanged(Movie movie, boolean isWatched) {
		viewModel.setIsWatched(movie, isWatched);
	}
}