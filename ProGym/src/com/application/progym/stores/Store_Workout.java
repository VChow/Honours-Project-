package com.application.progym.stores;

import java.util.ArrayList;

/**
 * Used to store details about a Workout.
 */
public class Store_Workout {
	public int index; //Index of object
	public String name; //Name of workout
	public String description; //Description of workout
	public ArrayList<String> image_paths; //List of images to be displayed
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getImage_paths() {
		return image_paths;
	}
	public void setImage_paths(ArrayList<String> image_paths) {
		this.image_paths = image_paths;
	}
	
}
