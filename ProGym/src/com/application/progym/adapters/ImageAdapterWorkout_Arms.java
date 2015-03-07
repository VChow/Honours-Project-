package com.application.progym.adapters;

import java.util.ArrayList;
import java.util.List;

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
import com.application.progym.stores.Store_Workout;

public class ImageAdapterWorkout_Arms extends BaseAdapter{

	private List<Item> items = new ArrayList<Item>();
    private LayoutInflater inflater;

    /**
     * Instantiates a new instance of the ImageAdapterMain class.
     * 
     * @param context The Context of the application/object.
     */
    public ImageAdapterWorkout_Arms(Context context, ArrayList<Store_Workout> workouts) {
        inflater = LayoutInflater.from(context);

        addItems(workouts, context);
    }

    //Adds new items to the grid based on the List of items.
    public void addItems(ArrayList<Store_Workout> workouts, Context context)
    {
    	//Store_Workout workout = new Store_Workout();
    	
    	//Loop through all workouts, add image to grid if not empty.
    	for(int i = 0; i < workouts.size(); i++)
    	{
    		String name = (workouts.get(i)).getName();
    		String imagename = (workouts.get(i).getImage_paths().get(0));
    		String final_imagename = "R.drawable." + imagename;
    		
    		//http://stackoverflow.com/questions/4427608/android-getting-resource-id-from-string
    		//int resId=ImageAdapterWorkout_Arms.this.getResources().getIdentifier("ball_red", "drawable", ImageAdapterWorkout_Arms.this.getPackageName());
    		
    		//http://stackoverflow.com/questions/15488238/using-android-getidentifier
    		int resourceID = context.getResources().getIdentifier(imagename, "drawable", context.getPackageName());
    		Log.d("PD", "Imagename: " + imagename + " resourceID:" + resourceID);
    		
    		//items.add(new Item(name, R.drawable.doge));
    		items.add(new Item(name, resourceID));
    	}
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
