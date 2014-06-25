package com.sampath.metrics;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.kinesis.metrics.interfaces.IMetricsScope;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IList;
import com.hazelcast.core.ISet;

public class HazelCastMetricsScope implements IMetricsScope {

	
	private static HazelcastInstance server;
	private static HazelcastInstance client;
	private String workerId;
	static {
		server = Hazelcast.newHazelcastInstance();
		client = HazelcastClient.newHazelcastClient();
	}

	public HazelCastMetricsScope(String workerId) {
		this.workerId = workerId;
		ISet<Object> workerList = client.getSet("Workers");
		workerList.add(workerId);
	}

	public void addData(String name, double value, StandardUnit unit) {
		ISet<Object> names = client.getSet("Names");
		names.add(name);
		IAtomicLong entry = client.getAtomicLong(getName(name));
		entry.getAndAdd(new Double(value).longValue());
	}

	private String getName(String name) {
		return workerId + " : " + name;
	}

	public void addDimension(String name, String value) {

	}

	public void end() {
		// TODO Auto-generated method stub

	}

}
