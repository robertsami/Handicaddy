package com.summer.handicaddy;

import java.io.Serializable;

public class Date implements Serializable{

	private int date;
	
	private int day;
	private int month;
	private int year;
	
	public Date(int day, int month, int year)
	{
		this.day = day; this.month = month; this.year = year;
		
		date = dateToInteger();
	}
	
	
	public Date(java.util.Date time) {
		this.day = time.getDate(); this.month = time.getMonth(); this.year = time.getYear();
		
		date = dateToInteger();
	}

	private int daysInMonth(int month)
	{
		switch (month) {
            case 1:  return 31;
            case 2:
            {
            	if(year % 4 == 0)
            		return 29;
            	else
            		return 28;
            }
            case 3:  return 31;
            case 4:  return 30;
            case 5:  return 31;
            case 6:  return 30;
            case 7:  return 31;
            case 8:  return 31;
            case 9:  return 30;
            case 10: return 31;
            case 11: return 30;
            case 12: return 31;
            default: break;
        }
		
		return -1;
	}
	
	private int dateToInteger()
	{
		int days = day;
		
		for(int i = month - 1; i > 0; i--)
			days += daysInMonth(i);
		
		days += (year - 1) / 4;
		
		days += (year - 1) * 365;
		
		return days;
	}
	
	public int getDate()
	{
		return date;
	}
	
	//Compares this to that, returns -1 if this is earlier, 1 if this is later, and 0 if they are the same
	public int compareDate(Date that)
	{
		int thisDate = this.date;
		int thatDate = that.date;
		
		if (thisDate < thatDate) return -1;
		else if (thisDate > thatDate) return 1;
		else return 0;
	}
	
	/**
	 * Returns a java.util.Date type object, for use with achartengine
	 * @return java.util.Date
	 */
	public java.util.Date getJavaDate()
	{
		return new java.util.Date(this.year, this.month, this.day);
	}
	
}
