package com.sampath.kinesis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sampath.twitter.client.KinesisTwitterStatusListener;
import com.sampath.twitter.client.TwitterStreamingClient;

public class App {

	private static final String CONSUMER_KEY = "consumer_key";
	private static final String CONSUMER_SECRET = "consumer_secret";
	private static final String ACCESS_TOKEN = "access_token";
	private static final String ACCESS_SECRET = "access_secret";

	private static String consumerKey;
	private static String consumerSecret;
	private static String accessToken;
	private static String accessSecret;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		parseProperties();
		TwitterStreamingClient client = new TwitterStreamingClient(consumerKey, consumerSecret, accessToken, accessSecret);
		List<String> keyWords = new ArrayList<String>();
		keyWords.add("google");
		client.addKeyWords(keyWords);
		client.setListener(new KinesisTwitterStatusListener());
		client.start();
	}

	private static void parseProperties() throws IOException {
        String userHome = System.getProperty("user.home");
        File twitterDir = new File(userHome, ".twitter");
        File credentialsFile   = new File(twitterDir, "credentials");
		FileInputStream inputStream = new FileInputStream(credentialsFile);
		Properties prop = new Properties();
		prop.load(inputStream);
		consumerKey = prop.getProperty(CONSUMER_KEY);
		consumerSecret = prop.getProperty(CONSUMER_SECRET);
		accessToken = prop.getProperty(ACCESS_TOKEN);
		accessSecret = prop.getProperty(ACCESS_SECRET);
	}

}
