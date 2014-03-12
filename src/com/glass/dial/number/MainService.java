package com.glass.dial.number;
//about voice trigger with expected reply: https://developers.google.com/glass/develop/gdk/input/voice#setting_constraints

// Glass Specific
import java.util.ArrayList;
import java.util.List;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;

public class MainService extends Activity {
	private static final int RESULT_OK = 1;
	private static final int SPEECH_REQUEST = 0;
	private static final String LIVE_CARD_ID = "helloglass";
	
	// timelineManager allows applications to interact with the timeline.
	// more info: https://developers.google.com/glass/develop/gdk/reference/com/google/android/glass/timeline/TimelineManager
	private TimelineManager mTimelineManager;
	
	// LiveCard lets you create cards as well as publish them to the users timeline.
	// more info: https://developers.google.com/glass/develop/gdk/reference/com/google/android/glass/timeline/LiveCard
	@SuppressWarnings("unused")
	private LiveCard mLiveCard;
	 
	// onStartCommand is used to start a service from your voice trigger you set up in res/xml/voice_trigger_start.xml
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Where the magic happens
		mTimelineManager = TimelineManager.from(this);
		
		mLiveCard = mTimelineManager.createLiveCard(LIVE_CARD_ID);
		
		//return START_STICKY;
	}
	@Override
	public void onResume() {
		super.onResume();		
		Bundle extras = getIntent().getExtras();
	    if(extras != null) {
	    	ArrayList<String> voiceResults = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
	    	String spokenText = voiceResults.get(0);
	    	Log.d("test", spokenText);
	    	 
			Intent i = new Intent(this, DialActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putExtra("phone_number", spokenText);
			startActivity(i);
	    }
	    else
	    	Log.d("test", "t1");
	}
	
	@SuppressWarnings("unused")
	private void displaySpeechRecognizer() {
	    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	    startActivityForResult(intent, SPEECH_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	        Intent data) {
	    if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
	        List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	        String spokenText = results.get(0);
	        // Do something with spokenText.
	    }
	    Log.d("test", "tata");
	    super.onActivityResult(requestCode, resultCode, data);
	}
}
