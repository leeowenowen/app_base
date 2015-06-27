package com.msjf.fentuan.movie;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.owo.app.base.ConfigurableActivity;
import com.owo.base.pattern.Singleton;

public class MovieActivity extends ConfigurableActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		String movie_id = getIntent().getExtras().getString("movie");
		Movie movie = Singleton.of(MovieData.class).get(movie_id);
		MoviePage page = new MoviePage(this);
		page.setTitle(movie.mName);
		page.setData(movie);

		setContentView(page);
	}

}
