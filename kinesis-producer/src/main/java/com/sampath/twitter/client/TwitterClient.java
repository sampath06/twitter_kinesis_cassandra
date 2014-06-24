package com.sampath.twitter.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClient {
	private static final Logger logger = LoggerFactory.getLogger(TwitterClient.class);
	enum Parameters {
		GEOCODE,
		LANG,
		LOCALE,
		RESULT_TYPE,
		COUNT,
		UNTIL,
		SINCE_ID,
		MAX_ID,
		INCLUDE_ENTITIES, 
		SINCE
	}

	protected Configuration conf;
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
	public TwitterClient(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
		this.conf = getConfiguration(consumerKey, consumerSecret, accessToken, accessSecret);
		logger.debug("Setting up configuration " + conf + " with parameters : " 
						+ consumerKey + " : "
						+ consumerSecret + " : "
						+ accessToken + " : "
						+ accessSecret);
	}
	
	public TwitterClient(ConfigurationBuilder configurationBuilder) {
		this.conf = configurationBuilder.build();
		logger.debug("Setting up configuration " + conf + " with parameters : " + configurationBuilder);
	}
	
	private Configuration getConfiguration(String consumerKey, String consumerSecret,
			String accessToken, String accessSecret) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		if (logger.isDebugEnabled()) {
					
			cb.setDebugEnabled(true);
		}
		cb.setUseSSL(true);
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(accessToken);
		cb.setOAuthAccessTokenSecret(accessSecret);
		cb.setJSONStoreEnabled(true);
		return cb.build();
	}



}
