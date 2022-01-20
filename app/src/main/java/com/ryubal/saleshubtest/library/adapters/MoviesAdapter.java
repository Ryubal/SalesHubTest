package com.ryubal.saleshubtest.library.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryubal.saleshubtest.R;
import com.ryubal.saleshubtest.databinding.FragmentLibraryRowBinding;
import com.ryubal.saleshubtest.db.entities.Movie;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends ListAdapter<Movie, MoviesAdapter.ViewHolder> {

	public interface MovieListener {
		void onDelete(Movie movie);
		void onWatchStatusChanged(Movie movie, boolean isWatched);
	}

	private final MovieListener listener;

	public MoviesAdapter(DiffUtil.ItemCallback<Movie> diffUtilCallback, MovieListener listener) {
		super(diffUtilCallback);
		this.listener = listener;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_library_row, parent, false);

		FragmentLibraryRowBinding binding = FragmentLibraryRowBinding.bind(v);

		return new ViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Movie movie = getItem(position);

		holder.binding.imageViewUnwatched.setVisibility(movie.getIsWatched() == 1 ? View.GONE : View.VISIBLE);
		holder.binding.imageViewWatched.setVisibility(movie.getIsWatched() == 1 ? View.VISIBLE : View.GONE);

		holder.binding.imageViewUnwatched.setOnClickListener(v1 -> {
			listener.onWatchStatusChanged(movie, true);
		});
		holder.binding.imageViewWatched.setOnClickListener(v1 -> {
			listener.onWatchStatusChanged(movie, false);
		});

		holder.binding.imageViewDelete.setOnClickListener(v -> {
			listener.onDelete(movie);
		});

		holder.binding.textViewName.setText(movie.getName());
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final FragmentLibraryRowBinding binding;

		public ViewHolder(FragmentLibraryRowBinding binding) {
			super(binding.getRoot());

			this.binding = binding;
		}
	}

	// Using DiffUtil instead of having to call notifyDataSetChanged everytime we have a change in our data
	// (for better performance)
	public static class MoviesDiff extends DiffUtil.ItemCallback<Movie> {
		@Override
		public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
			return oldItem == newItem;
		}

		@Override
		public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
			return oldItem.getName().equals(newItem.getName());
		}
	}
}