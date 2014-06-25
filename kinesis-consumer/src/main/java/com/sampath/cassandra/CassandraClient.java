package com.sampath.cassandra;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.StringUtils;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraClient {
       final String KEYSPACE_NAME = "mykeyspace";
       Session  session;
       public CassandraClient() {
               Cluster cluster = Cluster.builder().addContactPoints("localhost").build();
               session = cluster.connect("mykeyspace");
       }
       
       public void insert(String table, String...keyValuePairs) {
               List<String> columnNames = new ArrayList<String>();
               List<String> values = new ArrayList<String>();
               int index = 0;
               for (String keyValue : keyValuePairs) {
                       if ((index % 2) == 0) {
                               columnNames.add(keyValue);
                       } else {
                               values.add("'"+ keyValue + "'");
                       }
		index++;
               }
               String query = "INSERT INTO " + table + " ("
                               + StringUtils.join(",", columnNames.toArray(new String[columnNames.size()])) 
                               + ") VALUES (" 
                               + StringUtils.join(",", values.toArray(new String[values.size()])) 
                               + ")";
	session.execute(query);
		
	}
	public static void main(String[] args) {
		CassandraClient client = new CassandraClient();
		client.insert("tweets", "tweet_id", "test2", "tweet", "my tweet");
	}

}
