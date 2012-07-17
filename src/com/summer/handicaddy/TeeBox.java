package com.summer.handicaddy;

import java.io.Serializable;

public class TeeBox implements Serializable{
	

	private static final long serialVersionUID = -2575937602486356914L;
	private int slope;
	private int par;
    private double courseRating;
    private double yardage;
    private String name;
    private Course course;
    private HoleParams[] courseLayout;
    
    public TeeBox() { super(); }
    
    public TeeBox(int par, int slope, double courseRating, double yardage, String name, Course course)
    {
    	this.par = par;
    	this.slope = slope;
    	this.courseRating = courseRating;
    	this.yardage = (yardage != 0 ? yardage : -1);
    	this.name = name;
    	this.course = course;
    	
    	courseLayout = new HoleParams[18];
    }
    
    public String toString()
    {
    	return course.getName() + " " + name;
    }
    
    public int getSlopeRating() { return slope; }
    public double getCourseRating() { return courseRating; }
    public String getTeeBoxName() { return name; }
    public double getYardage() { return yardage; }
    public Course getCourse() { return course; }
    public int getPar() { return par; }
    public HoleParams getHoleParams(int hole) { return courseLayout[hole - 1]; }
    public HoleParams[] getHoleParams() { return courseLayout; }
    
    public void setSlopeRating() { this.slope = slope; }
    public void setCourseRating() { this.courseRating = courseRating; }
    public void setTeeBoxName() { this.name = name; }
    public void setYardage() { this.yardage = yardage; }
    public void setCourse() { this.course = course; }
    public void setPar() { this.par = par; }
    public void setHoleParams(int hole, HoleParams params) { courseLayout[hole - 1] = params; }
    public void setHoleParams(int hole, int par, double yardage) { courseLayout[hole - 1] = new HoleParams(par, yardage); }

}
