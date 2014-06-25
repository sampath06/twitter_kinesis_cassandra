package com.sampath.metrics;

import java.util.Arrays;
import java.util.List;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.ISet;

/**
 * Hello world!
 *
 */
public class App 
{
	static String[] allowedArray = { "RecordsProcessed", "DataBytesProcessed", "Time" };

	public static void main( String[] args )
	{
		List<String> allowedList = Arrays.asList(allowedArray);
		HazelcastInstance client = HazelcastClient.newHazelcastClient();
		while (true) {
			// get the workers
			ISet<Object> workers = client.getSet("Workers");
			ISet<Object> names = client.getSet("Names");
			for (Object worker : workers) {
				for (Object name : names) {
					if (!allowedList.contains(name)) {
						continue;
					}

					String entryName = worker + " : " + name;
					IAtomicLong value = client.getAtomicLong(entryName);
					System.out.println(entryName + " : " + value.get());
				}
				System.out.println();
			}
			System.out.println("---------------------------------------------");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}

		}
	}
}
