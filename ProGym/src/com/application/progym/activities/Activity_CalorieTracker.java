package com.application.progym.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.application.progym.utilities.Utilities;

/**
 * Handles the Calorie Tracker activity.
 * Lets users View/Add meals taken through the day/history.
 */
public class Activity_CalorieTracker extends Activity{

	TextView textDate, textTotalCalories;
	Button buttonAddItem, buttonPrevious, buttonNext;
	TableLayout calorieTable;
	
	Date currentDate;
	SimpleDateFormat format;
	String formattedDate;
	
	int dayCalorie, mealCalorie;
	boolean addMore;
	
	TableRow tableHeaderRow;
	List<TableRow> tableRows;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calorie_tracker); 
		
		ViewConfiguration config = ViewConfiguration.get(this);
		Utilities.disableHardwareMenuKey(config);

		//Initialise
		initialiseActivity();
		initialiseUI();
	
	}

	/**
	 * Initialises and instatiates the required objects/variables.
	 */
	private void initialiseActivity()
	{
		//Instatiate UI Widgets.
		textDate = (TextView) findViewById(R.id.textDate);
		textTotalCalories = (TextView) findViewById(R.id.textTotalCalories);
		
		buttonAddItem = (Button) findViewById(R.id.buttonAddItem);
		buttonPrevious = (Button) findViewById(R.id.buttonPrevious);
		buttonNext = (Button) findViewById(R.id.buttonNext);
	
		calorieTable = (TableLayout) findViewById(R.id.tableCalories);
	
		//Initialise Variables
		currentDate = new Date();
		format = new SimpleDateFormat("EEEE dd MMMM yyyy");
		formattedDate = format.format(currentDate);
		
		Log.d("PD", "Formatted Date: " + formattedDate);
		
		dayCalorie = mealCalorie = 0;
		addMore = false;
		
		tableHeaderRow = new TableRow(this);
		tableRows = new ArrayList<TableRow>();
	}
	
	/**
	 * Initialises UI widgets with correct information.
	 **/
	private void initialiseUI()
	{
		textDate.setText(formattedDate);
		textTotalCalories.setText("" + dayCalorie + "/2500 Calories");
	}
	
	/**
	 * Called when buttonPrevious is pressed.
	 * Returns the Calorie Tracker information for previous day.
	 */
	public void getPreviousTracker(View view)
	{
		if(checkPreviousNextTracker("left"))
		{
			Toast.makeText(getApplicationContext(), "No entry found.", Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Called when buttonNext is pressed.
	 * Returns the Calorie Tracker information for next day.
	 */
	public void getNextTracker(View view)
	{
		if(checkPreviousNextTracker("right"))
		{
			Toast.makeText(getApplicationContext(), "No entry found.", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Check if there is a Previous or Next Calorie Tracker information.
	 * @params leftright String indicating direction to check in.
	 * @return hasLeftRight boolean True is there another Tracker.
	 */
	private boolean checkPreviousNextTracker(String leftright)
	{
		boolean hasLeftRight = true;
		
		//TODO
		//Read in JSON file for Calorie Tracker
		//Check if string equals 'Left' or 'Right'
		//Check if there is a Previous or Next
		//Return true if found, else false
		
		return hasLeftRight;
	}
	
	/**
	 * Resets variables for adding a new Meal.
	 */
	private void resetValues()
	{
		tableHeaderRow = new TableRow(this);
		tableRows = new ArrayList<TableRow>();
		
		addMore = false;
		mealCalorie = 0;
	}
	
	/**
	 * Called when buttonAddItem is pressed.
	 * Displays series of Alert Dialogs for input.
	 * Adds input to table.
	 */
	public void addCalorieItem(View view)
	{
		//Reset values
		resetValues();
		
		//Get input
		getMealType();
	}
	
	/**
	 * Displays a dialog to obtain Meal Type.
	 */
	private void getMealType()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select Meal Type")
			   .setItems(R.array.meal_type, new DialogInterface.OnClickListener() {

				   		@Override
				   		public void onClick(DialogInterface dialog, int which) {
				   			String mealType = "";
				   			
				   			//Pass the meal type to getMealItem().
				   			
				   			switch(which){
				   			case 0: mealType = "Breakfast";
				   					break;
				   			case 1: mealType = "Lunch";
				   					break;
				   			case 2: mealType = "Dinner";
				   					break;
				   			case 3: mealType = "Snack";
				   					break;
				   			default: mealType = "Error";
				   					break;
				   			}
				   			
				   			getMealItem(mealType);
				   		}})

			   	.show();
	}
	
	/**
	 * Displays a dialog with custom layout to obtain Meal Item Details.
	 * Reference: http://stackoverflow.com/questions/6626006/android-custom-dialog-cant-get-text-from-edittext
	 * Reference: http://developer.android.com/guide/topics/ui/dialogs.html
	 * @param meal_type
	 */
	private void getMealItem(String meal_type)
	{
		final String test = meal_type; 			
		
		//Create layoutinflater and obtain view for future use.
		LayoutInflater inflater = LayoutInflater.from(this);            
		final View dialogView = inflater.inflate(R.layout.dialog_calorie_item, null);
		
		//Create the dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		//Set dialog properties
		builder.setTitle("Enter details about food");
		builder.setView(dialogView);

		//Instantiate EditTexts
		final EditText editName = (EditText) dialogView.findViewById(R.id.itemName);
		final EditText editCalorie = (EditText) dialogView.findViewById(R.id.itemCalorie);

		//Set dialog Yes option
		builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {

				//Get user input values
				String name = editName.getText().toString();
				int cals = Integer.valueOf(editCalorie.getText().toString());

				//Add values to table
				addToTable(test, name, cals);
			}});

		//Set dialog No option
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
			}});

		builder.show();
	}
	
	/**
	 * Called after user enters food information for a meal.
	 * Prompts user if they want to add another item for this meal.
	 */
	private void getUserChoice()
	{		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add another item to meal?")
			   .setPositiveButton("Yes", new DialogInterface.OnClickListener()
			   {
				   public void onClick(DialogInterface dialog, int which) {
					   addMore = true;
				   }
			   })
			   .setNegativeButton("No", new DialogInterface.OnClickListener()
			   {
				   public void onClick(DialogInterface dialog, int which) {
					   addMore = false;
				   }
			   })
			   .show();
	
	}
	
	/**
	 * Called after user enters input for new meal.
	 * Adds input to variables.
	 * Prompts user for additional items in the meal.
	 * Add items to the table.
	 *  
	 * @param type String the Meal Type: Breakfast, Lunch, Dinner, Snack
	 * @param name String the Name of the item.
	 * @param cals String the Calories in the item.
	 */
	private void addToTable(String type, String name, int cals)
	{
		Log.d("PD", "Meal Type: " + type);
		Log.d("PD", "Food Name: " + name);
		Log.d("PD", "Food Cals: " + cals);
		
		//Add values to List<TableRows>
		addToTableRowsList(name, cals);	
		
		//Prompt user for new item in meal.
		getUserChoice();
		while(addMore == true)
		{
			//show dialog
			getMoreMealItem();
			getUserChoice();
		}

		//Rows are already created at this point		
		//Create header once we know contents of all rows.
		createHeader(type);

		//Add HeaderRow and TableRows to the Calorie Table.
		addRowsToView();
	
		//Update running calorie total for the day.
		dayCalorie = dayCalorie + mealCalorie;
		
		//Update UI.
		textTotalCalories.setText("" + dayCalorie + "/2500 Calories");
	}
	
	/**
	 * Creates a Header row for a new Meal.
	 * 
	 * @param type String the meal type: Breakfast, Lunch, Dinner, Snack
	 */
	private void createHeader(String type)
	{
		TableRow headerRow = (TableRow) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablecalorie_header_row, null);

		//Find TextView for Meal Type and set Meal Type.
		TextView meal = (TextView) headerRow.findViewById(R.id.textMeal);
		meal.setText(type);
		
		//Find TextView for Total Calories and set Total Calories.
		TextView calories = (TextView) headerRow.findViewById(R.id.textMealCalorieCount);
		calories.setText(""+ mealCalorie + " Calories");
		
		tableHeaderRow = headerRow;
		//calorieTable.addView(headerRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}
	
	/**
	 * Adds input to a new TableRow, and store inside List<TableRow>.
	 * 
	 * @param name
	 * @param cals
	 */
	private void addToTableRowsList(String name, int cals)
	{
		TableRow newRow = (TableRow) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablecalorie_row, null);
		
		TextView foodName = (TextView) newRow.findViewById(R.id.textItem);
		foodName.setText(name);
		
		TextView foodCals = (TextView) newRow.findViewById(R.id.textItemCalorieCount);
		foodCals.setText(cals + " calories");
		
		//Add to running counter for meal
		mealCalorie = mealCalorie + cals;
		
		tableRows.add(newRow);
	}
	
	/**
	 * Called when user selects option to add more items to meal.
	 */
	private void getMoreMealItem()
	{		
		//Create layoutinflater and obtain view for future use.
		LayoutInflater inflater = LayoutInflater.from(this);            
		final View dialogView = inflater.inflate(R.layout.dialog_calorie_item, null);
		
		//Create the dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		//Set dialog properties
		builder.setTitle("Enter details about food");
		builder.setView(dialogView);

		//Instantiate EditTexts
		final EditText editName = (EditText) dialogView.findViewById(R.id.itemName);
		final EditText editCalorie = (EditText) dialogView.findViewById(R.id.itemCalorie);

		//Set dialog Yes option
		builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {

				//Get user input values
				String name = editName.getText().toString();
				int cals = Integer.valueOf(editCalorie.getText().toString());

				//Add values to table
				addToTableRowsList(name, cals);
			}});

		//Set dialog No option
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
			}});

		builder.show();
	}
	
	/**
	 * Adds all the rows to the view.
	 */
	private void addRowsToView()
	{
		//Add header to view.
		calorieTable.addView(tableHeaderRow, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	
		//Add all table rows to view.
		//TODO
		for(int i = 0; i < tableRows.size(); i++)
		{
			calorieTable.addView(tableRows.get(i), new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
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
