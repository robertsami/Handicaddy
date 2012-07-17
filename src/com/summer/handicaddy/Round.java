package com.summer.handicaddy;

import java.io.Serializable;

public class Round implements Serializable
{
	
	private static final long serialVersionUID = 1L;
    
    private Course course;
    private TeeBox teeBox;
    private int score;
    private int putts;
    private int greens;
    private int fairways;
    private Date date;

    
    public Round() { super(); }
    
    public Round(TeeBox teeBox, int score, Date date)
    {
    	this.course = teeBox.getCourse();
    	this.teeBox = teeBox;
    	this.score = score;
    	this.date = date;
    	
    	this.putts   = -1;
        this.greens  = -1;
        this.fairways = -1;
    }
    
    public Round(TeeBox teeBox, int score, Date date, boolean fullRound)
    {
    	this.course = teeBox.getCourse();
    	this.teeBox = teeBox;
    	this.score = score;
    	this.date = date;
    	
        if(fullRound)
        	holes = new Hole[18];
        else
        	holes = new Hole[9];
        
        this.putts   = -1;
        this.greens  = -1;
        this.fairways = -1;
    }
    
    public Round(TeeBox teeBox, int score, Date date, int putts, int greens, int fairways, boolean fullRound)
    {
        this.course  = teeBox.getCourse();
        this.teeBox = teeBox;
        this.score   = score;
        this.putts   = putts;
        this.greens  = greens;
        this.fairways = fairways;
        holeByHole = true;
        
        if(fullRound)
        	holes = new Hole[18];
        else
        	holes = new Hole[9];
        
        this.date = date;
    }
    
    /**
     * Primary constructor for Round. Takes a TeeBox object, an array of holes, and a date
     * @param teeBox
     * @param holes
     * @param date
     */
    public Round(TeeBox teeBox, Hole[] holes, Date date)
    {
    	this.course = teeBox.getCourse();
    	this.teeBox = teeBox;
    	
    	this.holes = holes;
    	
    	for(int i = 0; i < holes.length; i++)
    	{
    		score += holes[i].getScore();
    		putts += holes[i].getNumPutts();	
    		
    		if(holes[i].fairwayHit()) fairways++;
    		
    		if(holes[i].greenHit()) greens++;
    	}
    	
    	this.date = date;
    }

    
    public TeeBox getTeeBox() { return teeBox; }
    public Course getCourse() { return course; }
    public int getScore() { return score; }
    public int getPutts() { return putts; }
    public int getGreensHit() { return greens; }
    public int getFwysHit() { return fairways; }
    public int getDay() { return date.getDate(); }
    public Date getDate() { return date; }
    public java.util.Date getJavaDate() { return date.getJavaDate(); }
    
    public void setTeeBox(TeeBox teeBox) { this.teeBox = teeBox; }
    public void setCourse(Course course) { this.course = course; }
    public void setScore(int score) { this.score = score; }
    public void setDate(Date date) { this.date = date; }
    public void setPutts(int putts) { this.putts = putts; }
    public void setGreens(int greens) { this.greens = greens; }
    public void setFairways(int fairways) { this.fairways = fairways; }
    
    /*Adds hole to the num - 1 spot in the holes array, overwriting whatever was there
     */
    public void setHole(Hole hole, int num) { holes[num - 1] = hole; }
    
    public Hole getHole(int hole){ return holes[hole - 1]; }
    
    public double getAvgDrive()
    {
    	if(holes == null)
    		return 0;
    	
    	int count = 0;
    	double driveDistance = 0;
    	
    	for(int i = 0; i < holes.length; i++)
    	{	
    		if(holes[i].getDriveDistance() != 0)
    		{
    			count++;
        		driveDistance += holes[i].getDriveDistance();
    		}
    	}
    	
    	return driveDistance / count;
    	
    }
    
    public String toString()
    {
    	String round = "";
    	
    	round += course.getName();
    	round += " ";
    	round += score;
    	round += " ";
    	round += course.getPar();
    	
    	return round;
    }
    
    public boolean holeByHole = false;
    Hole[] holes;
    
}