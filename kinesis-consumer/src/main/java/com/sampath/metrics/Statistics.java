package com.sampath.metrics;

import java.util.HashMap;
import java.util.Map;


public class Statistics {

	Map<String, String> dimensions = new HashMap<String, String>();
	Map<String, Double> values = new HashMap<String, Double>();
	public void addDimension(String key, String value) {
		dimensions.put(key, value);
	}

	public void addData(String key, double value) {
		Double current = values.get(key);
	}

}
