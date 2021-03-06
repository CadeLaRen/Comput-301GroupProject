package ca.ualberta.cs.team16app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

// Edited by Tiancheng Shen
// Part of the codes are cited from lab sample "MockLocationTester"

/**
 * This class demonstrate usage of GPS. It receive GPS data and show on the screen. It also add destination geolocation and lead to map.
 * 
 * @author tshen
 *
 */
public class GeolocationActivity extends Activity{

	public static final String Geo_PROVIDER = "GeolocationProvider";
	@Override
	
	/**
	 * @param txt file
	 * @print latitude and longitude
	 * @throws FileNotFoundException and IOException
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geolocation);
		
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null){
			TextView tv = (TextView) findViewById(R.id.gps);
			tv.setText("Lat: " + location.getLatitude()
			+ "\nLong: " + location.getLongitude());
		}
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
		
		// set home geolocation
		/*final EditText et1 = new EditText(GeolocationActivity.this);
		final EditText et2 = new EditText(GeolocationActivity.this);
		final String lat = et1.getText().toString();
		final String lng = et2.getText().toString();
		if (lat != null && lng != null) {
			TextView tvhome = (TextView) findViewById(R.id.homeGeo);
			tvhome.setText("Latitude: " + lat + "\nLongitude: " + lng);
		}*/
		
		ArrayList<String> homeGeo = new ArrayList<String>();
		try {
	        FileInputStream inStream = this.openFileInput("homeGeo.txt");
	        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
			String line = in.readLine();
			while (line != null) {
				homeGeo.add(line);
				line = in.readLine();
			}
	        in.close();
	        inStream.close();
	        TextView tvhome = (TextView) findViewById(R.id.homeGeo);
	        tvhome.setText("Latitude: " + homeGeo.get(0).toString() + "\nLongitude: " + homeGeo.get(1).toString());
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    catch (IOException e){
	        return;
	    }
		
		Button sethome = (Button)findViewById(R.id.setHome);
		sethome.setOnClickListener(new View.OnClickListener(){
			@Override
			/**
			 * @param txt file
			 * @print latitude and longitude
			 * @throws FileNotFoundException and IOException
			 */
			public void onClick(View v){
				AlertDialog.Builder alert = new AlertDialog.Builder(GeolocationActivity.this);
				alert.setTitle("Please input the latitude and longitude of your home geolocation");
				
				LinearLayout mainLayout=new LinearLayout(GeolocationActivity.this);
				mainLayout.setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
				mainLayout.setOrientation(LinearLayout.VERTICAL);
				LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);
				lp.setMargins(20,0,20,0);
				
				TextView tv1 = new TextView(GeolocationActivity.this);
				tv1.setLayoutParams(lp);
				tv1.setText("Latitude:");
				final EditText et1 = new EditText(GeolocationActivity.this);
				et1.setLayoutParams(lp); 
				
				TextView tv2 = new TextView(GeolocationActivity.this);
				tv2.setLayoutParams(lp);
				tv2.setText("Longitude:");
				final EditText et2 = new EditText(GeolocationActivity.this);
				et2.setLayoutParams(lp); 
				
				mainLayout.addView(tv1);
				mainLayout.addView(et1);
				mainLayout.addView(tv2);
				mainLayout.addView(et2);
				alert.setView(mainLayout);
				
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int whichButton) {
						TextView tvhome = (TextView) findViewById(R.id.homeGeo);
						if (et1 != null & et2 != null) {
							String lat = et1.getText().toString();
							String lng = et2.getText().toString();
							tvhome.setText("Latitude: " + lat + "\nLongitude: " + lng);
							 try {
						            FileOutputStream outStream = openFileOutput("homeGeo.txt",Context.MODE_PRIVATE);
						            outStream.write(new String(et1.getText().toString()+"\n").getBytes());
						            outStream.write(new String(et2.getText().toString()+"\n").getBytes());
						            outStream.close();
						        } catch (FileNotFoundException e) {
						            return;
						        }
						        catch (IOException e){
						            return ;
						        }
							
						} else {
							tvhome.setText("You have not set home geolocation yet");
						}
					}

				});
				
				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
				
				alert.show();
			}
		});
		
		Button addDest = (Button)findViewById(R.id.addDest);
		addDest.setOnClickListener(new View.OnClickListener(){
			@Override
			/**
			 * @param txt file
			 * @return file
			 * @throws FileNotFoundException and IOException
			 */
			public void onClick(View v){
				AlertDialog.Builder alert2 = new AlertDialog.Builder(GeolocationActivity.this);
				alert2.setTitle("Please input destination, latitude and longitude");
				
				LinearLayout mainLayout=new LinearLayout(GeolocationActivity.this);
				mainLayout.setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
				mainLayout.setOrientation(LinearLayout.VERTICAL);
				LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);
				lp.setMargins(20,0,20,0);
				
				TextView tv3 = new TextView(GeolocationActivity.this);
				tv3.setLayoutParams(lp);
				tv3.setText("Destination:");
				final EditText et3 = new EditText(GeolocationActivity.this);
				et3.setLayoutParams(lp); 
				
				TextView tv4 = new TextView(GeolocationActivity.this);
				tv4.setLayoutParams(lp);
				tv4.setText("Latitude:");
				final EditText et4 = new EditText(GeolocationActivity.this);
				et4.setLayoutParams(lp);
				
				TextView tv5 = new TextView(GeolocationActivity.this);
				tv5.setLayoutParams(lp);
				tv5.setText("Longitude:");
				final EditText et5 = new EditText(GeolocationActivity.this);
				et5.setLayoutParams(lp);
				
				mainLayout.addView(tv3);
				mainLayout.addView(et3);
				mainLayout.addView(tv4);
				mainLayout.addView(et4);
				mainLayout.addView(tv5);
				mainLayout.addView(et5);
				alert2.setView(mainLayout);
				
				alert2.setPositiveButton("Add", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int whichButton) {
						if (et3 != null & et4 != null) {
							 try {
						            FileOutputStream outStream = openFileOutput("destination.txt",Context.MODE_APPEND);
						            outStream.write(new String(et3.getText().toString()+"\n").getBytes());
						            outStream.write(new String(et4.getText().toString()+"\n").getBytes());
						            outStream.write(new String(et5.getText().toString()+"\n").getBytes());
						            outStream.close();
						        } catch (FileNotFoundException e) {
						            return;
						        }
						        catch (IOException e){
						            return ;
						        }
							
						}
					}

				});
				
				alert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
				
				alert2.show();
			}
		});
		
		Button openMap = (Button)findViewById(R.id.openMap);
        openMap.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v){
        		Intent intent=new Intent(GeolocationActivity.this, Map.class);
        		startActivity(intent);
        	}
        });
	}
	
	/**
	 * @param latitude and longitude
	 * @print latitude and longitude
	 * @throws no exception
	 */
	private final LocationListener listener = new LocationListener() {
		public void onLocationChanged (Location location) {
			TextView tv = (TextView) findViewById(R.id.gps);
			if (location != null) {
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				Date date = new Date(location.getTime());
				
				tv.setText("The location is: \nLatitude: " + lat
						+ "\nLongitude: " + lng
						+ "\n At time: " + date.toString());
			} else {
				tv.setText("Cannot get the location");
			}
		}
		
		public void onProviderDisabled (String provider) {
			
		}
		
		public  void onProviderEnabled (String provider) {
			
		}
		
		public void onStatusChanged (String provider, int status, Bundle extras) {
			
		}
	};


}
