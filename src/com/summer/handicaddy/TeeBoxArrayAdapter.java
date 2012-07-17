package com.summer.handicaddy;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

public class TeeBoxArrayAdapter extends ArrayAdapter<TeeBox> implements ListAdapter, Filterable {
	
	LayoutInflater inflater;
	
	public TeeBoxArrayAdapter(Context context, int textViewResourceId, TeeBox[] teeBoxes) {
		super(context, textViewResourceId, teeBoxes);
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null)
			convertView = inflater.inflate(R.layout.tee_box_header, parent, false);
			
		TextView name = (TextView) convertView.findViewById(R.id.course_name_in_header);
		
		name.setText((this.getItem(position)).toString());
		
		return convertView;
	}
	
	/* I believe this method is what allows us to filter the RoundHistory results
	 */
	public CharSequence convertResultToString(Round round) { return round.getCourse().getName(); } 

}