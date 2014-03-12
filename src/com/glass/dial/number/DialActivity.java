/*
 * Magic.java
 * @author Cody Engel
 * http://codyengel.info
 * 
 * This is the service which is started from HelloGlass.java, this is where the magic happens.
 */
package com.glass.dial.number;

import java.util.ArrayList;

import com.google.android.glass.app.Card;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class DialActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//ArrayList<String> voiceResults = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
		//String spokenText = voiceResults.get(0);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String phoneNumber = extras.getString("phone_number");
		    Log.d("test", "result");
	    	Log.d("test", phoneNumber);
	    	
       	 	Intent localIntent = new Intent();
       	 	localIntent.putExtra("com.google.glass.extra.PHONE_NUMBER", phoneNumber);
       	 	localIntent.setAction("com.google.glass.action.CALL_DIAL");
       	 	sendBroadcast(localIntent);
		} 
		 
		//We're creating a card for the interface.
		//more info: http://developer.android.com/guide/topics/ui/themes.html
//		Card card1 = new Card(this);
//		card1.setText("1111"); // Main text area
//		card1.setFootnote("222"); // Footer
//		View card1View = card1.toView();
		 
//		setContentView(card1View); // Display the card we just created
	}

}
