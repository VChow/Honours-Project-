package com.application.progym.utilities;

import java.io.InputStream;
import java.nio.Buffer;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class JSONFetcher {

	
	public static void readFile(String filename, Context appContext)
	{

	}
	
	/**
	 * Reads assets file into a string
	 * @return Assets file as string
	 */
	public static String readAssets(Context context, String filename)
	{	
		//Log.d("PD", "readAssets Filename: " + filename);
		
		String bufferString = null;
		try
		{
			AssetManager assetManager = context.getAssets();
			InputStream is = assetManager.open(filename); //filename.json
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			bufferString = new String(buffer);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//Log.d("PD", "readAssets File Contents: " + bufferString);
		return bufferString;
	}
}
