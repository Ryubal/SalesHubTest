package com.ryubal.saleshubtest.library.new_movie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.ryubal.saleshubtest.databinding.FragmentLibraryNewDialogBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NewMovieDialog extends DialogFragment implements DialogInterface.OnShowListener {

	private FragmentLibraryNewDialogBinding binding;

	public NewMovieDialog() {

	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		binding = FragmentLibraryNewDialogBinding.inflate(LayoutInflater.from(getActivity()));

		// TODO - Export strings

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setTitle("New movie")
				.setView(binding.getRoot())
				.setPositiveButton(android.R.string.ok, null);

		AlertDialog dialog = builder.create();
		dialog.setOnShowListener(this);

		return dialog;
	}

	@Override
	public void onShow(DialogInterface dialogInterface) {
		// To avoid closing the dialog after pressing OK, we'll have to get the button
		// after showing it, and then attach the listener
		AlertDialog dialog = (AlertDialog) getDialog();

		dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
			String name = binding.textInputName.getEditText().getText().toString();

			if(name.isEmpty()) {
				binding.textInputName.setError("Please enter a movie name");
				return;
			}

			binding.textInputName.setError(null);

			Bundle result = new Bundle();
			result.putString("movieName", name);

			getParentFragmentManager().setFragmentResult("onNewMovie", result);

			dialog.dismiss();
		});
	}
}
