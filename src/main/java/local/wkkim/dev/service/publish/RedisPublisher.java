package local.wkkim.dev.service.publish;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisPublisher
{
	@Autowired
	private RedisTemplate<String, String> defaultRedisTemplate;
	
	@Autowired
	private Properties globalProp;
	
	public void publish(String message) 
	{
		defaultRedisTemplate.convertAndSend(globalProp.getProperty("redis.session.key"), message);
	}

}
