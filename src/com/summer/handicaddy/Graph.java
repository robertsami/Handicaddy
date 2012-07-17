/**
 * 
 */
package com.summer.handicaddy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * @author rsami
 *
 */
public class Graph extends View {
	
	private TeeBox[] boxes; //storage array for tee boxes
	private char attribute; //char storage indicating attribute to be graphed
	private TimeSeries[] series; //storage array for time series of attributes to be graphed
	private RoundCatalog catalog; //catalog..
	private Date startDate; //indicates the start date for the graph, if null all dates before endDate are used
	private Date endDate; //indicates the end date for the graph, if null all dates after startDate are used
	private XYMultipleSeriesDataset dataset; //dataset containing TimeSeries' to be graphed
	private XYMultipleSeriesRenderer renderer; //renderer for dataset
	
	public static char SCORE = 's';
	public static char FAIRWAYS = 'f';
	public static char GREENS = 'g';
	public static char PUTTS = 'p';
	public static char DRIVE = 'd';
	
	public Graph(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		readCatalogFromFile();
	}

	
	/**
	 * Inherited method, called to draw this view type
	 */
	public void onDraw(Canvas canvas)
	{
		generateTimeSeries();
		
		generateData();
		
		GraphicalView graphView = ChartFactory.getTimeChartView(getContext(), dataset, renderer, null);
		
		graphView.draw(canvas);
	}

	public XYMultipleSeriesDataset getDataset() { return dataset; }

	public XYMultipleSeriesRenderer getRenderer() { return renderer; }
	
	/**
	 * Pass an array of tee boxes to graph
	 * @param boxes
	 */
	public void setTeeBoxes(TeeBox[] boxes) { this.boxes = boxes;}
	
	/**
	 * This method allows us to set the attribute using one of the static
	 * char's as input.
	 * @param attr
	 */
	public void setAttribute(char attr)
	{
		attribute = attr;
		/*
		switch(attr)
		{
		case 's' : attribute = "Score"; break;
		case 'f' : attribute = "Fairway"; break;
		case 'g' : attribute = "Green"; break;
		case 'p' : attribute = "Putts"; break;
		case 'd' : attribute = "Drive"; break;
		}
		*/
	}
	
	/**
	 * Generate the time series according to the boxes array
	 */
	private void generateTimeSeries()
	{
		series = new TimeSeries[boxes.length];
		
		TeeBox tempBox; //temporary pointer for use in loop
		Round[] tempRoundHolder; //ditto
		
		for(int i = 0; i < series.length; i++)
		{
			tempBox = boxes[i];
			tempRoundHolder = catalog.getRounds(tempBox, startDate, endDate);
			
			series[i] = new TimeSeries(tempBox.getTeeBoxName());
			
			Round tempRound; //temporary pointer for use in loop
			
			for(int j = 0; j < tempRoundHolder.length; j++)
			{
				tempRound = tempRoundHolder[j];
				
				series[i].add(tempRound.getDate().getJavaDate(), getAttribute(tempRound));
			}
			
		}
	
	}
	
	/**
	 * Create and populate the dataset with the XYSeries in series
	 */
	private void generateData()
	{
		dataset = new XYMultipleSeriesDataset();
		
		for(int i = 0; i < series.length; i++)
			dataset.addSeries(series[i]);
		
		renderer = new XYMultipleSeriesRenderer();
		
	}
	
	
	
	
	
	
	/**
	 * Public method used to set the date range for the graphs
	 * @param start
	 * @param end
	 */
	public void setDateRange(Date start, Date end) { startDate = start; endDate = end; }
	
	/**
	 * Given a round, returns the pre-designated attribute in the form of a double
	 * @param round
	 */
	private double getAttribute(Round round)
	{
		switch(attribute)
		{
		case 's': return round.getScore();
		case 'f': return round.getFwysHit();
		case 'g': return round.getGreensHit();
		case 'p': return round.getPutts();
		case 'd': return round.getAvgDrive();
		default: return 0;
		}
	}
	
	private void readCatalogFromFile() {
		ObjectInputStream in;
		try {
			File file = new File(((Context) getApplicationWindowToken()).getFilesDir(), "RoundCatalog");
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
