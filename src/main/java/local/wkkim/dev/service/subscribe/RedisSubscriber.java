package local.wkkim.dev.service.subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisSubscriber implements MessageListener 
{
	private static final Logger LOG = LoggerFactory.getLogger(RedisSubscriber.class);
	
	@Override
	public void onMessage(Message message, byte[] pattern)
	{
		LOG.debug("------------------------------ Message Received : {}", message.toString());
	}

}
