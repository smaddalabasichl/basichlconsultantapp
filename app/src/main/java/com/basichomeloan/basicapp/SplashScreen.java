package com.basichomeloan.basicapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;

public class SplashScreen extends Activity {
	public static  String CurrentValue = "";
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		SharedPreferences.Editor editor = pref.edit();
        String current = pref.getString("key","0.0");
        boolean istTime =  pref.getBoolean("istTime",true);



		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try  {

					String response = FetchApi.GETRequest();
					JSONObject jsonObject = new JSONObject(response);
					JSONArray result=	jsonObject.getJSONArray("result");

					CurrentValue = result.getJSONObject(0).get("rmAppVersion").toString();

					if(pref.getBoolean("istTime",true))
					{
						editor.putString("key",CurrentValue);
					}
					editor.putBoolean("istTime",false);
					editor.commit();


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		try {
//			thread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


		int SPLASH_TIME_OUT = 1000;
		new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if(pref.getBoolean("istTime", true)) {
					Intent i = new Intent(SplashScreen.this, MainActivity.class);
					startActivity(i);
				}
                else  if(!pref.getString("key","0.0").equals(CurrentValue))
				{
					Intent i = new Intent(SplashScreen.this, MessageActivity.class);
					startActivity(i);
				}
                else
				{
					Intent i = new Intent(SplashScreen.this, MainActivity.class);
					startActivity(i);
				}


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

}
