package com.application.progym.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.application.progym.R;
import com.application.progym.activities.menubar.Activity_About;
import com.application.progym.activities.menubar.Activity_Help;
import com.application.progym.activities.menubar.Activity_Preferences;
import com.application.progym.activities.menubar.Activity_Update;
import com.application.progym.utilities.Utilities;

/**
 * Handles the Stopwatch functionality.
 * 
 */
public class Activity_Utility_Stopwatch extends Activity{
	
	Button buttonResetLap, buttonStartStop;
	TextView textHours, textMinutes, textSeconds;
	int resetlap_buttonState, startstop_buttonState;
	int timerTime;
	
	TableLayout lapTable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utility_stopwatch); 
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);

		//Instantiate buttons locally
		buttonResetLap = (Button) findViewById(R.id.buttonResetLap);
		buttonStartStop = (Button) findViewById(R.id.buttonStartStop);
				
		//Instantiate textviews locally
		textHours = (TextView) findViewById(R.id.textHours);
		textMinutes = (TextView) findViewById(R.id.textMinutes);
		textSeconds = (TextView) findViewById(R.id.textSeconds);
		
		//Instantiate the table
		lapTable = (TableLayout)findViewById(R.id.tableLaps);
		addTableHeader(this.findViewById(R.id.tableLaps));
		
		//Initialise variables
		resetlap_buttonState = 0; //0 = Reset, 1 = Lap
		startstop_buttonState = 0; //0 = Stopped, 1 = Started
		timerTime = 0;
	}  
	
	/**
	 * Adds the header row to the laps table.
	 */
	public void addTableHeader(View view)
	{		
		TableRow headerRow = (TableRow) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablelap_header_row, null);

		lapTable.addView(headerRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

	}
	 
	/**
	 * Start/Resume countdown or Stop countdown depending on state of the button. 
	 */
	public void buttonStartStop()
	{
		startstop_buttonState++;
		
		if(startstop_buttonState > 1) //If 2 (over 0-1 range)
		{
			startstop_buttonState = 0; //Reset back to 0 (i.e. Stopped)
		}
				
		if(startstop_buttonState == 0)
		{
			stopTimer(); //If 0, stop the timer
		}		
		else
		{
			startTimer(); //If 1, start the timer
		}
	}
	
	/**
	 * Stops the timer.
	 */
	private void stopTimer()
	{
		buttonStartStop.setText("START");
	}
	
	/**
	 * Starts/resumes the timer.
	 */
	private void startTimer()
	{
		buttonStartStop.setText("STOP");
	}
	
	/**
	 * Start/Resume countdown or Stop countdown depending on state of the button. 
	 */
	public void buttonResetLap()
	{
		resetlap_buttonState++;
		
		if(resetlap_buttonState > 1) //If 2 (over 0-1 range)
		{
			resetlap_buttonState = 0; //Reset back to 0 (i.e. Stopped)
		}
				
		if(resetlap_buttonState == 0)
		{
			reset();
		}		
		else
		{
			lap(); 
		}
	}
	
	/**
	 * Resets the Timer to 0h: 00m: 00s.
	 */
	public void reset()
	{
		stopTimer();
		textHours.setText("00");
		textMinutes.setText("00");
		textSeconds.setText("00");
	}
	
	/**
	 * Adds new row to the tablelayout
	 * Populate with current lap, current time and the time since last lap.
	 */
	public void lap()
	{
		
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
