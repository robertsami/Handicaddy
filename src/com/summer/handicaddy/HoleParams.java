/**
 * 
 */
package com.summer.handicaddy;

/**
 * @author rsami
 *
 */
public class HoleParams {

	private int par;
	private double yardage;
	
	public HoleParams(int par, double yardage)
	{
		this.par = par;
		this.yardage = yardage;
	}
	
	public HoleParams()
	{
		super();
	}
	
	public int getPar() { return par; }
	
	public double getYardage() { return yardage; }
	
	public void setPar(int par) { this.par = par; }
	
	public void setYardage(double yardage) { this.yardage = yardage; }
}
