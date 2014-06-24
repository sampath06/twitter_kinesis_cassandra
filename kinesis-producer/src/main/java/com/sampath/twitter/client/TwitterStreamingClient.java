package com.sampath.twitter.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterStreamingClient extends TwitterClient {
	private static final Logger logger = LoggerFactory.getLogger(TwitterStreamingClient.class);

	private TwitterStream twitterStream;
	private List<String> keywords = new ArrayList<String>();
	private List<Long> userIds = new ArrayList<Long>();


	/**
	 * This version of the client uses the application-only authentication. This is described in
	 * https://dev.twitter.com/docs/auth/tokens-devtwittercom
	 * Here the accessToken and accessSecret are generated from the application panel of the twitter UI.
	 * This allows the client to make API calls on behalf of the application where no user authorization
	 * is needed
	 * 
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessSecret
	 */
	public TwitterStreamingClient(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
		super(consumerKey, consumerSecret, accessToken, accessSecret);
		TwitterStreamFactory tf = new TwitterStreamFactory(this.conf);
		this.twitterStream = tf.getInstance();
	}


	public void setListener(StatusListener listener) {
    	 twitterStream.addListener(listener);
	}

	public void addKeyWords(List<String> keywords) {
		this.keywords.addAll(keywords);
	}

	public void addUserIds(List<Long> userIds) {
		this.userIds.addAll(userIds);
	}

	public void start() {
		
		long[] userIds =getUserIds();
		FilterQuery query = new FilterQuery(0, 
				userIds,
				keywords.toArray(new String[keywords.size()]));
		this.twitterStream.filter(query);
	}

	private long[] getUserIds() {
		long[] result = new long[userIds.size()];
		int index = 0;
		for (Long userId : userIds) {
			result[index] = userId;
			index++;
		}
		return result;
	}

	public void stop() {
		logger.debug("Shutting down streaming api");
		this.twitterStream.shutdown();
		
	}
}
