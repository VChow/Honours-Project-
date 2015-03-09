package com.application.progym.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.application.progym.R;
import com.application.progym.activities.menubar.Activity_About;
import com.application.progym.activities.menubar.Activity_Help;
import com.application.progym.activities.menubar.Activity_Preferences;
import com.application.progym.activities.menubar.Activity_Update;
import com.application.progym.utilities.Utilities;

/**
 * Handles the Timer functionality.
 * 
 */
public class Activity_Utility_Timer extends Activity{
	
	Button buttonReset, buttonStart, buttonStop;
	EditText textHours, textMinutes, textSeconds;
	TextView textTestTimer;
	boolean isStopped;
	int timerTime;
	long timerTimeLeft;
	
	long hours, minutes, seconds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utility_timer);
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);

		//Instantiate buttons 
		buttonReset = (Button) findViewById(R.id.buttonResetTimer);
		buttonStart = (Button) findViewById(R.id.buttonStartTimer);
		buttonStop = (Button) findViewById(R.id.buttonStopTimer);
		
		//Instantiate textviews 
		textHours = (EditText) findViewById(R.id.textHours);
		textMinutes = (EditText) findViewById(R.id.textMinutes);
		textSeconds = (EditText) findViewById(R.id.textSeconds);
		textTestTimer = (TextView) findViewById(R.id.textTestTimer);
		
		//Instantiate variables
		isStopped = true;
		timerTimeLeft = getTimeLeft();
		timerTime = 0;
		
		//Time Digits
		hours = Long.valueOf(textHours.getText().toString());
		minutes = Long.valueOf(textMinutes.getText().toString());
		seconds = Long.valueOf(textSeconds.getText().toString());
	}  
	
	/**
	 * Resets the Timer to 0h: 00m: 00s.
	 */
	public void resetTimer(View view)
	{
		textHours.setText("00");
		textMinutes.setText("00");
		textSeconds.setText("00");
	}
	
	/**
	 * Starts/resumes the timer.
	 * Referenced from: http://developer.android.com/reference/android/os/CountDownTimer.html
	 */
	public void startTimer(View view)
	{	
		//Get timeLeft
		long countDown = getTimeLeft();
		
		//Long timeLeft, Long countdownInterval
		//new CountDownTimer(10000, 1000) {
		new CountDownTimer(countDown, 1000) {

		     public void onTick(long millisUntilFinished) {
		         textTestTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
		         
		         
		     }

		     public void onFinish() {
		    	 textTestTimer.setText("done!");
		    	
		    	 	//Play default notification sound
					MediaPlayer player = MediaPlayer.create(getApplicationContext(),
					Settings.System.DEFAULT_ALARM_ALERT_URI);
					player.start();
		     }
		  }.start();
	}
	
	/**
	 * Stops the timer.
	 */
	public void stopTimer(View view)
	{
		
	}
	
	/**
	 * Gets the current time left from the text views.
	 * @return timeLeft The time left in milliseconds.
	 */
	private long getTimeLeft()
	{
		//Initialise variables.
		long timeLeft = 0;
		int mhours, mminutes, mseconds = 0;
		long hours, minutes, seconds = 0;
		
		//Get the values entered by the user.
		mhours = Integer.valueOf(textHours.getText().toString());
		mminutes = Integer.valueOf(textMinutes.getText().toString());
		mseconds = Integer.valueOf(textSeconds.getText().toString());
		
		//Multiply each value by appropriate milliseconds.
		hours = mhours * 3600000; 	//1 hour = 3,600,000 milliseconds.
		minutes = mminutes * 60000; //1 minute = 60,000 milliseconds.
		seconds = mseconds * 1000; 	//1 second = 1000 milliseconds.	
		
		//Add all the milliseconds together.
		timeLeft = hours + minutes + seconds;
		
		return timeLeft;
	}
	
	/**
	 * Gets the hour displayed on Hours EditText.
	 * @return
	 */
	private int getHours()
	{
		int hours = Integer.valueOf(textHours.getText().toString());
		
		return hours;
	}
	
	private int getMinutes()
	{
		int minutes = Integer.valueOf(textMinutes.getText().toString());
		
		return minutes;
	}
	
	private int getSeconds()
	{
		int seconds = Integer.valueOf(textSeconds.getText().toString());
		
		return seconds;
	}
	
	/**
	 * Creates the Menu Bar.
	 * 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Event Handlers for Menu Bar.
	 * 
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{		
		switch (item.getItemId())
		{
		case (R.id.action_home):
			finish();
			this.startActivity(new Intent(this, Activity_Home.class));
			return true;
		case (R.id.action_help):
			this.startActivity(new Intent(this, Activity_Help.class));
			return true;
		case (R.id.action_about):
			this.startActivity(new Intent(this, Activity_About.class));
			return true;
		case (R.id.action_preferences):
			this.startActivity(new Intent(this, Activity_Preferences.class));
			return true;
		case (R.id.action_update):
			this.startActivity(new Intent(this, Activity_Update.class));
			return true;
		case (R.id.action_exit):
			Intent intent = new Intent(Intent.ACTION_MAIN); 
	    	intent.addCategory(Intent.CATEGORY_HOME);
	    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    	startActivity(intent);
	        return true;
		default:
	        return super.onOptionsItemSelected(item);
		}
	}
}
