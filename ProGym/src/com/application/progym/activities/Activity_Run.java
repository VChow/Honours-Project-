package com.application.progym.activities;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

import com.application.progym.R;
import com.application.progym.activities.menubar.Activity_About;
import com.application.progym.activities.menubar.Activity_Help;
import com.application.progym.activities.menubar.Activity_Preferences;
import com.application.progym.activities.menubar.Activity_Update;
import com.application.progym.managers.RunManager;
import com.application.progym.utilities.Utilities;

/**
 * Handles the Run activity. Allows the user to track their location from start
 * to finish, and calculates how far they have travelled based on their 
 * coordinates. Results are displayed in a table. 
 *
 */
public class Activity_Run extends Activity {

	//GoogleMap googleMap;
	private RunManager gps;
	private double startLatitude, startLongitude; 
	private double endLatitude, endLongitude;
	
	private Button buttonStart, buttonEnd;
	private TableLayout runTable;
	
	public Chronometer timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run); 
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);
		//googleMap = ((MapFragment)) getFragmentManager().findFragmentById(R.id.map).getMap();
		
		//Initialise the activity
		initialise(); 
		
	}
	
	/**
	 * Initialises the activity
	 */
	private void initialise()
	{
		//Initialise objects
		gps = new RunManager(this);
		timer = (Chronometer) findViewById(R.id.runTimer);
		timer.setBase(SystemClock.elapsedRealtime());
		
		//Intialise variables
		startLatitude = 0;
		startLongitude = 0;
		endLatitude = 0;
		endLongitude = 0;
		
		//Instantiate Buttons
		buttonStart = (Button) findViewById(R.id.buttonStartRun);
		buttonEnd = (Button) findViewById(R.id.buttonStopRun);
		
		//Instantiate Table
		runTable = (TableLayout) findViewById(R.id.tableRunData);
	}
	
	/**
	 * Called when user clicks the Start Run Button.
	 * Starts timer and gets current coordinates.
	 * 
	 * @param view
	 */
	public void startRun(View view) 
	{					
		//Show/Hide buttons
		buttonStart.setVisibility(View.INVISIBLE);
		buttonEnd.setVisibility(View.VISIBLE);
		
		//Reset coordinates
		resetTracker();
		
		//Reset and Start timer
		timer.setBase(SystemClock.elapsedRealtime());
		timer.start();
		
		if(gps.canGetLocation())
		{
			startLatitude = gps.getLatitude(); 
			startLongitude = gps.getLongitude(); 
			
			Toast.makeText(getApplicationContext(), "Latitude: " + startLatitude + "\nLongitude: " + startLongitude, Toast.LENGTH_LONG).show();
			Log.d("PD", "Starting Latitude: " + startLatitude);
			Log.d("PD", "Starting Longitude: " + startLongitude);
		}
		else
		{
            gps.showSettingsAlert();
		}
	}
	
	/**
	 * Called when user clicks the End Run Button.
	 * Stops the timer and calculates statistics for the run duration.
	 * 
	 * @param view
	 */
	public void stopRun(View view)
	{
		//Show/Hide buttons
		buttonStart.setVisibility(View.VISIBLE);
		buttonEnd.setVisibility(View.INVISIBLE); 
		
		//End timer
		timer.stop();
		
		if(gps.canGetLocation())
		{
			endLatitude = gps.getLatitude(); 
			endLongitude = gps.getLongitude(); 
			
			Toast.makeText(getApplicationContext(), "Latitude: " + endLatitude + "\nLongitude: " + endLongitude, Toast.LENGTH_LONG).show();
			Log.d("PD", "Ending Latitude: " + endLatitude);
			Log.d("PD", "Ending Longitude: " + endLongitude);
			
			calculateStats();
		}
		else
		{
            gps.showSettingsAlert();
		}
	} 
	
	/**
	 * Calculates stats based on the readings from the Run Manager and Run Activity.
	 */
	private void calculateStats()
	{
		double distanceTravelled = 0;
		String timeTaken = "";
		double averageSpeed = 0;
		
		//Get Distance Travelled.
		distanceTravelled = getDistanceTravelled();
		
		//Get Time Taken.
		timeTaken = getTimeTaken();
		
		//Get Average Speed.
		averageSpeed = getAverageSpeed(distanceTravelled);
		
		//Add results to the table.
		addToTable(distanceTravelled, timeTaken, averageSpeed);
	}
	
	/**
	 * Calculates the average speed for the run.
	 * Divide distance by time taken.
	 * Distance is in meters.
	 * Time is a String, so extract (Hours, Minutes and Seconds) * 1000 milliseconds.
	 * 
	 * @return averageSpeed The average speed of the run.
	 */
	private double getAverageSpeed(double distance)
	{
		double averageSpeed = 0;	
		int hours, minutes, seconds;
		String time = "";
		long timeInMilliseconds = 0;
		long timeInSeconds = 0;
		
		time = timer.getText().toString();
		
		//Get Hours, Minutes and Seconds from lapped time.
		String hr = time.substring(0, 2);
		String min = time.substring(3, 5);
		String sec = time.substring(6, 8);
		
		//Convert to ints
		hours = Integer.valueOf(hr);
		minutes = Integer.valueOf(min);
		seconds = Integer.valueOf(sec);
		
		//Get the total time in milliseconds
		timeInMilliseconds = (hours * 3600000) + (minutes * 60000) + (seconds * 1000);
		
		//Get the total time in seconds
		timeInSeconds = timeInMilliseconds / 1000;
		
		//Calculate averageSpeed in m/s
		averageSpeed = distance / timeInSeconds;
		
		Log.d("PD", "Average Speed: " + averageSpeed);
		
		return averageSpeed;
	}
	
	/**
	 * Calculates the time taken for the run.
	 * 
	 * @return timeTaken The time taken for the run.
	 */
	private String getTimeTaken()
	{
		String timeTaken = "";
		String time = "";
		
		time = timer.getText().toString();
		Log.d("PD", "time: " + time);
		
		//Get Hours, Minutes and Seconds from time.
		String hr = time.substring(0, 2);
		String min = time.substring(3, 5);
		String sec = time.substring(6, 8);
		
		timeTaken = "" + hr + ":" + min + ":" + sec;
		
		Log.d("PD", "Time Taken: " + timeTaken);
		
		return timeTaken;
	}
	
	/**
	 * Calculates the distance travelled between two coordinates.
	 * Uses the built-in function in the Location class to determine distance.
	 * 
	 * @return distanceTravelled the distance between two points in meters.
	 */
	private double getDistanceTravelled()
	{
		double distanceTravelled = 0;
		
		//Define start location
		Location startLocation = new Location("Start");
		startLocation.setLatitude(startLatitude);
		startLocation.setLongitude(startLongitude);
		
		//Define end location
		Location endLocation = new Location("End");
		endLocation.setLatitude(endLatitude);
		endLocation.setLongitude(endLongitude);
		
		//Calculate distance travelled in meters
		distanceTravelled = startLocation.distanceTo(endLocation);
		
		Log.d("PD", "getDistanceTravelled: " + distanceTravelled + "m");
		
		return distanceTravelled;
	}
	
	
	/**
	 * Adds results of the run to the table.
	 */
	private void addToTable(double distanceTravelled, String timeTaken, double averageSpeed)
	{
		createTableHeader();
		createTableRow(distanceTravelled, timeTaken, averageSpeed);
	}
	
	
	/**
	 * Create and display the Header row for the table.
	 */ 
	private void createTableHeader()
	{
		TableRow headerRow = (TableRow) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablerun_header_row, null);
	
		runTable.addView(headerRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	} 
	
	/**
	 * Create and display the data row for the table.
	 */
	private void createTableRow(double distanceTravelled, String timeTaken, double averageSpeed)
	{
		TableRow tableRow = (TableRow) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablerun_row, null);
	
		TextView distance = (TextView) tableRow.findViewById(R.id.textRunDistance);
		TextView speed = (TextView) tableRow.findViewById(R.id.textRunSpeed);
		TextView time = (TextView) tableRow.findViewById(R.id.textRunTime);
		
		distance.setText("" + distanceTravelled);
		speed.setText(""+averageSpeed);
		time.setText(""+timeTaken);
		
		runTable.addView(tableRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}
	
	/**
	 * Resets Latitudes and Longitudes for a new run instance.
	 */
	private void resetTracker()
	{
		startLatitude = 0;
		startLongitude = 0;
		endLatitude = 0;
		endLongitude = 0;
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
