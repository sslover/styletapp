package com.samslover.styletappjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.gsontest.R;
// This application uses the very nice gson, JSON parsing library from Google
// https://sites.google.com/site/gson/
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddClothing extends Activity  {
	
	//add clothing button
	Button addClothingButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_clothing);
		// this is assuming that the photo is already taken and on file
		// Need to dynamically set the ImageView to the photo
		ImageView imageView = (ImageView) this.findViewById(R.id.imageView1);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// on click of button, execute addClothing
		addClothingButton = (Button) this.findViewById(R.id.ButtonAddClothing);
		addClothingButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	PostJSON post = new PostJSON();
		    	post.addClothing(v);
		    }
		});
	}
	
	// asynch task is not working. see http://developer.android.com/reference/android/os/AsyncTask.html
	class PostJSON extends AsyncTask<String, Void, String> { 

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
	private void addClothing (View v){
		Log.v("AddClothing", "we got here");
		if (v == addClothingButton){
			final EditText captionField = (EditText) findViewById(R.id.EditCaption);  
			String caption = captionField.getText().toString(); 
			final EditText nameField = (EditText) findViewById(R.id.EditName);  
			String name = nameField.getText().toString();  
			final EditText brandField = (EditText) findViewById(R.id.EditBrand);  
			String brand = brandField.getText().toString();  
			final Spinner clothingSpinner = (Spinner) findViewById(R.id.SpinnerClothingType);  
			String clothingType = clothingSpinner.getSelectedItem().toString();   
			// we then call execute, which posts to database with above parameters
			// we need to add in the dynamic photo; it is still a hard-coded URL
			String photo = "http://www.theminerswife.com/wp-content/uploads/2012/02/iceburg.jpg";
			execute(name, clothingType, caption, brand, photo);
			// finishes and we need to define where it goes next
			finish();
		}	
	}
	
	public void execute(String name, String type, String caption, String brand, String photo) {
	    // creates a JSON object with the below
		Map<String, String> clothing = new HashMap<String, String>();
	    clothing.put("name", name);
	    clothing.put("type", type);
	    clothing.put("caption", caption);
	    clothing.put("brand", brand);
	    clothing.put("photo", photo);
	    String json = new GsonBuilder().create().toJson(clothing, Map.class);
        Log.v("MainActivity", json);
	    makeRequest("http://styletapp.herokuapp.com/android", json);
	}

	public HttpResponse makeRequest(String uri, String json) {
		try {
	        HttpPost httpPost = new HttpPost(uri);
	        httpPost.setEntity(new StringEntity(json));
	        httpPost.setHeader("Accept", "application/json");
	        httpPost.setHeader("Content-type", "application/json");
	        return new DefaultHttpClient().execute(httpPost);
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
  }	
}

