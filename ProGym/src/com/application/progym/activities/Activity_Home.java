package com.application.progym.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.application.progym.R;
import com.application.progym.activities.menubar.Activity_About;
import com.application.progym.activities.menubar.Activity_Help;
import com.application.progym.activities.menubar.Activity_Preferences;
import com.application.progym.activities.menubar.Activity_Update;
import com.application.progym.adapters.ImageAdapterMain;
import com.application.progym.utilities.Utilities;

public class Activity_Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);
		
		//Create Home Screen Selection Menu
		createCategories();
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

	/**
	 * Populates the GridView with selectables defined in the ImageAdapterMain class.
	 */
	private void createCategories()
	{
		//Get the reference of ListViewCategories
		final GridView categoriesGrid=(GridView)findViewById(R.id.gridview_main);
		categoriesGrid.setAdapter(new ImageAdapterMain(this));

		//Register onClickListener to handle click events on each item.
		categoriesGrid.setOnItemClickListener(new OnItemClickListener()
		{
			//Argument position gives the index of item which is clicked.
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
			{
				if(position == 0) //Workouts
				{
					Intent intentWorkouts = new Intent(getApplicationContext(),Activity_Workouts.class);
					startActivity(intentWorkouts);
				}
				else if(position == 1) //Utilities
				{
					Intent intentUtilities = new Intent(getApplicationContext(),Activity_Utilities.class);
					startActivity(intentUtilities);
				}
				else if(position == 2) //Calorie Counter
				{
					Intent intentCalorie = new Intent(getApplicationContext(),Activity_CalorieTracker.class);
					startActivity(intentCalorie);
					
				}
				else if(position == 3) //Run
				{
					Intent intentRun = new Intent(getApplicationContext(),Activity_Run.class);
					startActivity(intentRun);
				}
				else if(position == 4) //Advice
				{
					Intent intentAdvice = new Intent(getApplicationContext(),Activity_Advice.class);
					startActivity(intentAdvice);
				}
			}
		});
	}
}
