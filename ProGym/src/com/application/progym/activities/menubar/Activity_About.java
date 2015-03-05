package com.application.progym.activities.menubar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.application.progym.R;
import com.application.progym.activities.Activity_Home;
import com.application.progym.utilities.Utilities;

/**
 * Displays the About page: 
 * Author, Project, Year, Purpose, Technologies.
 */
public class Activity_About extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		switch (item.getItemId()){
		case (R.id.action_home):
        	this.startActivity(new Intent(this, Activity_Home.class));
        	this.finish();
        	return true;
		case (R.id.action_help):
			this.startActivity(new Intent(this, Activity_Help.class));
			return true;		
		case (R.id.action_about):
			this.startActivity(new Intent(this, Activity_About.class));
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
