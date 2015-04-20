package com.application.progym.activities;

import java.util.ArrayList;
import java.util.Random;

import com.application.progym.R;
import com.application.progym.activities.menubar.Activity_About;
import com.application.progym.activities.menubar.Activity_Help;
import com.application.progym.activities.menubar.Activity_Preferences;
import com.application.progym.activities.menubar.Activity_Update;
import com.application.progym.adapters.GalleryImageAdapter;
import com.application.progym.utilities.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Generalised class to extract information from the JSON related to a specific Workout. 
 */
public class Activity_ObjectDescription extends Activity{

	private String name = "";
	private String description = "";
	private String image = "";
	private int imageID = 0;
	
	private TextView textView;
	private ImageView selectedImage;
	
	private String image1, image2;
    private ArrayList<Integer> imageNames = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_object_description); 
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			name = extras.getString("Name");
			description = extras.getString("Description");
			imageID = extras.getInt("ImageID");
			image = extras.getString("ImageName");
		}
		
		image1 = image;
        image2 = image+"2";
        
        imageNames.add(getApplicationContext().getResources().getIdentifier(image1, "drawable", getApplicationContext().getPackageName()));
        imageNames.add(getApplicationContext().getResources().getIdentifier(image2, "drawable", getApplicationContext().getPackageName()));
		
		displayObjectDetails();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void displayObjectDetails()
	{
		setTitle(name);
		setImageGallery();
		setImage();
		
		//Find TextView and allow scrolling.
        textView = (TextView)findViewById(R.id.textViewItem);
        textView.setText(description);
	}
	
	public void setImageGallery()
	{
		Log.d("PD", "setImageGallery() ENTRY");
		
		//Set up the Image Gallery.
	    Gallery gallery = (Gallery) findViewById(R.id.imageGallery);
        selectedImage=(ImageView)findViewById(R.id.imageSelected);
        gallery.setSpacing(1);
        gallery.setScaleX(1.7f);
        gallery.setScaleY(1.7f);
        gallery.setY(80f);
        
        gallery.setAdapter(new GalleryImageAdapter(getApplicationContext(), imageNames));
        
        // clicklistener for Gallery
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {               
                // show the selected Image
                selectedImage.setImageResource(imageNames.get(position));
            }

        });
	}
	
	public void setImage()
	{
		//selectedImage=(ImageView)findViewById(R.id.imageSelected);
			
		selectedImage.setBackgroundResource(imageID);
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
