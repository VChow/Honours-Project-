package com.application.progym.activities;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.application.progym.R;
import com.application.progym.activities.menubar.Activity_About;
import com.application.progym.activities.menubar.Activity_Help;
import com.application.progym.activities.menubar.Activity_Preferences;
import com.application.progym.activities.menubar.Activity_Update;
import com.application.progym.adapters.ImageAdapterWorkout_Arms;
import com.application.progym.libs.PojoMapper;
import com.application.progym.stores.Store_Workout;
import com.application.progym.stores.Store_Workouts;
import com.application.progym.utilities.JSONFetcher;
import com.application.progym.utilities.Utilities;

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

/**
 * Handles the Arms Workouts information.
 * 
 */
public class Activity_Workout_Arms extends Activity{
	
	String JSONString = "";
	private Store_Workouts allWorkouts = new Store_Workouts();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_arms); 
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);

		//Get workouts from the JSON.
        JSONString = JSONFetcher.readAssets(getApplicationContext(), "workout_arms.json");
		
        parseString();
        
        setupGridView();
		
	}  
	 
	/**
	 * Constructs the Grid View and populates with Images via the ImageAdapters.
	 */
	private void setupGridView()
	{
		//Locate Grid View from .xml Layout.
		GridView gridview = (GridView) findViewById(R.id.gridview_main);
		
		//Attach ImageAdapter to gridview that adds categories.
		gridview.setAdapter(new ImageAdapterWorkout_Arms(this));
		
		//Setup Event Listener to direct user to information page.
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent intentObjectDescription = new Intent(getApplicationContext(), Activity_ObjectDescription.class);
			
				//Add additional paremeters to intent for information.
				//intentObjectDescription.putExtra("Type", "Arms"); //Type of workout
				//intentObjectDescription.putExtra("Position", position); //Number of workout
				
				String name = "";
				String description = "";
				String image = "";
				
				name = allWorkouts.Arms.get(position).name;
				description = allWorkouts.Arms.get(position).description;
				image = allWorkouts.Arms.get(position).image;
				
				int resourceID = getApplicationContext().getResources().getIdentifier(image, "drawable", getApplicationContext().getPackageName());
				
				intentObjectDescription.putExtra("Name", name); 
				intentObjectDescription.putExtra("Description", description); 
				intentObjectDescription.putExtra("ImageID", resourceID); 
				intentObjectDescription.putExtra("ImageName", image);
				
				startActivity(intentObjectDescription);
			}
		});
		
	}
	
	/**
     * Takes the JSONString and parses the JSON objects into individual items.
     */
    private void parseString()
    {
    	try
    	{
			allWorkouts = (Store_Workouts)PojoMapper.fromJson(JSONString, Store_Workouts.class);

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
