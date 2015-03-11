package com.application.progym.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
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
 * Reference: http://sampleprogramz.com/android/chronometer.php
 * Reference: http://developer.android.com/reference/android/widget/Chronometer.html
 * 
 */
public class Activity_Utility_Stopwatch extends Activity{
	
	Button buttonReset, buttonLap, buttonStart, buttonStop;
	EditText textHours, textMinutes, textSeconds;
	int lapCounter;
	int current_hour, current_min, current_sec, previous_hour, previous_min, previous_sec;
	Chronometer stopwatch;
	
	TableLayout lapTable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utility_stopwatch); 
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);

		//Instantiate buttons 
		buttonReset = (Button) findViewById(R.id.buttonResetStopwatch);
		buttonLap = (Button) findViewById(R.id.buttonLapStopwatch);
		buttonStart = (Button) findViewById(R.id.buttonStartStopwatch);
		buttonStop = (Button) findViewById(R.id.buttonStopStopwatch);
				
		//Instantiate textviews 
		//textHours = (EditText) findViewById(R.id.textHours);
		//textMinutes = (EditText) findViewById(R.id.textMinutes);
		//textSeconds = (EditText) findViewById(R.id.textSeconds);
		
		//Instantiate the table
		lapTable = (TableLayout)findViewById(R.id.tableLaps);
		addTableHeader(this.findViewById(R.id.tableLaps));
		
		//Initialise variables
		lapCounter = 0;
		current_hour = current_min = current_sec = previous_hour = previous_min = previous_sec = 0;
		
		//Initialise stopwatch
		//stopwatch = new Chronometer(getApplicationContext());
		//stopwatch.setBase(SystemClock.elapsedRealtime());
		//stopwatch.setFormat("H:MM:SS");
		stopwatch = (Chronometer) findViewById(R.id.stopwatch);
		stopwatch.setBase(SystemClock.elapsedRealtime());
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
	 * Start timer
	 */
	public void startStopwatch(View view)
	{
		//Change visibility of buttons
		buttonStart.setVisibility(View.INVISIBLE);
		buttonReset.setVisibility(View.INVISIBLE);
		buttonStop.setVisibility(View.VISIBLE);		
		buttonLap.setVisibility(View.VISIBLE);
			
		stopwatch.start();
		
	}
	
	/**
	 * Stop timer.
	 */
	public void stopStopwatch(View view)
	{
		//Change visibility of buttons		
		buttonStop.setVisibility(View.INVISIBLE);
		buttonLap.setVisibility(View.INVISIBLE);
		buttonStart.setVisibility(View.VISIBLE);
		buttonReset.setVisibility(View.VISIBLE);	
		
		stopwatch.stop();
	}
	
	/**
	 * Reset all variables.
	 */
	public void resetStopwatch(View view)
	{		
		stopwatch.setBase(SystemClock.elapsedRealtime());

		lapTable.removeViews(1, lapTable.getChildCount()-1);

		lapCounter = 0;
		
		previous_hour = previous_min = previous_sec = 0;
	}
	
	/**
	 * Add current time to table. 
	 */
	public void lapStopwatch(View view)
	{
		Log.d("PD", "Lap: "+ stopwatch.getText().toString());
		String time = stopwatch.getText().toString();
	
		addLap(time);
	}
	
	
	/**
	 * Collect data to add new row to lap table.
	 */
	public void addLap(String time)
	{
		//Increment lapCounter
		lapCounter++;
		
		//Get Hours, Minutes and Seconds from lapped time.
		String hr = time.substring(0, 2);
		String min = time.substring(3, 5);
		String sec = time.substring(6, 8);
		
		Log.d("PD", "Hrs: " + hr + " Mins: "+ min + " Secs: " + sec);
		
		//Convert to ints
		current_hour = Integer.valueOf(hr);
		current_min = Integer.valueOf(min);
		current_sec = Integer.valueOf(sec);

		int diff_hour, diff_min, diff_sec;
		
		//Calculate difference from previous lap.
		diff_hour = current_hour - previous_hour;
		diff_min = current_min - previous_min;
		diff_sec = current_sec - previous_sec;
		
		//Stop negatives
		if(diff_sec < 0)
		{
			diff_sec = previous_sec - current_sec;
		}
		
		//Add in missing 0's.
		String shour, smin, ssec;
		
		shour = String.valueOf(diff_hour);
		smin = String.valueOf(diff_min);
		ssec = String.valueOf(diff_sec);
		
		if(shour.length() < 2)
		{
			shour = "0"+shour;
		}
		if(smin.length() < 2)
		{
			smin = "0"+smin;
		}
		if(ssec.length() < 2)
		{
			ssec = "0"+ssec;
		}
		
		//String diff_time = "0" + diff_hour + ":0" + diff_min + ":" + diff_sec;
		String diff_time = shour + ":" + smin + ":" + ssec;
		Log.d("PD", "Difference: " + diff_time);
		
		//Set previous time with current time.
		previous_hour = current_hour;
		previous_min = current_min;
		previous_sec = current_sec;
		
		addLapRow(lapCounter, time, diff_time);
	}
	
	/**
	 * Adds a new row to the Lap Table.
	 * Populate with current lap, current time and the time since last lap.
	 */
	private void addLapRow(int lap, String current_time, String difference_time)
	{
		Log.d("PD", "addLapRow() ENTRY, lap: " + lap);
		TableRow newRow = (TableRow) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablelap_row, null);
		
		TextView counter = (TextView) newRow.findViewById(R.id.textLapCounter);
		TextView curr_time = (TextView) newRow.findViewById(R.id.textCurrentCounter);
		TextView diff_time = (TextView) newRow.findViewById(R.id.textDifferenceCounter);
		
		counter.setText(String.valueOf(lap));
		curr_time.setText(current_time);
		diff_time.setText(difference_time);
		
		//Add new Row
		lapTable.addView(newRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
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
