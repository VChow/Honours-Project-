package com.application.progym.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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
		isStopped = false;
		timerTime = 0;
		
	}  
	
	/**
	 * Resets the Timer to 0h: 00m: 00s.
	 */
	public void resetTimer(View view)
	{
		//Release all Countdown Timers by restarting the activity.
		finish();
		startActivity(getIntent());
	}
	
	/**
	 * Starts/resumes the timer.
	 * Referenced from: http://developer.android.com/reference/android/os/CountDownTimer.html
	 */
	public void startTimer(View view)
	{	
		//Initialise variables
		isStopped = false;
		timerTimeLeft = getHours() + getMinutes() + getSeconds();
		
		//Start the timers
		startHoursTimer(getHours());
		startMinutesTimer(getMinutes());
		startSecondsTimer(getSeconds());
		
		//Change visibility of buttons
		buttonStart.setVisibility(View.INVISIBLE);
		buttonStop.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Starts a new instance of CountDownTimer for the Hours remaining.
	 * @param hrs The hours remaining in milliseconds.
	 */
	private void startHoursTimer(long hrs)
	{
		new CountDownTimer(hrs, 3600000) {

		     public void onTick(long millisUntilFinished) {
		    	 
		    	 //Check if user has paused the timer
		    	 if(isStopped == true)
		    	 {
		    		 cancel();
		    	 }
		    	 else
		    	 {
		    		 String text = "" + millisUntilFinished / 3600000;

		    		 if(text.length() < 2)
		    		 {
		    			 text = "0" + text;
		    		 }

		    		 textHours.setText(text);
		    	 }
		     }
		     
		     public void onFinish() {}
		  }.start();
	}
	
	/**
	 * Starts a new instance of CountDownTimer for the Minutes remaining.
	 * @param mins The minutes remaining in milliseconds.
	 */
	private void startMinutesTimer(long mins)
	{
		new CountDownTimer(mins, 60000) {

			public void onTick(long millisUntilFinished) {

				//Check if user has paused the timer
				if(isStopped == true)
				{
					cancel();
				}
				else
				{

					String text = "" + millisUntilFinished / 60000;

					if(text.length() < 2)
					{
						text = "0" + text;
					}

					textMinutes.setText(text);

					if((timerTimeLeft / 1000) < 60) //if time left is less than 60 secs
					{
						textMinutes.setText("00");
					}
				}
			}
			
			public void onFinish() {}
		}.start();

	}
	
	/**
	 * Starts a new instance of CountDownTimer for the Seconds remaining.
	 * @param secs The seconds remaining in milliseconds.
	 */
	private void startSecondsTimer(long secs)
	{
		Log.d("PD", "startSecondsTimer() timerTimeLeft: " + timerTimeLeft);
		
		new CountDownTimer(secs, 1000) {

		     public void onTick(long millisUntilFinished) {
		    	 
		    	//Check if user has paused the timer
		    	 if(isStopped == true)
		    	 {
		    		 cancel();
		    	 }
		    	 else
		    	 {
		    		 String text = "" + millisUntilFinished / 1000;

		    		 //Decrement total time left by 1 second.
		    		 timerTimeLeft = timerTimeLeft - 1000;
		    		 
		    		 //Print out time left for debugging
		    		 textTestTimer.setText("Seconds left: " + timerTimeLeft / 1000);

		    		 if(text.length() < 2)
		    		 {
		    			 text = "0" + text;
		    		 }

		    		 textSeconds.setText(text);

		    		 //Timer doesn't drop to 0, so set it manually.
		    		 if(text.equals("01"))
		    		 {
		    			 textSeconds.setText("00");
		    		 }

		    		 //Check if Hours and Minutes need to be decremented.
		    		 long timeLeft = timerTimeLeft / 1000; //Convert into seconds, easier to work with.
		    		 if(timeLeft < 3600) //if time left is under an hour, set text to 0
		    		 {
		    			 textHours.setText("00");
		    			 textMinutes.setText("" + (timeLeft / 60));
		    		 }
		    	 }
		     }

		     public void onFinish()
		     {
		    	 long timeLeft = timerTimeLeft / 1000; //Convert into seconds, easier to work with.
		    	 
		    	 if(timeLeft < 3600) //if time left is under an hour, set text to 0
	    		 {
	    			 textHours.setText("00");
	    			 textMinutes.setText("" + (timeLeft / 60));
	    		 }
		    	 
		    	 if ((timeLeft) > 0 && (timeLeft) > 60) //if time left is over a minute, start 60 sec timer again
		    	 {
		    		 startSecondsTimer(60000);		    		 
		    	 }
		    	 else if((timeLeft) > 0 && (timeLeft) < 60) //if time left has few seconds left, set appropriate timer
		    	 {
		    		 startSecondsTimer(timeLeft * 1000);
		    		 textHours.setText("00");
		    		 textMinutes.setText("00");
		    	 }
		    	 else //if 0 or less, play alarm.
		    	 {
		    		//Play default notification sound
					MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
					player.start();
		    	 }
		     }
		  }.start();
	}
	
	/**
	 * Stops the timer.
	 */
	public void stopTimer(View view)
	{
		buttonStart.setVisibility(View.VISIBLE);
		buttonStop.setVisibility(View.INVISIBLE);
		
		isStopped = true;
	}
	
	/**
	 * Gets the value displayed on Hours EditText.
	 * @return long mhours The number of hours.
	 */
	private long getHours()
	{
		long mhours = Integer.valueOf(textHours.getText().toString()) * 3600000;
		
		return mhours;
	}
	
	/**
	 * Gets the value displayed on Minutes EditText.
	 * @return long mminutes The number of number of minutes.
	 */
	private long getMinutes()
	{
		long mminutes = Integer.valueOf(textMinutes.getText().toString()) * 60000;
		
		return mminutes;
	}
	
	/**
	 * Gets the value displayed on Seconds EditText.
	 * @return long mseconds The number of seconds.
	 */
	private long getSeconds()
	{
		long mseconds = Integer.valueOf(textSeconds.getText().toString()) * 1000;
		
		return mseconds;
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
