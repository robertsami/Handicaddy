package com.summer.handicaddy;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RadioGroupUpdateListener implements TextWatcher {

	Course[] courses;
	RadioButton[] buttons;
	String[] names;
	Course coursePlayed;
	RadioGroup group;
	RadioButton button;
	
	public RadioGroupUpdateListener(RadioGroup group, Course[] courses, RadioButton[] buttons)
	{
		super();
		this.courses = courses;
		this.buttons = buttons;
		this.group = group;
		
		//this.coursePlayed = coursePlayed; // this pointer is passed in the constructor to reference the course played in EnterRound.java
		
		names = new String[courses.length];
		for(int i = 0; i < names.length; i++)
			names[i] = courses[i].getName();
		
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		
		int j = hasCourse(s.toString()); //check what index of the courses array the desired course is at
		
		if(j != -1) //as long as the course exists in courses
		{
			
			TeeBox[] teeBoxes = courses[j].getTeeBoxes(); //get teeBoxes at desired course
			
			for(int i = 0; i < teeBoxes.length; i++) //populate the buttons
			{
				
				buttons[i].setText(teeBoxes[i].getTeeBoxName());
				buttons[i].setVisibility(0);

			}
			
			coursePlayed = courses[j]; //specify which course was played
			
		} else {
			//TODO
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * This method scans names for a match to courseName.
	 * @param courseName
	 * @return index of course if names contains a match, -1 otherwise
	 */
	private int hasCourse(String courseName)
	{
		int hasCourse = -1;
		
		for(int i = 0; i < names.length; i++)
		{
			if(names[i].equals(courseName))
				hasCourse = i;
		}
		
		return hasCourse;
	}
	
	public Course getCoursePlayed() { return coursePlayed; }
	
}
