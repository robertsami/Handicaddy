package com.summer.handicaddy;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable
{
    
	private static final long serialVersionUID = 7861253707771357606L;
	private String name;
    private int par;
    private ArrayList<TeeBox> tees;
    
    public Course(String name, int par)
    {
        this.name = name;
        this.par = par;
        tees = new ArrayList<TeeBox>();
        //tees.add(new TeeBox(-1, -1, -1, "main", name));
    }
    
    public Course(String name, int par, int slope, double courseRating, String teeName, double yardage)
    {
        this.name = name;
        this.par = par;
        tees = new ArrayList<TeeBox>();
        tees.add(new TeeBox(par, slope, courseRating, yardage, teeName, this));
    }
    
    public String getName() { 
    	if(this == null)
    		return "";
    	return name; 
    }
    
    public int getPar() { return par; }
    
    public boolean equals(Course course)
    {
    	return false;
    }
    
    public void addTeeBox(String teeName, int slope, double courseRating, double yardage)
    {
    	tees.add(new TeeBox(par, slope, courseRating, yardage, teeName, this));
    }
    
    public String[] getTeeBoxNames()
    {
    	String[] teeBoxes = new String[tees.size()];
    	
    	for(int i = 0; i < teeBoxes.length; i++)
    		teeBoxes[i] = tees.get(i).getTeeBoxName();
    	
    	return teeBoxes;
    }

    public TeeBox[] getTeeBoxes()
    {
    	TeeBox[] teeBoxes = new TeeBox[tees.size()];
    	return tees.toArray(teeBoxes);
    }

    
}