package com.example.ti.ble.sensortag;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DSPUtility {
	final Double ALPHA = 0.2;
	private List<Double> rawData = new ArrayList<Double>();
	private List<Double> filteredData = new ArrayList<Double>();
	private List<Double> lowFilteredData = new ArrayList<Double>();
	
	public List<Double> getRawData() {
		return rawData;
	}

	public List<Double> getFilteredData() {
		return filteredData;
	}

	
	public double highPassFilter(double value)
	{
		Double f;
		rawData.add(value);
		
		int lastFiltered = filteredData.size();
		int lastRaw = rawData.size();
		if (lastRaw==1)
		{
			filteredData.add(value);
//			lowFilteredData.add(value);
		}
		else 
		{
			//f = ALPHA * lowFilteredData.get(last-1) + (1 - ALPHA) * value;
			f = ALPHA * (filteredData.get(lastFiltered-1) + value - rawData.get(lastRaw-2));
			f = round(f,3);
//			lowFilteredData.add(f);
			filteredData.add(f);
		}
		return filteredData.get(lastFiltered);
	}

	public double magnitude(double x, double y, double z)
	{
		double magnitude = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)+Math.pow(z, 2));
		magnitude = round(magnitude, 3);
		return magnitude;
	}
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
