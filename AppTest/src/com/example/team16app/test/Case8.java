package com.example.team16app.test;

import com.example.team16app.ApproverClaimListActivity;
import com.example.team16app.ApproverClaimViewActivity;
import com.example.team16app.Claim;
import com.example.team16app.ClaimList;
import com.example.team16app.ClaimListController;

import junit.framework.TestCase;


import android.content.ClipData.Item;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import junit.framework.TestCase;

public class Case8 extends ActivityInstrumentationTestCase2<ApproverClaimListActivity> {


	private Intent launch;
	final Button approverButton;
	
	//US08.01.01 & US08.06.02
	public void SubmittedClaimsTest() {
		// init the controller
		ClaimListController list = new ClaimListController();
		// set all values
		Claim claim = new Claim();
		claim.getStartDate(20121212);
		claim.getEndDate(20130101);
		claim.getStatus("submit");
		claim.getName("approver test");
		claim.addDestination("Beijing");
		claim.getCurrency("EUR");
		claim.getCost(1000);
		claim.addComment("test comment");
		ClaimListController test_con = new ClaimListController();
		test_con.add(claim);
		Claim output = list.get(0);
		
		// do the test
		assertEquals("start date not same",claim.getStartDate(0),output.getStartDate(0));
		assertEquals("end date not same",claim.getEndDate(0),output.getEndDate(0));
		assertEquals("status not same",claim.getStatus,output.getStatus(null));
		assertEquals("name not same",claim.getName,output.getName);
		assertEquals("destination not same",claim.getDestination,output.getDestination());
		assertEquals("total not same",claim.getCurrency(),output.getCurrency());
		assertEquals("currency not same",claim.getCurrency(),output.getCurrency());
		assertEquals("cost not same",claim.getCost(0),output.getCost(0));
		assertEquals("comment not equal","hello world",claim.getComment());
	}
	
	//US08.02.01 check if the claim is sorted in approver's list
	public void SortClaimTest() {
		
		
		// Populate the claim controller
		Claim a = new Claim(null);
		Claim b = new Claim(null);
		// Set the dates for 2 dates
		a.getStartDate(1);
		b.getStartDate(2);
		// Create the controller, add the claims to the controller
		ClaimListController test = new ClaimListController();
		test.add(b);
		test.add(a);
		assertTrue("not sorted",test.get(0).getStartDate()<=test.get(1).getStartDate());
	}
	
	
	//US08.03.02 & US08.04.02  approver can see the details of submitted claims
	public void SubmittedClaimDetailsTest() {
		ApproverClaimListActivity activity = getActivity();
		ListView view = (ListView) activity.findViewById(R.id.claimlistview);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),view);
		ClaimList test = new ClaimList();
		Item item = new Item("car",2012,"transpotation","USD",20000);
		Claim claim = new Claim(item);
		assertTrue("expense is not in claim",claim.contains(item));
	}
	
	
	//US08.05.02   allow approver view photo recept
	public void VeiwPhotoTest() {
		ApproverClaimListActivity activity = getActivity();
		ImageButton view = (Button) activity.findViewById(R.id.photograph);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),view);
		assertNotNull("not empty",view.getDrawable());
		assertTrue(View.VISIBLE == view.getVisibility());
	}
	
	
	
	//US08.07.02 & US08.08.02 approver return or approve claims and display his name so that user can see its status and name
	public void ReturnClaimTest() {
		Claim claim = new Claim(null);
		claim.getStatus("returned");
		claim.getApprover("test approver");
		AssertTrue("approvers don't match",claim.getApprover("Leon"),"test approver");
		AssertTrue("claim is not returned",claim.getStatus("submitted"),"returned");
		
	}
	
