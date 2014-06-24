package com.sampath.kinesis;

import java.nio.ByteBuffer;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;

public class KinesisProducer {

	/*
	 * Before running the code:
	 *      Fill in your AWS access credentials in the provided credentials
	 *      file template, and be sure to move the file to the default location
	 *      (~/.aws/credentials) where the sample code will load the
	 *      credentials from.
	 *      https://console.aws.amazon.com/iam/home?#security_credential
	 *
	 * WANRNING:
	 *      To avoid accidental leakage of your credentials, DO NOT keep
	 *      the credentials file in your source directory.
	 */

	final String myStreamName = "streaming_poc";
	static AmazonKinesisClient kinesisClient;
	private static final Logger LOG = LoggerFactory.getLogger(KinesisProducer.class);
	private static Random random = new Random();

	public KinesisProducer() {

		/*
		 * The ProfileCredentialsProvider will return your [default]
		 * credential profile by reading from the credentials file located at
		 * (~/.aws/credentials).
		 */
		AWSCredentials credentials = null;
		try {
			credentials = new ProfileCredentialsProvider().getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. " +
							"Please make sure that your credentials file is at the correct " +
							"location (~/.aws/credentials), and is in valid format.",
							e);
		}

		kinesisClient = new AmazonKinesisClient(credentials);
	}
	
	public PutRecordResult produce(String stream) {
		LOG.info("Putting records in stream : " + myStreamName);
			PutRecordRequest putRecordRequest = new PutRecordRequest();
			putRecordRequest.setStreamName(myStreamName);
			putRecordRequest.setData(ByteBuffer.wrap(stream.getBytes()));
			putRecordRequest.setPartitionKey("partition" + random.nextLong());
			PutRecordResult putRecordResult = kinesisClient.putRecord(putRecordRequest);
			System.out.println("Successfully putrecord, partition key : " + putRecordRequest.getPartitionKey()
					+ ", ShardID : " + putRecordResult.getShardId());

			return putRecordResult;
	}
}

