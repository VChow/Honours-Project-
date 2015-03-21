package com.application.progym.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.application.progym.R;
import com.application.progym.activities.menubar.Activity_About;
import com.application.progym.activities.menubar.Activity_Help;
import com.application.progym.activities.menubar.Activity_Preferences;
import com.application.progym.activities.menubar.Activity_Update;
import com.application.progym.utilities.Utilities;

/**
 * Handles the Pedometer functionality.
 * Reference: http://blog.bawa.com/2013/11/create-your-own-simple-pedometer.html
 * Reference: https://github.com/theelfismike/android-step-counter/blob/master/src/com/starboardland/pedometer/CounterActivity.java
 * Reference: https://jayeshcp.wordpress.com/2013/05/01/how-to-use-accelerometer-sensor-in-android/
 */
public class Activity_Utility_Pedometer extends Activity implements SensorEventListener{
	 
	Button buttonStart, buttonStop;
	TextView textCounter;
	
	private boolean isRunning;
	private boolean mInitialized; // used for initializing sensor only once	 
	private SensorManager mSensorManager;	 
	private Sensor mAccelerometer;
	
	private final float NOISE = (float) 2.0;
	
	private int stepsCount = 0;
	private double mLastX = 0;
	private double mLastY = 0;
	private double mLastZ = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utility_pedometer);
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);
		
		//Instantiate buttons
		buttonStart = (Button) findViewById(R.id.buttonStartPedometer);
		buttonStop = (Button) findViewById(R.id.buttonStopPedometer);
				
		//Instantiate textviews
		textCounter = (TextView) findViewById(R.id.textPedometerCounter);
	
		// Initialize Accelerometer sensor
		 mInitialized = false;
		 mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		 mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		 startSensor();
		
		isRunning = false; 
		 
	}  
	
	/**
	 * Registers and starts the Accelerometer listener.
	 */
	private void startSensor() 
	{
		mSensorManager.registerListener(this, mAccelerometer,
		SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	/**
	 * Interface method.
	 * Handles an event each time the acclerometer registers a change.
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {

		//Check is user has started the pedometer
		if(isRunning)
		{
			// event object contains values of acceleration, read those
			double x = event.values[0];
			double y = event.values[1];
			double z = event.values[2];

			final double alpha = 0.8; // constant for our filter below

			double[] gravity = {0,0,0};

			// Isolate the force of gravity with the low-pass filter.
			gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
			gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
			gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

			// Remove the gravity contribution with the high-pass filter.
			x = event.values[0] - gravity[0];
			y = event.values[1] - gravity[1];
			z = event.values[2] - gravity[2];

			if (!mInitialized) {
				// sensor is used for the first time, initialize the last read values
				mLastX = x;
				mLastY = y;
				mLastZ = z;
				mInitialized = true;
			} else {
				// sensor is already initialized, and we have previously read values.
				// take difference of past and current values and decide which
				// axis acceleration was detected by comparing values

				double deltaX = Math.abs(mLastX - x);
				double deltaY = Math.abs(mLastY - y);
				double deltaZ = Math.abs(mLastZ - z);
				if (deltaX < NOISE)
					deltaX = (float) 0.0;
				if (deltaY < NOISE)
					deltaY = (float) 0.0;
				if (deltaZ < NOISE)
					deltaZ = (float) 0.0;
				mLastX = x;
				mLastY = y;
				mLastZ = z;

				if (deltaX > deltaY) {
					// Horizontal shake
					// do something here if you like

				} else if (deltaY > deltaX) {
					// Vertical shake
					// do something here if you like

				} else if ((deltaZ > deltaX) && (deltaZ > deltaY)) {
					// Z shake
					stepsCount = stepsCount + 1;
					if (stepsCount > 0) {
						textCounter.setText(String.valueOf(stepsCount));
					}
				}
			}
		}
	}

	/**
	 * Interface method.
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	 
	/**
	 * Called when Start button is clicked.
	 * Starts the Pedometer, hides Start and shows Stop button.
	 * 
	 * @param view
	 */
	public void startPedometer(View view)
	{
		buttonStart.setVisibility(View.INVISIBLE);
		buttonStop.setVisibility(View.VISIBLE);
		
		isRunning = true;
		
		Toast.makeText(this, "Pedometer Started", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Called when Stop button is clicked.
	 * Stops the Pedometer, hides Stop and shows Start button.
	 * 
	 * @param view
	 */
	public void stopPedometer(View view)
	{
		buttonStart.setVisibility(View.VISIBLE);
		buttonStop.setVisibility(View.INVISIBLE);
		
		isRunning = false;
		
		Toast.makeText(this, "Pedometer Stopped", Toast.LENGTH_LONG).show();
	}

	/**
	 * Called when Reset button is clicked.
	 * Resets the step counter and display counter back to 0.
	 * @param view
	 */
	public void resetPedometer(View view)
	{
		stepsCount = 0;
		textCounter.setText(String.valueOf(stepsCount));
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
