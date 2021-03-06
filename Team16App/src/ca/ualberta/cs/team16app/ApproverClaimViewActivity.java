/*
 * This file should allow the approver to see the claim details and 
 * approve or return the claim. 
 * The functions mentioned above are still being worked on. Also,
 * to do: approver should be transferred to claim page or comment page
 * depending on the option clicked */
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

import ca.ualberta.cs.team16app.Claim.Status;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Team16
 * 
 *
 */
/**
 * This file should allow the approver to see the claim details and 
 * approve or return the claim. 
 * The functions mentioned above are still being worked on. Also,
 * To do: approver should be transferred to claim page or comment page
 * depending on the option clicked
 *
 *
 */

public class ApproverClaimViewActivity extends Activity {

	private Button returnClaimButton;
	private Button approveClaimButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_approver_claim_view);
		
		returnClaimButton = (Button)findViewById(R.id.returnbutton);
		approveClaimButton = (Button)findViewById(R.id.approvebutton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver_claim_view, menu);
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
	
	public void returnClaimButton(View v){
		Toast.makeText(this,"Returning claim: comment", Toast.LENGTH_SHORT).show(); // show message
		
		Intent intent = new Intent(ApproverClaimViewActivity.this,ApproverCommentsActivity.class);//
		startActivity(intent);// go to approver comments
	}
	
	public void approveClaimButton(View v){
		
		//alertDialog code adapted from android snippets:
		//http://www.androidsnippets.com/prompt-user-input-with-an-alertdialog
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Approver Name");
		alert.setMessage("Please Type in Your Name");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String value = input.getText().toString();
		  Intent intent = new Intent(ApproverClaimViewActivity.this,ApproverClaimListActivity.class);
		  startActivity(intent);
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
		
		/*Toast.makeText(this,"Claim Approved!", Toast.LENGTH_SHORT).show(); // show message
		
		Claim.Status status = Status.Approved;
		//ClaimList.removeClaim(Claim);
		Intent intent = new Intent(ApproverClaimViewActivity.this,ApproverClaimListActivity.class);//
		startActivity(intent);*/
	}
}
