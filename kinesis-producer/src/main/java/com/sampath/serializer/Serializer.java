package com.sampath.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.type.TypeFactory;

import twitter4j.TwitterException;
import twitter4j.json.DataObjectFactory;


public class Serializer {
	private static final Log log = LogFactory.getLog(Serializer.class);

	public static String serialize(Object annotation) {
		try {
			
			log.debug("Serialize Annotation Wrapper Object : " + annotation.getClass());
			ObjectMapper m = new ObjectMapper();
			StringWriter sw = new StringWriter();
			m.setSerializationConfig(m.getSerializationConfig().without(Feature.FAIL_ON_EMPTY_BEANS));
			JsonGenerator jg = m.getJsonFactory().createJsonGenerator(sw);
			m.writeValue(jg, annotation);
			return sw.toString();
		} catch (Exception e) {
			log.error("Serializing Annotation Wrapper ", e);
		}
		return null;
	}


	public static <T> T deSerialize(Class<T> targetClass, String jsonText) {
		try {
			log.debug("DeSerialize Annotation Wrapper Object");
			return new ObjectMapper().readValue(jsonText, targetClass);
		} catch (Exception e) {
			/*
			 * Kludge for twitter status. Twitter status does not work well with
			 * jackson json. It uses abstract classes that need to be specifically
			 * constructed. For now just try creating from twitter. Need a better
			 * way to organize this
			 */
			log.error(jsonText, e);
			try {
				return (T) DataObjectFactory.createStatus(jsonText);
			} catch (TwitterException e1) {
				log.error(jsonText, e1);
			}

		} 
		return null;
	}
	
//	public static SerDeser getSerializer(Class clazz) {
//		for (Class key : serializerMap.keySet()) {
//			try {
//				Class superClass = clazz.getSuperclass();
//				superClass = superClass.getSuperclass();
//				key.asSubclass(clazz);
//			} catch (ClassCastException e) {
//				continue;
//			}
//			return serializerMap.get(key);
//		}
//		return null;
//	}

	public static <T> List<T>  deSerializeArrayList(Class<T> targetClass, String jsonText) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			log.debug("DeSerialize Annotation Wrapper list");
			return objectMapper.readValue(jsonText, TypeFactory.collectionType(ArrayList.class, targetClass));
		} catch (Exception e) {
			log.error("DeSerializing Annotation Wrapper ", e);
		} 
		return null;
	}

	public static <T> T deSerialize(Class<T> clazz, byte[] data) {
		return deSerialize(clazz, new String(data));
	}

	public static <T> T deSerialize(Class<T> targetClass, InputStream is) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(is, targetClass);
	}

}
