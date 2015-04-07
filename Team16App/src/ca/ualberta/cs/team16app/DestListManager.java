/**
 * Team16App: travel expense tracking application
 * Copyright (C) 2015 peijen  Chris Lin 
 * dmeng  Di Meng 
 * tshen
 * qtan  Qi Tan 
 * yuentung  
 * omoyeni  Omoyeni Adeyemo 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.

 */
package ca.ualberta.cs.team16app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;


public class DestListManager
{
	static final String prefFile = "DestList";  //const
	static final String clKey = "destList";     //const
	Context context;
	
	
	//base on eclass youtube video by Abram Hindle:https://www.youtube.com/watch?v=uat-8Z6U_Co
	//allow us to get destlist manager	
	static private DestListManager destListManager = null;
	
	public static void initManager(Context context){
		if(destListManager == null){
			if(context ==null){
				throw new RuntimeException("missing context for DestListManager");
			}
			destListManager = new DestListManager(context);
		}		
	}
	
	public static DestListManager getManager(){
		if(destListManager == null){			
				throw new RuntimeException("Did not initialize Manager");
		}
		return destListManager;
	}
	
	
	public DestListManager(Context context) {
		
		this.context = context;
	}
	public DestList loadDestList() throws ClassNotFoundException, IOException{
		SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
		String destListData = settings.getString(clKey,"");
		// check if the list is empty
		if(destListData.equals("")){
			return new DestList();  //if it's empty, create a new list
		}
		else{
			return destListFromString(destListData);  //return list
		}	 
	}
	
	static public DestList destListFromString(String destListData) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(destListData, Base64.DEFAULT));
		ObjectInputStream oi = new ObjectInputStream(bi);
		return (DestList) oi.readObject();
		
		
		
	}
	static public String destListToString(DestList dl) throws IOException {
	
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(dl);
		oo.close();
		byte bytes[] = bo.toByteArray();
		
		return Base64.encodeToString(bytes,Base64.DEFAULT);
	}
	
	public void saveDestList(DestList dl) throws IOException{
		SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(clKey, destListToString(dl));
		editor.commit();
		
		
	}
}
