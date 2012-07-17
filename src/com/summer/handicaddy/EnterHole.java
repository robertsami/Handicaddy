package com.summer.handicaddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;

public class EnterHole extends Activity{

	int day;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_hole);
		
		DatePicker date = (DatePicker) findViewById(R.id.SelectDate);
		
		day = date.getDayOfMonth() + date.getMonth(); //date.get
		Button enterRound = (Button) findViewById(R.id.Enter_Round);
		enterRound.setOnClickListener(null);
	}
	 
	
	
}
