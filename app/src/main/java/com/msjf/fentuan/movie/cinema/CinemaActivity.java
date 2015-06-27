package com.msjf.fentuan.movie.cinema;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.msjf.fentuan.movie.BasePage;
import com.msjf.fentuan.movie.Movie;
import com.msjf.fentuan.movie.MovieData;
import com.owo.app.base.ConfigurableActivity;
import com.owo.base.pattern.Singleton;

public class CinemaActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		String movie_id = getIntent().getExtras().getString("movie_id");
		Movie movie = Singleton.of(MovieData.class).get(movie_id);
		BasePage page = new BasePage(this, false);
		page.setTitle(movie.mName);
		CinemaWidget widget = new CinemaWidget(this);
		page.setContentView(widget);
		setContentView(page);
	}

}
