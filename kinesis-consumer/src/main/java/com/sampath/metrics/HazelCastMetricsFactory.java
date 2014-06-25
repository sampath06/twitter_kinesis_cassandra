package com.sampath.metrics;

import com.amazonaws.services.kinesis.metrics.interfaces.IMetricsFactory;
import com.amazonaws.services.kinesis.metrics.interfaces.IMetricsScope;

public class HazelCastMetricsFactory implements IMetricsFactory {

	
	private String workerId;

	public HazelCastMetricsFactory(String workerId) {
		this.workerId = workerId;
	}

	public IMetricsScope createMetrics() {
		return new HazelCastMetricsScope(workerId);
	}

}
