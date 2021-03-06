package com.ryubal.saleshubtest;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ryubal.saleshubtest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

	private AppBarConfiguration appBarConfiguration;
	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivityMainBinding.inflate(getLayoutInflater());

		setContentView(binding.getRoot());

		setSupportActionBar(binding.appBarMain.toolbar);

		DrawerLayout drawer = binding.drawerLayout;
		NavigationView navigationView = binding.navView;

		// Fixing issue where click events wouldn't be detected
		navigationView.bringToFront();

		appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_library, R.id.nav_watched, R.id.nav_watchlist)
				.setOpenableLayout(drawer)
				.build();

		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navigationView, navController);
	}

	@Override
	public boolean onSupportNavigateUp() {
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
		return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
	}
}