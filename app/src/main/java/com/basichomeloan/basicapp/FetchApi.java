package com.basichomeloan.basicapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchApi {
  static String releaseUrl = "https://utservice.basichomeloan.com/api/v1/AppVersion";
  static String devUrl = "https://dev-utservice.basichomeloan.com/api/v1/AppVersion";

   public static String GETRequest() throws IOException {
		String finlResponse = "";
		URL urlForGetRequest = new URL(releaseUrl);
		String readLine = null;
		try {
			HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
			conection.setRequestMethod("GET");
			//	conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
			int responseCode = conection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(
						new InputStreamReader(conection.getInputStream()));
				StringBuffer response = new StringBuffer();
				while ((readLine = in.readLine()) != null) {
					response.append(readLine);
				}
				in.close();
				// print result
				System.out.println("JSON String Result " + response.toString());
				finlResponse = response.toString();
				//GetAndPost.POSTRequest(response.toString());
			} else {
				System.out.println("GET NOT WORKED");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return finlResponse;
	}
}
