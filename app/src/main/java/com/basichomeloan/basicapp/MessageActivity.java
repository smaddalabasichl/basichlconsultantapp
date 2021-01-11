package com.basichomeloan.basicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MessageActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		SharedPreferences.Editor editor = pref.edit();
		Button button = (Button) findViewById(R.id.button);
		button.setVisibility(View.INVISIBLE);
		Thread thread = new Thread(new Runnable() {


			@Override
			public void run() {
				deleteCache(getApplicationContext());
				editor.putString("key", SplashScreen.CurrentValue);
				editor.commit();

			}
		});

		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {

				Intent i = new Intent(MessageActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}

		}, 1000);


		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				editor.putString("key", SplashScreen.CurrentValue);
				editor.commit();
				deleteCache(getApplicationContext());
				finish();
			}
		});
	}

	public static void deleteCache(Context context) {
		try {
			File dir = context.getCacheDir();
			deleteDir(dir);
		} catch (Exception e) { e.printStackTrace();}
	}

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}
}
