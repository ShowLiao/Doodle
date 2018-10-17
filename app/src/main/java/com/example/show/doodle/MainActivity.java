package com.example.show.doodle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

	GraffitiView graffitView;
	Button btn;
	Button btnRedo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		graffitView = new GraffitiView(this);

		FrameLayout layout = findViewById(R.id.frameLayout);
		layout.addView(graffitView);

		btn = findViewById(R.id.btnUndo);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				graffitView.undo();
			}
		});

		btnRedo = findViewById(R.id.btnRedo);
		btnRedo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				graffitView.redo();
			}
		});
	}

}
