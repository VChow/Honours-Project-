package com.application.progym.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.progym.R;
import com.application.progym.libs.PojoMapper;
import com.application.progym.stores.Store_Workout;
import com.application.progym.stores.Store_Workouts;
import com.application.progym.utilities.JSONFetcher;

public class ImageAdapterWorkout_Arms extends BaseAdapter{

	private List<Item> items = new ArrayList<Item>();
    private LayoutInflater inflater;

    private ArrayList<Store_Workout> workouts = new ArrayList<Store_Workout>();
    
    public Context context;
    
    /**
     * Instantiates a new instance of the ImageAdapterMain class.
     * 
     * @param context The Context of the application/object.
     */
    public ImageAdapterWorkout_Arms(Context context, ArrayList<Store_Workout> workouts) {
        inflater = LayoutInflater.from(context);
        
        //addItems(workouts, context);
        
        this.context = context;
    }

    /**
     * Instantiates a new instance of the ImageAdapterMain class.
     * 
     * @param context The Context of the application/object.
     */
    public ImageAdapterWorkout_Arms(Context context)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        
        String JSONString;
        
        //Get workouts from the JSON.
        JSONString = JSONFetcher.readAssets(context, "workout_arms.json");
        
        //Parse string into workouts ArrayList Store.
        parseString(JSONString);
        
    }
    
    /**
     * Takes the JSONString and parses the JSON objects into individual items.
     */
    private void parseString(String string)
    {
    	try {
			Store_Workouts allWorkouts = (Store_Workouts)PojoMapper.fromJson(string, Store_Workouts.class);
			for(Store_Workout workout : allWorkouts.Arms)
			{ 
				addItem(workout.getName(), workout.getImage());
				Log.d("PD", "Arms:" + workout.name);
			}
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
        
    public void addItem(String workout_name, String image_name)
    {
    	int resourceID = context.getResources().getIdentifier(image_name, "drawable", context.getPackageName());
    	
    	items.add(new Item(workout_name,resourceID));
    }
  
    
    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int i) {
        return items.get(i).drawableId;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if(v == null) {
            v = inflater.inflate(R.layout.grid_layout, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
            v.setPadding(5, 5, 5, 5);
        }

        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);

        Item item = (Item)getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
    }

    /**
     * Creates a new Item to add to the Adapter.
     */
    private class Item {
        final String name;
        final int drawableId;
        final Uri imageUri;
        final String imageName;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
            this.imageUri = null;
            this.imageName = null;
        }
        
        Item(String name, Uri imageUri) {
        	this.name = name;
            this.drawableId = (Integer) null;
            this.imageUri = imageUri;
            this.imageName = null;
        }
        
        Item(String name, String imageName) {
        	this.name = name;
            this.drawableId = (Integer) null;
            this.imageUri = null;
            this.imageName = imageName;
        }
    }
}
