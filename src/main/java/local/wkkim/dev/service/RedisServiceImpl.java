package local.wkkim.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.wkkim.dev.dao.RedisDao;
import local.wkkim.dev.entity.MyPhone;
import local.wkkim.dev.service.publish.RedisPublisher;

@Service
public class RedisServiceImpl implements RedisService
{
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private RedisPublisher redisPublisher;
	
	@Override
	public String getString(String key) 
	{
		return redisDao.getString(key);
	}

	@Override
	public Object getObject(String key) 
	{
		return redisDao.getObject(key);
	}

	@Override
	public void putString(String key, String value) 
	{
		redisDao.putString(key, value);
	}

	@Override
	public void putObject(String key, String value) 
	{
		redisDao.putObject(key, new MyPhone(value));
	}

	@Override
	public void publish(String message) 
	{
		redisPublisher.publish(message);
	}

}
