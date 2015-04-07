/**
 * this class is an activity for creating a claim to a claim list
 * 
 * @author Chris Lin
 */

package ca.ualberta.cs.team16app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddClaimActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_claim);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_claim, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void saveclaim(View v){
		ClaimListController cm = new ClaimListController();
		EditText textview = (EditText) findViewById(R.id.claimNameInfo);
		EditText startdateView = (EditText) findViewById(R.id.startdate);
		EditText enddateView = (EditText) findViewById(R.id.enddate);
		EditText descriptView = (EditText) findViewById(R.id.reason);
		EditText destView = (EditText) findViewById(R.id.userName_create);
		cm.addClaim(new Claim(textview.getText().toString(),startdateView.getText().toString(),enddateView.getText().toString(),descriptView.getText().toString(),destView.getText().toString(),true));
		
		
		String name = textview.getText().toString();
		String start = startdateView.getText().toString();
		String  end = enddateView.getText().toString();
		String dest = destView.getText().toString();
		String des = descriptView.getText().toString();
		
		if(name.equals("")||start.equals("")||end.equals("")||dest.equals("")||des.equals("")){
			Toast.makeText(getApplicationContext(), "Make sure every entries are filled!",
			Toast.LENGTH_SHORT).show();
			
		}
		else{
			Toast.makeText(this,"added a claim", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AddClaimActivity.this,ClaimListActivity.class);
			startActivity(intent);
		}
		
		
	}
	
	public void geolocation(MenuItem menu){
		Toast.makeText(this, "Geolocation", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(AddClaimActivity.this, GeolocationActivity.class);
		startActivity(intent);
	}
}
