package com.application.progym.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;

import com.application.progym.R;
import com.application.progym.activities.menubar.Activity_About;
import com.application.progym.activities.menubar.Activity_Help;
import com.application.progym.activities.menubar.Activity_Preferences;
import com.application.progym.activities.menubar.Activity_Update;
import com.application.progym.utilities.Utilities;

/**
 * Handles the Heartbeat Counter functionality.
 * 
 */
public class Activity_Utility_HeartbeatCounter extends Activity{
	
	Button buttonRecord;
	TextView textHeartRate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utility_heartbeatcounter); 
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);

		//Instantiate button
		buttonRecord = (Button) findViewById(R.id.buttonStartRecord);
		
		//Instantiate textview
		textHeartRate = (TextView) findViewById(R.id.textHeartRate);

	}  
	
	/**
	 * Display instructions to user and record BPM.
	 */
	public void buttonStartRecord(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Place your thumb over the Heartbeat Sensor and do not remove until it is finished.")
			   .setTitle("Heartbeat Counter")
			   .setPositiveButton("Ok", new DialogInterface.OnClickListener()
			   {
				   public void onClick(DialogInterface dialog, int which) {
					   //record();
				   }
			   })
			   .setIcon(android.R.drawable.ic_dialog_alert)
			   .show();
	}
	
	/**
	 * Opens the Heartbeat sensor and records user's BPM
	 */
	private void record()
	{
		//TODO
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
