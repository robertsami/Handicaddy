/**
 * 
 */
package com.summer.handicaddy.chart;


import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;

import com.summer.handicaddy.*;

/**
 * @author rsami
 *
 */
public class ScoreChart extends GolfChart {

	private List<TeeBox[]> teeBoxes; //array of rounds to graph scores of
	private String[] titles; //array of titles of the rounds, parallel to rounds
	private RoundCatalog catalog;
	private Date startDate;
	private Date endDate;
	
	private List<Round[]> rounds; //list of round arrays parallel to teeBoxes
	
	public ScoreChart(List<TeeBox[]> teeBoxes, String[] titles, RoundCatalog catalog) {
		this.teeBoxes = teeBoxes;
		this.titles = titles;
		this.catalog = catalog;
		
		rounds = new ArrayList<Round[]>();
		
		for(int i = 0; i < teeBoxes.size(); i++)
			rounds.add(catalog.getRounds(teeBoxes.get(i)));
		
	}
	
	/* (non-Javadoc)
	 * @see com.summer.handicaddy.chart.IChart#getTeeBox()
	 */
	@Override
	public List<TeeBox[]> getTeeBoxes() {
		// TODO Auto-generated method stub
		return teeBoxes;
	}

	/* (non-Javadoc)
	 * @see com.summer.handicaddy.chart.IChart#getDesc()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Chart of scores between " + startDate + 
				" and " + endDate + ".";
	}

	/* (non-Javadoc)
	 * @see com.summer.handicaddy.chart.IChart#getAttribute()
	 */
	@Override
	public char getAttribute() {
		// TODO Auto-generated method stub
		return 's';
	}

	/* (non-Javadoc)
	 * @see com.summer.handicaddy.chart.IChart#execute(android.content.Context)
	 */
	@Override
	public Intent execute(Context context) {
		
		int numSeries = teeBoxes.size();
		
	    List<java.util.Date[]> dates = new ArrayList<java.util.Date[]>();
	    List<double[]> values = new ArrayList<double[]>();
	    
	    Round[] temp;
	    
	    java.util.Date[] tempDates;
	    double[] tempScores;
	    
	    int[] tempColors = new int[numSeries];
	    PointStyle[] styles = new PointStyle[numSeries];
	    
	    for(int i = 0; i < numSeries; i++)
	    {
	    	temp = rounds.get(i);
	    	
	    	tempDates = new java.util.Date[temp.length];
	    	tempScores = new double[temp.length];
	    	
	    	tempColors[i] = colors[i];
	    	styles[i] = PointStyle.POINT;
	    	
	    	for(int j = 0; j < temp.length; j++)
	    	{
	    		tempDates[j] = temp[j].getJavaDate();
	    		tempScores[j] = temp[j].getScore();
	    	}
	    	
	    	dates.add(tempDates);
	    	values.add(tempScores);
	    	
	    }
	    
	    XYMultipleSeriesRenderer renderer = buildRenderer(tempColors, styles);
	    
	    return ChartFactory.getTimeChartIntent(context, buildDateDataset(titles, dates, values), renderer, getTitle());
	}

	
	private String[] getSeriesTitles()
	{
		String[] s = new String[teeBoxes.size()];
		
		TeeBox[] temp;
		
		for(int i = 0; i < s.length; i++)
		{
			temp = teeBoxes.get(i);
			
			if(temp.length == 1)
				s[i] = temp[0].toString();
			else
				s[i] = temp[0].getCourse().getName() + ", " + temp[0].getTeeBoxName();
			
			for(int j = 1; j < temp.length - 1; j++)
			{
				s[i] = s[i] + ", " + temp[j];
			}
			
			s[i] = s[i] + " and " + temp[temp.length - 1].getTeeBoxName();
		}
		
		return s;
	}
	
}
