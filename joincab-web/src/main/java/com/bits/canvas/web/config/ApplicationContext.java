package com.bits.canvas.web.config;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author vatsritu
 */
@Configuration
@ComponentScan(basePackages = {
        "com.bits.canvas"
})
@Import({WebAppContext.class, PersistenceContext.class, SecurityContext.class})
@PropertySource("classpath:application.properties")
public class ApplicationContext {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";
	protected static final String PROPERTY_NAME_REDIS_HOST = "redis.host";
	protected static final String PROPERTY_NAME_REDIS_PORT = "redis.port";
	
	@Resource
	private Environment env2;
	
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean
    public RedisTemplate<String, String> redisTemplate() {
    	RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
	    JedisConnectionFactory factory = getJedisConnFactory();
	    redisTemplate.setConnectionFactory(factory);
	    redisTemplate.afterPropertiesSet();
	    return redisTemplate;
    }
    
    @Bean
    public RedisTemplate<String, Integer> redisTemplate2() {
    	RedisTemplate<String, Integer> redisTemplate2 = new RedisTemplate<String, Integer>();
	    JedisConnectionFactory factory = getJedisConnFactory();
	    redisTemplate2.setConnectionFactory(factory);
	    redisTemplate2.afterPropertiesSet();
	    return redisTemplate2;
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate3() {
    	RedisTemplate<String, Object> redisTemplate3 = new RedisTemplate<String, Object>();
	    JedisConnectionFactory factory = getJedisConnFactory();
	    redisTemplate3.setConnectionFactory(factory);
	    redisTemplate3.afterPropertiesSet();
	    return redisTemplate3;
    }
    
    /*
    @Bean
    public RedisScript<Boolean> getRedisScript() {
      DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<Boolean>();
      redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("META-INF/scripts/checkandset.lua")));
      redisScript.setResultType(Boolean.class);
      return redisScript;
    }
    */
    @Bean
    public JedisConnectionFactory getJedisConnFactory(){
	    JedisConnectionFactory factory = new JedisConnectionFactory();
	    factory.setHostName("localhost");
	    factory.setPort(6379);
	    factory.setUsePool(true);
	    factory.setPassword("");
	    factory.afterPropertiesSet();
	    return factory;
    }
}
