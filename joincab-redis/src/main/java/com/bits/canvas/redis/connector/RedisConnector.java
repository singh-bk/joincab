package com.bits.canvas.redis.connector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisConnector {

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	RedisTemplate<String, Integer> redisTemplate2;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate3;
	
	private static int EXPTIME_HOURS = 20000;//TODO - In production set it to 1.
	
	
	/**
	 * Add a string key-value store
	 * @param key
	 * @param value
	 */
	public void set(String key, String value){
		/*
		 * Expire every key after 2 hours.
		 * TODO - make this algo intelligent at a later date
		 */
		redisTemplate.opsForValue().set(key, value, EXPTIME_HOURS, TimeUnit.HOURS);
	}
	
	/**
	 * Add a string key and an integer value
	 * @param key
	 * @param value
	 */
	public void set(String key, Integer value){
		/*
		 * Expire every key after 2 hours.
		 * TODO - make this algo intelligent at a later date
		 */
		redisTemplate2.opsForValue().set(key, value, EXPTIME_HOURS, TimeUnit.HOURS);
	}
	
	/**
	 * Add a string key and an object value
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value){
		/*
		 * Expire every key after 2 hours.
		 * TODO - make this algo intelligent at a later date
		 */
		redisTemplate3.opsForValue().set(key, value, EXPTIME_HOURS, TimeUnit.HOURS);
	}
	
	/**
	 * get value for a single string key
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		String value = redisTemplate.opsForValue().get(key);
		return value;
	}
	
	public int getIntvalue(String key){
		int value = redisTemplate2.opsForValue().get(key);
		return value;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object getObjectValue(String key){
		return redisTemplate3.opsForValue().get(key);
	}
	
	/**
	 * Get list of values for a list a keys. use the string value
	 * @param keys
	 * @return
	 */
	public List<String> getValueList(List<String> keys){
		List<String> values = new ArrayList<String>();
		values = redisTemplate.opsForValue().multiGet(keys);
		return values;
	}
	
	/**
	 * Get list of values with string key and object value
	 * @param keys
	 * @return
	 */
	public List<Object> getObjectValueList(List<String> keys){
		List<Object> values = new ArrayList<Object>();
		values = redisTemplate3.opsForValue().multiGet(keys);
		return values;
	}
	
	/**
	 * Push a new value to a list of values with the key
	 * @param key
	 * @param value
	 */
	public void addToList(String key, String value){
		redisTemplate.opsForList().rightPush(key, value);
		//Expire the key after two hours
		redisTemplate.expire(key, EXPTIME_HOURS, TimeUnit.HOURS);
	}
	
	/**
	 * Push a new value to a list of values with the key
	 * @param key
	 * @param value
	 */
	public void addToList(String key, Object value){
		redisTemplate3.opsForList().rightPush(key, value);
		//Expire the key after two hours
		redisTemplate3.expire(key, EXPTIME_HOURS, TimeUnit.HOURS);
	}
	
	/**
	 * Get the whole list value for a particular key.
	 * @param key
	 */
	public List<String> getAll(String key){
		List<String> valueList = redisTemplate.opsForList().range(key, 0, -1);
		return valueList;
	}
	
	/**
	 * Get the whole list value for a particular key.
	 * @param key
	 */
	public List<Object> getAllObjects(String key){
		List<Object> objList = redisTemplate3.opsForList().range(key, 0, -1);
		return objList;
	}
}
