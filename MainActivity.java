package com.example.calllogtest;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.CallLog;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static ArrayList<String> callLogList = new ArrayList<String>();;
	private static ArrayAdapter<String> callLogListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		callLogListAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, callLogList);
		final ListView logListView = (ListView) findViewById(R.id.logListView);
		logListView.setAdapter(callLogListAdapter);
		logListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		
		getPhoneData();
	}
	
	private void getPhoneData() {
		//get handle on database for call logs
		ContentResolver contentResolver = getContentResolver();
		Cursor phoneLog = contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null ,null);
		
		int cachedNameColumn = phoneLog.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int phoneNumberColumn = phoneLog.getColumnIndex(CallLog.Calls.NUMBER);
		int dateColumn = phoneLog.getColumnIndex(CallLog.Calls.DATE);
		
		//print out Call Log For Last Six Months

		//Fill string with call Log
		int phoneLogLength = phoneLog.getCount();
		for (int logIndex = 0; logIndex < phoneLogLength; logIndex++)
		{
			phoneLog.moveToPosition(logIndex);
			String cachedName = phoneLog.getString(cachedNameColumn);
			String phoneNumber = phoneLog.getString(phoneNumberColumn);
			String stringDate = phoneLog.getString(dateColumn);
			Date date = new Date(Long.valueOf(stringDate));
			DateFormat dateFormat = DateFormat.getInstance();
			
			String printOut = "Name: " + cachedName + " Phone Number: " + phoneNumber +
								" Date: " + dateFormat.format(date);
			callLogList.add(printOut);
		}
		
		callLogListAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
