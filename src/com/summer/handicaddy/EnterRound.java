package com.summer.handicaddy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class EnterRound extends Activity{
	
	private RoundCatalog catalog;
	private Course coursePlayed;
	private TeeBox teePlayed;
	private String courseName;
	private String teeName;
	private RadioGroupUpdateListener listener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_round_entry);
		
		RadioGroup group = (RadioGroup) findViewById(R.id.tee_box_group_round_entry);
		
		
		RadioButton[] buttons = { (RadioButton) findViewById(R.id.teebox1), 
				(RadioButton) findViewById(R.id.teebox2), (RadioButton) findViewById(R.id.teebox3), 
				(RadioButton) findViewById(R.id.teebox4), (RadioButton) findViewById(R.id.teebox5), 
				(RadioButton) findViewById(R.id.teebox6), (RadioButton) findViewById(R.id.teebox7) };
		
		/*
		RadioButton newBox = new RadioButton(getApplicationContext());
		newBox.setText("Add new tee box...");
		group.addView(newBox, 10); //This button is hard-coded at position 10*/
	    
		catalog = (RoundCatalog) getIntent().getSerializableExtra("round_catalog");
		
		Course[] courses = catalog.getCourses();
		
		
		listener = new RadioGroupUpdateListener(group, courses, buttons);
		
		
		//Sets up auto-complete for easy round name fill
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.course_name);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, catalog.getCourseNames());
	    textView.setAdapter(adapter);
	    	
	    textView.addTextChangedListener(listener);
	    
	    
	    group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    	public void onCheckedChanged(RadioGroup group, int onCheckedId)
	    	{	
	    		coursePlayed = listener.getCoursePlayed();
	    		
	    		System.out.println(R.id.teebox1);
	    		System.out.println(R.id.tee_box_group_round_entry);
	    		System.out.println(onCheckedId);
	    		
	    		switch (onCheckedId)
	    		{
	    		
	    		case R.id.teebox1: teePlayed = coursePlayed.getTeeBoxes()[0]; return;
	    		case R.id.teebox2: teePlayed = coursePlayed.getTeeBoxes()[1]; return;
	    		case R.id.teebox3: teePlayed = coursePlayed.getTeeBoxes()[2]; return;
	    		case R.id.teebox4: teePlayed = coursePlayed.getTeeBoxes()[3]; return;
	    		case R.id.teebox5: teePlayed = coursePlayed.getTeeBoxes()[4]; return;
	    		case R.id.teebox6: teePlayed = coursePlayed.getTeeBoxes()[5]; return;
	    		case R.id.teebox7: teePlayed = coursePlayed.getTeeBoxes()[6]; return;
	    		
	    		case R.id.add_new_tee_box:
	    			{
	    				teeName = displayTeeBoxDialog();
	    				//Text on RadioButton is changed in above call based on user input
		    			return;
	    			}
	    			
	    		}
	    		
	    	}
	    });	

		final DatePicker date = (DatePicker) findViewById(R.id.SelectDate);
		
		Button enterRound = (Button) findViewById(R.id.Enter_Round);
		enterRound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            	String courseName =  ((EditText) findViewById(R.id.course_name)).getText().toString();
            	
        	    Course temp = catalog.getCourseIfPlayed(courseName);
            	
                double courseRating = Double.parseDouble(  ((EditText) findViewById(R.id.course_rating)).getText().toString());
                int slopeRating = Integer.parseInt(  ((EditText) findViewById(R.id.slope_rating)).getText().toString());
                int par = Integer.parseInt(  ((EditText) findViewById(R.id.par)).getText().toString());
                int myScore = Integer.parseInt(  ((EditText) findViewById(R.id.your_score)).getText().toString());
                
                //hard coded, should be included in user input
                double yardage = -1;
                
                Date dateOfRound = new Date(date.getDayOfMonth(), date.getMonth(), date.getYear());
                
                //check if playing at new course
                if(temp == null) 
                {
                	temp = new Course(courseName, par, slopeRating, courseRating, teeName, yardage);
                	teePlayed = temp.getTeeBoxes()[0];
                	System.out.println(temp.getTeeBoxNames()[0]);
                }
                
                //check if playing on a new tee-box
                else if(teePlayed == null) 
                {
                	teePlayed = new TeeBox(par, slopeRating, courseRating, yardage, teeName, temp);
                	temp.addTeeBox(teeName, slopeRating, courseRating, yardage);
                }

                Round thisRound = new Round(teePlayed, myScore, dateOfRound);
                catalog.addRound(thisRound);
                Toast.makeText(getApplicationContext(), "Round Added", Toast.LENGTH_SHORT).show();
              
                
                Intent i = new Intent(getApplicationContext(), Handicaddy.class);
                i.putExtra("round_catalog_after_adding", catalog);
                startActivity(i);
            }
        });
		
		Button cancel = (Button) findViewById(R.id.Cancel_round_input);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Handicaddy.class);
				startActivity(i);
			}
		});
	}
	
	private String displayTeeBoxDialog()
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		final EditText input = new EditText(getApplicationContext());
		alertBuilder.setView(input);
		
		alertBuilder.setMessage("Enter the name of the tee box");
		
		alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString().trim();
				((RadioButton)findViewById(R.id.add_new_tee_box)).setText(value); //change text of "add..." radio button
			} });
		
		alertBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		
		alertBuilder.create().show();
		
		return input.toString();
	}
	
	public int monthToDays()
	{
	return 0;	
	}
	
	@Override
	public void onPause()
	{
		writeCatalogToFile();
	}
	
	@Override
	public void onResume()
	{
		readCatalogFromFile();
	}
	
	private void writeCatalogToFile() {
		ObjectOutputStream out;
		File file = new File(getFilesDir(), "RoundCatalog");
		
		if (file.exists()) file.delete(); //unsure if necessary
		
		try {
			file.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(catalog);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readCatalogFromFile() {
		ObjectInputStream in;
		try {
			File file = new File(getFilesDir(), "RoundCatalog");
			if (!file.exists()) file.createNewFile();
			in = new ObjectInputStream(new FileInputStream(file));
			catalog = (RoundCatalog) in.readObject();
			
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			Calendar today = Calendar.getInstance();
			
			System.out.println(new Date(today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.MONTH), today.get(Calendar.YEAR)).getDate() - 200 + ":)");
			if (catalog == null) catalog = new RoundCatalog(new Date(today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.MONTH), today.get(Calendar.YEAR)).getDate() - 200, 1000);
		}

	}
	
}
