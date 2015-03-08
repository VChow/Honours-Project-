package com.application.progym.activities.menubar;

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
import android.widget.Toast;

import com.application.progym.R;
import com.application.progym.activities.Activity_Home;
import com.application.progym.utilities.Utilities;

public class Activity_Preferences extends Activity{

	Button buttonClearData, buttonResetPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);
		
		//Instantiate buttons
		buttonClearData = (Button) findViewById(R.id.buttonClearData);
		buttonResetPreferences = (Button) findViewById(R.id.buttonResetPreferences);
	}
	
	/**
	 * Called when buttonClearData is clicked.
	 * @param view
	 */
	public void clearData(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you wish to clear all stored data on the device? This cannot be undone.")
			   .setTitle("Clear Data")
			   .setPositiveButton("Yes", new DialogInterface.OnClickListener()
			   {
				   public void onClick(DialogInterface dialog, int which) {
					   Toast.makeText(getApplicationContext(), "Data cleared", Toast.LENGTH_LONG).show();
				   }
			   })
			   .setNegativeButton("No", new DialogInterface.OnClickListener()
			   {
				   public void onClick(DialogInterface dialog, int which) {
					   //Do nothing.
				   }
			   })
			   .setIcon(android.R.drawable.ic_dialog_alert)
			   .show();
	}
	
	/**
	 * Called when buttonResetPreferences is clicked.
	 * @param view
	 */
	public void resetPreferences(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you wish to reset back to default preferences?")
			   .setTitle("Reset Preferences")
			   .setPositiveButton("Yes", new DialogInterface.OnClickListener()
			   {
				   public void onClick(DialogInterface dialog, int which) {
					   Toast.makeText(getApplicationContext(), "Preferences reset.", Toast.LENGTH_LONG).show();
				   }
			   })
			   .setNegativeButton("No", new DialogInterface.OnClickListener()
			   {
				   public void onClick(DialogInterface dialog, int which) {
					   //Do nothing.
				   }
			   })
			   .setIcon(android.R.drawable.ic_dialog_alert)
			   .show();
	}
	
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
