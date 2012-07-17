/**
 * 
 */
package com.summer.handicaddy.chart;

import java.util.List;

import com.summer.handicaddy.*;

import android.content.Context;
import android.content.Intent;

/**
 * Defines the stat charts.
 */
public interface IChart {
  
  /** A constant for the attribute to be graphed. */
  char attribute  = '-';
  
  /** A constant for the description field in the list activity. */
  String TITLE = "title";

  /**
   * Returns the List<TeeBox[]>.
   * 
   * @return the tee boxes
   */
  List<TeeBox[]> getTeeBoxes();

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  String getTitle();
  
  /**
   * Returns the attribute.
   * 
   * @return the attribute to be graphed
   */
  char getAttribute();
  
  

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  Intent execute(Context context);

}