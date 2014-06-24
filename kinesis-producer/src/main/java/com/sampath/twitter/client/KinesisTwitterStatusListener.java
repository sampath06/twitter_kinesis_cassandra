package com.sampath.twitter.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.json.DataObjectFactory;

import com.sampath.kinesis.KinesisProducer;
import com.sampath.serializer.Serializer;

public class KinesisTwitterStatusListener implements StatusListener {
	private static final Logger logger = LoggerFactory.getLogger(KinesisTwitterStatusListener.class);
	private KinesisProducer kinesisProducer;

	public KinesisTwitterStatusListener() {
		kinesisProducer = new KinesisProducer();
	}
	@Override
	public void onException(Exception ex) {
		logger.error("Got exception", ex);
	}

	@Override
	public void onStatus(Status status) {
		handleTweet(status);
	}

	protected void handleTweet(Status status) {
		if (logger.isDebugEnabled()) {
			logger.debug("Got status : " + status.getUser().getName() + ":" +
					status.getInReplyToStatusId() + ":" +
					status.getRetweetedStatus() + ":" +
					status.getRetweetCount() + ":" +
					status.getText());
		}
		
		String jsonText = DataObjectFactory.getRawJSON(status);
		kinesisProducer.produce(jsonText);
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		logger.debug("Deleted : " + statusDeletionNotice);
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		logger.debug("Got limitation notice : " + numberOfLimitedStatuses);
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		logger.debug("Scrubbing geo for : " + userId + " upto status : " + upToStatusId);
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		logger.debug("Stalled : " + warning);
	}

}
