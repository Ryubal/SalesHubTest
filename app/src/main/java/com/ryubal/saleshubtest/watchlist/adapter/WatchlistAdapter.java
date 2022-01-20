package com.ryubal.saleshubtest.watchlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryubal.saleshubtest.R;
import com.ryubal.saleshubtest.databinding.FragmentWatchlistRowBinding;
import com.ryubal.saleshubtest.db.entities.WatchingAndMovie;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class WatchlistAdapter extends ListAdapter<WatchingAndMovie, WatchlistAdapter.ViewHolder> {

	public interface WatchingListener {
		void onDelete(WatchingAndMovie movie);
	}

	private final WatchingListener listener;

	public WatchlistAdapter(DiffUtil.ItemCallback<WatchingAndMovie> diffUtilCallback, WatchlistAdapter.WatchingListener listener) {
		super(diffUtilCallback);
		this.listener = listener;
	}

	@NonNull
	@Override
	public WatchlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_watchlist_row, parent, false);

		FragmentWatchlistRowBinding binding = FragmentWatchlistRowBinding.bind(v);

		return new WatchlistAdapter.ViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(@NonNull WatchlistAdapter.ViewHolder holder, int position) {
		WatchingAndMovie movie = getItem(position);

		holder.binding.textViewExistsInLibrary.setVisibility(movie.getMovie_id() == 0 ? View.GONE : View.VISIBLE);

		holder.binding.imageViewDelete.setOnClickListener(v -> {
			listener.onDelete(movie);
		});

		holder.binding.textViewName.setText(movie.getName());
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final FragmentWatchlistRowBinding binding;

		public ViewHolder(FragmentWatchlistRowBinding binding) {
			super(binding.getRoot());

			this.binding = binding;
		}
	}

	// Using DiffUtil instead of having to call notifyDataSetChanged everytime we have a change in our data
	// (for better performance)
	public static class MoviesDiff extends DiffUtil.ItemCallback<WatchingAndMovie> {
		@Override
		public boolean areItemsTheSame(@NonNull WatchingAndMovie oldItem, @NonNull WatchingAndMovie newItem) {
			return oldItem == newItem;
		}

		@Override
		public boolean areContentsTheSame(@NonNull WatchingAndMovie oldItem, @NonNull WatchingAndMovie newItem) {
			return oldItem.getName().equals(newItem.getName());
		}
	}
}