package com.dianping.rotate.admin.utils;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

public final class JsonUtils {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		MAPPER.getDeserializationConfig().set(
				DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	}



	/**
	 * 将对象转换为JSON格式
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */
	public static String toStr(Object model) throws IOException {
		return MAPPER.writeValueAsString(model);
	}

	/**
	 * 将JSON字符串转换为指定类实例
	 * 
	 * @param <T>
	 * @param content
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T fromStr(String content, Class<T> clazz) throws IOException {
		return MAPPER.readValue(content, clazz);
	}

	/**
	 * 将JSON字符串转换为Map
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> fromStrToMap(String content) throws IOException {
		return fromStr(content, Map.class);
	}

}
