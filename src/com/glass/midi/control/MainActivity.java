package com.glass.midi.control;
//about voice trigger with expected reply: https://developers.google.com/glass/develop/gdk/input/voice#setting_constraints

// Glass Specific
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
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

public class MainActivity extends Activity {
	private static final String LIVE_CARD_ID = "MidiStudio";
	
	// timelineManager allows applications to interact with the timeline.
	// more info: https://developers.google.com/glass/develop/gdk/reference/com/google/android/glass/timeline/TimelineManager
	private TimelineManager mTimelineManager;
	
	// LiveCard lets you create cards as well as publish them to the users timeline.
	// more info: https://developers.google.com/glass/develop/gdk/reference/com/google/android/glass/timeline/LiveCard
	@SuppressWarnings("unused")
	private LiveCard mLiveCard;
    private String serverIpAddress = "10.0.1.26";  
    private boolean connected = false; 
    
	// onStartCommand is used to start a service from your voice trigger you set up in res/xml/voice_trigger_start.xml
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Where the magic happens
		mTimelineManager = TimelineManager.from(this);
		mLiveCard = mTimelineManager.createLiveCard(LIVE_CARD_ID);
		//return START_STICKY;
		
		Thread cThread = new Thread(new ClientThread());  
        cThread.start(); 
	}
	//*** MAKE SURE BLUETOOTH OFF ON PHONE SO THAT GLASS CAN CONNECT WITH PC! ***
    public class ClientThread implements Runnable {  
        public void run() {  
            try {  
                InetAddress serverAddr = InetAddress.getByName(serverIpAddress);  
                Log.d("ClientActivity", "C: Connecting...");  
                Socket socket = new Socket(serverAddr, 11000);  
                connected = true;  
                while (connected) {  
                    try {  
                        Log.d("ClientActivity", "C: Sending command.");  
                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);  
                        // WHERE YOU ISSUE THE COMMANDS  
                        out.println("1|3|5|7<EOF>");  
                        Log.d("ClientActivity", "C: Sent.");  
                    } catch (Exception e) {  
                        Log.e("ClientActivity", "S: Error -" + e.getMessage() + "...", e);   
                    }  
                }  
                socket.close();  
                Log.d("ClientActivity", "C: Closed.");  
            } catch (Exception e) {  
                Log.e("ClientActivity", "C: Error" + e.getMessage() + "...", e);   
                connected = false;  
            }  
        }  
    }  
    
	@Override
	public void onResume() {
		super.onResume();		
    	Log.d("test", "t1");
	}
}
