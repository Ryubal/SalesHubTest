package com.ryubal.saleshubtest.watched.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryubal.saleshubtest.R;
import com.ryubal.saleshubtest.databinding.FragmentLibraryRowBinding;
import com.ryubal.saleshubtest.databinding.FragmentWatchedRowBinding;
import com.ryubal.saleshubtest.db.entities.Movie;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class WatchedAdapter extends ListAdapter<Movie, WatchedAdapter.ViewHolder> {

	public interface WatchedListener {
		void onDelete(Movie movie);
	}

	private final WatchedListener listener;

	public WatchedAdapter(DiffUtil.ItemCallback<Movie> diffUtilCallback, WatchedListener listener) {
		super(diffUtilCallback);
		this.listener = listener;
	}

	@NonNull
	@Override
	public WatchedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_watched_row, parent, false);

		FragmentWatchedRowBinding binding = FragmentWatchedRowBinding.bind(v);

		return new WatchedAdapter.ViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(@NonNull WatchedAdapter.ViewHolder holder, int position) {
		Movie movie = getItem(position);

		holder.binding.imageViewDelete.setOnClickListener(v -> {
			listener.onDelete(movie);
		});

		holder.binding.textViewName.setText(movie.getName());
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final FragmentWatchedRowBinding binding;

		public ViewHolder(FragmentWatchedRowBinding binding) {
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