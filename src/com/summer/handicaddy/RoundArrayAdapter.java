package com.summer.handicaddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

public class RoundArrayAdapter extends ArrayAdapter<Round> implements ListAdapter, Filterable {
	
	LayoutInflater inflater;
	
	public RoundArrayAdapter(Context context, int textViewResourceId, Round[] rounds) {
		super(context, textViewResourceId, rounds);
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView == null)
			convertView = inflater.inflate(R.layout.round_header, parent, false);
		
		TextView courseName = (TextView) convertView.findViewById(R.id.course_label);
		TextView score = (TextView) convertView.findViewById(R.id.score);
		
		courseName.setText(((Round)this.getItem(position)).getCourse().getName());
		score.setText(((Round)this.getItem(position)).getScore() + "/" + ((Round)this.getItem(position)).getCourse().getPar());
		
		return convertView;
	}
	
	/* I believe this method is what allows us to filter the RoundHistory results
	 */
	public CharSequence convertResultToString(Round round) { return round.getCourse().getName(); } 

}
