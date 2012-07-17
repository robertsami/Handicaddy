package com.summer.handicaddy;


import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FilterStats extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter_stats);

		catalog = (RoundCatalog) getIntent().getSerializableExtra("round_catalog");


		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.filter_course_name);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, catalog.getCourseNames());
		textView.setAdapter(adapter);

		final DatePicker startDate = (DatePicker) findViewById(R.id.datePicker1);
		final DatePicker endDate = (DatePicker) findViewById(R.id.datePicker2);
		Button enter = (Button) findViewById(R.id.enter_search_args);
		enter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ArrayList<String> courseNames = null;
				String courseName;
				for (int i = 0, count = group.getChildCount(); i < count; ++i) {
					EditText field = (EditText)group.getChildAt(i);
					courseName =  field.getText().toString();           
					if (courseName != null) {courseNames.add(courseName);}
				}


				Date start = new Date(startDate.getDayOfMonth(), startDate.getMonth(), startDate.getYear());
				Date end = new Date(endDate.getDayOfMonth(), endDate.getMonth(), endDate.getYear());

				Intent i = new Intent(getApplicationContext(), Stats.class);
				i.putExtra("course_names", courseNames);
				i.putExtra("start_date", start);
				i.putExtra("end_date", end);

				startActivity(i);
			}
		});
		Button addCourse = (Button) findViewById(R.id.add_course);
		addCourse.setOnClickListener(new View.OnClickListener() {
			//Class sets the textview for a new course parameter to be visible
			public void onClick(View v) {
				ArrayList<RadioButton> buttons = null;
				for (int i = 0; i < 10; i++){
					RadioButton newButton = new RadioButton(getApplicationContext());
					newButton.setVisibility(2);
					((ViewGroup) v).addView(newButton);
					buttons.add(newButton);
				}
				RadioButton[] buttonArray = (RadioButton[]) buttons.toArray();
				RadioGroupUpdateListener listener = new RadioGroupUpdateListener(radioGroup, courses, buttonArray);
				AutoCompleteTextView field = new AutoCompleteTextView(getApplicationContext());
				field.setVisibility(0);
				((ViewGroup) v).addView(field);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, catalog.getCourseNames());
				field.setAdapter(adapter);

				field.addTextChangedListener(listener);
				/*for (int i = 0, count = group.getChildCount(); i < count; ++i) {
					EditText field = (EditText) group.getChildAt(i);
					if (field.getVisibility() == 2){
						field.setVisibility(0);
						break;
					}
				}*/
				

			}
		});


	}
	public String base = "filter_course_name_";
	int count = 0;
	ArrayList<TeeBox> teeBoxes;
	ViewGroup group = (ViewGroup)findViewById(R.id.course_name_group);
	RadioGroup radioGroup;
	RoundCatalog catalog;
	Course[] courses = catalog.getCourses();
}





/*setListAdapter(new RoundArrayAdapter(this, R.layout.round_header, catalog.getAllRounds()));

		ListView lv = getListView();

		EditText searchBar = (EditText)findViewById(R.id.search_bar);
	    searchBar.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	getListView().setFilterText(s.toString());
	        	if (s.toString().equals("") || s.toString() == null) {
	        		getListView().clearTextFilter();
	        	}
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){

	        }
	    }

	    		); */


