package com.application.progym.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Adapter for Image Gallery, extracts the Image from the database. 
 * 
 * Reference: http://www.learn-android-easily.com/2013/07/android-gallery-view-example.html
 */
@SuppressWarnings("deprecation")
public class GalleryImageAdapter extends BaseAdapter
{
	private Context mContext;
	private String image1, image2;
    private ArrayList<Integer> imageNames = new ArrayList<Integer>();
	
    /**
     * Initialises an instance of the GalleryImageAdapter
     * 
     * @param context The Context of the application.
     * 
     */
    public GalleryImageAdapter(Context context, ArrayList<Integer> names) 
    {
        mContext = context;
        imageNames = names;
    }
       
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return imageNames.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return position;
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int index, View view, ViewGroup viewGroup) {
		ImageView i = new ImageView(mContext);
        
		i.setImageResource(imageNames.get(index));
		Log.d("PD", "imageName.get(index): " + imageNames.get(index));
        i.setLayoutParams(new Gallery.LayoutParams(100, 100));
    
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
	}

}
