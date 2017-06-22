package local.wkkim.dev.configuration.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import local.wkkim.dev.service.subscribe.RedisSubscriber;

@Configuration
public class RedisConfiguration implements InitializingBean
{
	private static final Logger LOG = LoggerFactory.getLogger(RedisConfiguration.class);
	
	/* Redis Connection Bean */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory()
	{
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		
		jedisConnectionFactory.setHostName("192.168.1.170");
		jedisConnectionFactory.setPort(6379);
		
		return jedisConnectionFactory;
	}
	
	/* Redis Template Bean */
	@Bean
	public RedisTemplate<String, String> defaultRedisTemplate()
	{
		RedisTemplate<String, String> defaultRedisTemplate = new RedisTemplate<String, String>();
		
		defaultRedisTemplate.setConnectionFactory(jedisConnectionFactory());
		defaultRedisTemplate.setKeySerializer(stringRedisSerializer());
		defaultRedisTemplate.setValueSerializer(stringRedisSerializer());
		
		return defaultRedisTemplate;
	}
	
	/* Redis Template Bean */
	@Bean
	public RedisTemplate<String, Object> objectRedisTemplate()
	{
		RedisTemplate<String, Object> objectRedisTemplate = new RedisTemplate<String, Object>();
		
		objectRedisTemplate.setConnectionFactory(jedisConnectionFactory());
		objectRedisTemplate.setKeySerializer(stringRedisSerializer());
		objectRedisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer());
		
		return objectRedisTemplate;
	}
	
	/* Redis Serializer Bean */
	@Bean
	public StringRedisSerializer stringRedisSerializer()
	{
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		
		return stringRedisSerializer;
	}
	
	/* Redis Serializer Bean */
	@Bean
	public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer()
	{
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(new ObjectMapper());
		
		return genericJackson2JsonRedisSerializer;
	}
	
	/* Redis Listening Container Bean */
	@Bean
	public RedisMessageListenerContainer redisContainer()
	{
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		
		redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory());
		redisMessageListenerContainer.addMessageListener(messageListener(), channelTopic());
		
		return redisMessageListenerContainer;
	}
	
	/* Redis Listener Bean */
	@Bean
	public MessageListenerAdapter messageListener()
	{
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();
		
		messageListenerAdapter.setDelegate(new RedisSubscriber());
		
		return messageListenerAdapter;
	}
	
	/* Redis Publish/Subscribe Channel */
	@Bean
	public ChannelTopic channelTopic()
	{
		String key = "QUEUE";
		
		return new ChannelTopic(key);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception 
	{
		LOG.debug("---------------------------------------------");
		LOG.debug("-------- RedisConfiguration Initialized ... -");
		LOG.debug("---------------------------------------------\n");
	}
	
}
