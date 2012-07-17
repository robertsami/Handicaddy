package com.summer.handicaddy;

import org.achartengine.model.XYSeries;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RoundView extends Activity{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.round_view);
		
		Round toDisplay = (Round) getIntent().getSerializableExtra("round");
		if (toDisplay.holeByHole) {
			Button viewScorecard = new Button(getApplicationContext());
			viewScorecard.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//TODO make a scorecard view
				}
				
			});
			
			RelativeLayout.LayoutParams ourRulez = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			ourRulez.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			ourRulez.addRule(RelativeLayout.CENTER_HORIZONTAL);
			((RelativeLayout) findViewById(R.id.round_view_relative_layout)).addView(viewScorecard, ourRulez);
		}
		
		
		((TextView) findViewById(R.id.round_view_course_name)).setText(toDisplay.getCourse().getName() + " " + toDisplay.getTeeBox().getTeeBoxName());
		
		((TextView) findViewById(R.id.round_view_score)).setText(Integer.toString(toDisplay.getScore()));
		
		((TextView) findViewById(R.id.round_view_fairways_hit)).setText((((toDisplay.getFwysHit() == -1) ? "N/A" : Integer.toString(toDisplay.getFwysHit()))));
		
		((TextView) findViewById(R.id.round_view_greens_hit)).setText((toDisplay.getGreensHit() == -1 ? "N/A" : Integer.toString(toDisplay.getGreensHit())));
		
		((TextView) findViewById(R.id.round_view_putts)).setText((String)(toDisplay.getPutts() == -1 ? "N/A" : Integer.toString(toDisplay.getPutts())));
		
		((TextView) findViewById(R.id.round_view_avg_drive)).setText((String)(toDisplay.getAvgDrive() == 0 ? "N/A" : Double.toString(toDisplay.getAvgDrive())));
		
		//((TextView) findViewById(R.id.round_view_slope_rating)).setText((String)(toDisplay.getCourse().getSlopeRating() == -1 ? "N/A" : Integer.toString(toDisplay.getCourse().getSlopeRating())));
		
		//((TextView) findViewById(R.id.round_view_course_rating)).setText((String) (toDisplay.getCourse().getCourseRating() == -1 ? "N/A" : Double.toString(toDisplay.getCourse().getCourseRating())));
		
	}
	
}
