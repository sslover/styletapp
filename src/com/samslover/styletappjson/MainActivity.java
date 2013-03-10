package com.samslover.styletappjson;

import com.example.gsontest.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button myButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		myButton = (Button) this.findViewById(R.id.button1);
		myButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        buttonClickedView(v);
		    }
		});
	}

	private void buttonClickedView (View v){
		if (v == myButton){
			Intent i = new Intent(this,AddClothing.class);
			startActivity(i); 
		}	
	}
}
