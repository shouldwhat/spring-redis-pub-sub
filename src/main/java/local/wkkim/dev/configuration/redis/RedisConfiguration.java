package local.wkkim.dev.configuration.redis;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration implements InitializingBean
{
	private static final Logger LOG = LoggerFactory.getLogger(RedisConfiguration.class);
	
	@Autowired
	private Properties globalProp;
	
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		
		/* 
		 * 최대 커넥션 수를 지정한다.
		 * Redis Server의 .conf 파일에서 maxconnection 옵션을 지정해주어야 한다. 디폴트:10000 
		 */
		poolConfig.setMaxTotal(Integer.parseInt(globalProp.getProperty("redis.max-conn")));

		/* 
		 * true : 커넥션 풀이 가득 찼을 경우 준비된 연결이 도착하기를 기다린다.  
		 * false : 기다리지 않고 NoSuchElementException을 발생시킨다. 
		 */
		poolConfig.setBlockWhenExhausted(true);
		
		/* 커넥션 풀에서 커넥션을 얻어올 때 테스트를 실행한다. */
		poolConfig.setTestOnBorrow(false);
		
		/* 커넥션 풀로 커넥션을 반납할 때 테스트를 실행한다. */
		poolConfig.setTestOnReturn(false);
		
		return poolConfig;
	}
	
	/* Redis Connection Bean */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory()
	{
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		
		jedisConnectionFactory.setHostName(globalProp.getProperty("redis.address"));
		jedisConnectionFactory.setPort(Integer.parseInt(globalProp.getProperty("redis.port")));
		jedisConnectionFactory.setUsePool(true);
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
		
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
		String key = globalProp.getProperty("redis.session.key");
		
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
