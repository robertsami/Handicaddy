package com.summer.handicaddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

import com.summer.handicaddy.chart.ScoreChart;

/**
 * Supports viewing stats for up to ten courses
 * @author rsami
 *
 */
public class Stats extends Activity {

	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);

		catalog = (RoundCatalog) getIntent().getSerializableExtra("round_catalog");
		
		Button filter = (Button) findViewById(R.id.stats_filter_button);
		filter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), FilterStats.class);
				i.putExtra("round_catalog", catalog);
				startActivity(i);
			}
		});
		
	} */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);

		catalog = (RoundCatalog) getIntent().getSerializableExtra("round_catalog");
		
		TeeBox[] temp = catalog.getTeeBoxes();
		
		List<TeeBox[]> boxes = new ArrayList<TeeBox[]>();
		boxes.add(new TeeBox[]{ temp[0] });
		boxes.add(new TeeBox[]{ temp[1] });
		
		String[] titles = { "0", "1" };
		
		ScoreChart chart = new ScoreChart(boxes, titles, catalog);
		
		startActivity(chart.execute(getApplicationContext()));
		
	}
	
	
	public void onResume()
	{
		super.onResume();
		/*
		courses = (ArrayList<Course>) getIntent().getSerializableExtra("course_names");
		startDate = (Date) getIntent().getSerializableExtra("start_date");
		endDate = (Date) getIntent().getSerializableExtra("end_date");
		
		Round[] rounds;
		HashMap<Course, Round[]> courseToRounds = new HashMap<Course, Round[]>();
		for(int i = 0; i < courses.size(); i++)
		{
			rounds = catalog.getRounds(courses.get(i));
			courseToRounds.put(courses.get(i), rounds);
		}
		*/
		
		
		
	}
	
	RoundCatalog catalog;
	ArrayList<Course> courses;
	Date startDate;
	Date endDate;
	
}
	


	
