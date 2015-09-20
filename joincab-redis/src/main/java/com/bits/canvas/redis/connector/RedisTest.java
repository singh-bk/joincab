package com.bits.canvas.redis.connector;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

public class RedisTest {


	
	public static void main(String[] args) throws Exception{
    	
		RedisTemplate<String, String> redisTemplate = getRedisTemplate();
		RedisTemplate<String, Integer> redisTemplate2 = getRedisTemplate2();
		RedisTemplate<String, Object> redisTemplate3 = getRedisTemplate3();
		
		//String key = "doo";
		//getListValueTest(redisTemplate, "200000115080949");
		//getStringValueTest(redisTemplate, "30edfcf8-18de-4ae5-9347-3f8cec930044");
		//stringValueTest(redisTemplate, "123123213", "123");
		//getIntegerValueTest(redisTemplate2, "d2eb2df3-f797-422b-82c8-a5625171ee40Stat");
		//setStringValueTimeOutTest(redisTemplate, "temp", "tempVal");
		//getStringValueTimeOutTest(redisTemplate, "temp");
		//getListValuePipelinedTest(redisTemplate, new String[]{"200000115080949"});
		 //objectValueTest(redisTemplate3, "obj-123", new Temp(33, "BK"));
		 
		//lockTest2(redisTemplate, redisTemplate3,Arrays.asList(new String[]{"d2eb2df3-f797-422b-82c8-a5625171ee40Stat","abcd-trip"}), Arrays.asList(new String[]{"1","abcd-trip-details"}));
		getListObjectTest(redisTemplate3, "86143103-eef5-42c9-9203-60dc095b713aTrip");
		//System.out.println(scriptTest(redisTemplate, "123123213"));
		/*
    	String expectedValue="booVal";
    	
		
		stringValueTest(redisTemplate, key, expectedValue);
		//listValueTest(redisTemplate, key, value);
		String newValue = "boolVal2";
		//expectedValue = "test";
		System.out.println(lockTest(redisTemplate,key, expectedValue, newValue));
		getStringValueTest(redisTemplate, key);
		List<String> keys = new ArrayList<String>();
		keys.add("f071e585-79d3-4d78-b88e-b7170ca17fe0");
		keys.add("d8d45a6b-118c-4533-8eb4-ecfe9a87c17f");
		keys.add("asdas");
		keys.add("b1749527-1039-459b-83a5-ad15c2c29b91");
		getMultiStringKeyTest(redisTemplate, keys);
		*/
	}
	
	public static boolean lockTest(RedisTemplate<String, String> redisTemplate, 
			String key, String expectedValue, String newValue){
		RedisScript<Boolean> script = getRedisScript();
		boolean added = redisTemplate.execute(script, Collections.singletonList(key),
	            expectedValue, newValue);
		return added;
	}
	
	public static boolean lockTest2(RedisTemplate<String, String> redisTemplate, RedisTemplate<String, Object> redisTemplate3, List<String> keys, List<String> values){
		RedisScript<Boolean> script = getRedisScript();
		
		boolean added = redisTemplate3.execute(script, keys, values);
		getStringValueTest(redisTemplate, keys.get(1));
		return added;
	}
	
	public static int scriptTest(RedisTemplate<String, String> redisTemplate, String key){
		RedisScript<Integer> script = getRedisScript2();
		int added = redisTemplate.execute(script, Collections.singletonList(key));
		return added;
	}
	
	public static void stringValueTest(RedisTemplate<String, String> redisTemplate, String key, String value){
	    redisTemplate.opsForValue().set(key, value);
		String val = redisTemplate.opsForValue().get(key);
	    System.out.println(val);
	}
	
	public static void setStringValueTimeOutTest(RedisTemplate<String, String> redisTemplate, String key, String value){
	    redisTemplate.opsForValue().set(key, value, 1, TimeUnit.MINUTES);
		String val = redisTemplate.opsForValue().get(key);
	    System.out.println(val);
	}
	
	public static void getStringValueTimeOutTest(RedisTemplate<String, String> redisTemplate, String key){
		String val = redisTemplate.opsForValue().get(key);
	    System.out.println(val);
	}
	
	public static void getStringValueTest(RedisTemplate<String, String> redisTemplate, String key){
		String val = redisTemplate.opsForValue().get(key);
	    System.out.println(val);
	}

	public static void setIntegerValueTest(RedisTemplate<String, Integer> redisTemplate, String key, Integer value){
		redisTemplate.opsForValue().set(key, value);
	}
	
	public static void getIntegerValueTest(RedisTemplate<String, Integer> redisTemplate, String key){
		int val = redisTemplate.opsForValue().get(key);
	    System.out.println(val);
	}
	
	public static void getMultiStringKeyTest(RedisTemplate<String, String> redisTemplate, List<String> keys){
		List<String> values = redisTemplate.opsForValue().multiGet(keys);
		for(String str: values){
			System.out.println(str);
		}
	}
	
	public static void listValueTest(RedisTemplate<String, String> redisTemplate, String key, String value){
	    redisTemplate.opsForList().leftPush(key, value);
	    System.out.println(redisTemplate.opsForList().range(key, 0, -1));
	}
	
	public static void getListValueTest(RedisTemplate<String, String> redisTemplate, String key){
	    System.out.println(redisTemplate.opsForList().range(key, 0, -1));
	}
	
	public static void objectValueTest(RedisTemplate<String, Object> redisTemplate, String key, Temp value){
		redisTemplate.opsForValue().set(key, value);
		Temp updated = (Temp)redisTemplate.opsForValue().get(key);
		System.out.println(updated.getName()+","+updated.getAge());
	}
	
	public static void getListObjectTest(RedisTemplate<String, Object> redisTemplate, String key){
	    System.out.println(redisTemplate.opsForList().range(key, 0, -1));
	}
	
	public static void getListValuePipelinedTest(RedisTemplate<String, String> redisTemplate, final String[] keys){
	    redisTemplate.executePipelined(new RedisCallback() {
	    	List<String> list = new ArrayList<String>();
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				for(int i=0;i<keys.length;i++){
					String str;
					try {
						System.out.println(keys[i]);
						str = new String(connection.get(keys[i].getBytes("utf-8")));
						list.add(str);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				System.out.println(list);
				return null;
			}
		});
	}
		
	public static RedisScript<Boolean> getRedisScript() {
		  DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>();
		  redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("checkandset.lua")));
		  redisScript.setResultType(Boolean.class);
		  return redisScript;
		}
	
	public static RedisScript<Integer> getRedisScript2() {
		  DefaultRedisScript<Integer> redisScript = new DefaultRedisScript<Integer>();
		  redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("checkandset2.lua")));
		  redisScript.setResultType(Integer.class);
		  return redisScript;
		}
	
	public static RedisTemplate<String, String> getRedisTemplate() throws Exception{

		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
	    JedisConnectionFactory factory = new JedisConnectionFactory();
	   
	    factory.setHostName("127.0.0.1");
	    factory.setPort(6379);
	    factory.setUsePool(true);
	    factory.setPassword("");
	    factory.afterPropertiesSet();
	    redisTemplate.setConnectionFactory(factory);
	    redisTemplate.afterPropertiesSet();
	    return redisTemplate;
	    
	}

	
	public static RedisTemplate<String, Integer> getRedisTemplate2() throws Exception{

		RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<String, Integer>();
	    JedisConnectionFactory factory = new JedisConnectionFactory();
	   
	    factory.setHostName("127.0.0.1");
	    factory.setPort(6379);
	    factory.setUsePool(true);
	    factory.setPassword("");
	    factory.afterPropertiesSet();
	    redisTemplate.setConnectionFactory(factory);
	    redisTemplate.afterPropertiesSet();
	    return redisTemplate;
	    
	}
	
	public static RedisTemplate<String, Object> getRedisTemplate3() throws Exception{

		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
	    JedisConnectionFactory factory = new JedisConnectionFactory();
	   
	    factory.setHostName("127.0.0.1");
	    factory.setPort(6379);
	    factory.setUsePool(true);
	    factory.setPassword("");
	    factory.afterPropertiesSet();
	    redisTemplate.setConnectionFactory(factory);
	    redisTemplate.afterPropertiesSet();
	    return redisTemplate;
	    
	}

}
