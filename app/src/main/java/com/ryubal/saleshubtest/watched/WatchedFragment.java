package com.ryubal.saleshubtest.watched;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryubal.saleshubtest.R;

public class WatchedFragment extends Fragment {

	private WatchedViewModel mViewModel;

	public static WatchedFragment newInstance() {
		return new WatchedFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
													 @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_watched, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = new ViewModelProvider(this).get(WatchedViewModel.class);
		// TODO: Use the ViewModel
	}

}