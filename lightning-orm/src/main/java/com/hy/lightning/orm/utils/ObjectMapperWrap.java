package com.hy.lightning.orm.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

public class ObjectMapperWrap {
	
	private static Logger logger = Logger.getLogger(ObjectMapperWrap.class);
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static ObjectMapper getObjectMapper() {
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		//设置有属性不能映射成PO时不报错  
		objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		/**
		 * 当值为null是序列化为""
		 */
		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {

			@Override
			public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
					throws IOException, JsonProcessingException {
				jgen.writeString("");
			}
		});
		return objectMapper;
	}
	
	public static String serialize(Object object) {
		String rel = "";
		try {
			rel = getObjectMapper().writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(object, e);
		}
		return rel;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> deserialize(String src) {
		try {
			return getObjectMapper().readValue(src, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(src, e);
		}
		return new HashMap<>();
	}
	
	
	public static void main(String[] arges) {
		Map<String,Object> map = new HashMap<>();
		map.put("id", "hy");
		map.put("age", 12);
		map.put("nickname", null);
		String s = serialize(map);
		System.out.println(s);
		Map<String,Object> _map = deserialize(s);
		System.out.println(_map);
	}
}
