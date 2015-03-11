package com.application.progym.activities;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.application.progym.R;
import com.application.progym.activities.menubar.Activity_About;
import com.application.progym.activities.menubar.Activity_Help;
import com.application.progym.activities.menubar.Activity_Preferences;
import com.application.progym.activities.menubar.Activity_Update;
import com.application.progym.utilities.Utilities;
 
/**
 * Displays the Advice page:
 * Random facts?
 */
public class Activity_Advice extends Activity{
	
	TextView textAdvice;
	final int ADVICE_TOTAL = 19;
	Random rand;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advice); 
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);

		//Instantiate textview.
		textAdvice = (TextView) findViewById(R.id.textAdvice);
		
		//Initialise variables.
		rand = new Random();
		
		//generateAdvice(getCurrentFocus());
	}  
	
	public void generateAdvice(View view)
	{
		String string_name = "advice_";
		
		//Generate a random number to append to string_name
		int rng = rand.nextInt(ADVICE_TOTAL);
		Log.d("PD", "rng: " + rng);
		
		//Append random number to string_name to get a string resource name
		string_name = string_name + rng;
		Log.d("PD", "string_name: " + string_name);
		
		//Obtain resource id value of string_name
		int string_id = Activity_Advice.this.getResources().getIdentifier(string_name, "string", Activity_Advice.this.getPackageName());
		Log.d("PD", "string_id: " + string_id);
		
		//Obtain string value of resource id
		String toDisplay = getResources().getString(string_id);
		Log.d("PD", "toDisplay: " + toDisplay);
		
		//Set text
		textAdvice.setText(toDisplay);
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
