package com.summer.handicaddy;

import java.io.Serializable;

public class Hole implements Serializable{
	

	private static final long serialVersionUID = 7530844248992501959L;
	
	private HoleParams params;
	private int score;
	private boolean fairwayHit;
	private double driveDistance;
	private boolean greenHit;
	private int putts;

	public Hole()
	{
		params = new HoleParams();
	}
	
	public void setYardage(double yardage){
		params.setYardage(yardage);
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public void setPar(int par){
		params.setPar(par);
	}
	
	public void setDriveDistance(double distance){
		this.driveDistance = distance;
	}
	
	public void setPutts(int numPutts){
		putts = numPutts;
	}
	
	public void setFairwayHit(boolean fairwayHit){
		this.fairwayHit = fairwayHit;
	}

	public void setGreenHit(boolean greenHit){
		this.greenHit = greenHit;
	}
	
	public int getScore(){
		return score;
	}
	
	public int getPar(){
		return params.getPar();
	}
	
	public double getDriveDistance(){
		return driveDistance;
	}
	
	public int getNumPutts(){
		return putts;
	}
	
	public boolean fairwayHit(){
		return fairwayHit;
	}
	
	public boolean greenHit(){
		return greenHit;
	}
	
	public double getYardage(){
		return params.getYardage();
	}
	
	/*
	@Override
	public String toString()
	{
		String hole = Integer.toString(par) + "-" + Integer.toString(score) + "-" + 
				Integer.toString(fairwayHit) + "-" + Integer.toString(driveDistance) + "-" + 
				Integer.toString(greenHit) + "-" + Integer.toString(putts);
		
		return hole;
	}
	*/
	
	
}
