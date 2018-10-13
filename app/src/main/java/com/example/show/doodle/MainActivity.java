package com.example.show.doodle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	GraffitiView graffitView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		graffitView = new GraffitiView(this);
		setContentView(graffitView);

	}
}
