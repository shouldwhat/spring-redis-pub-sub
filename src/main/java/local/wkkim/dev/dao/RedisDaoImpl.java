package local.wkkim.dev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import local.wkkim.dev.entity.MyPhone;

@Repository
public class RedisDaoImpl implements RedisDao
{
	@Autowired
	private RedisTemplate<String, String> defaultRedisTemplate;
	
	@Autowired
	private RedisTemplate<String, Object> objectRedisTemplate;

	@Override
	public String getString(String key) 
	{
		return defaultRedisTemplate.opsForValue().get(key);
	}

	@Override
	public Object getObject(String key) 
	{
		return objectRedisTemplate.opsForValue().get(key);
	}

	@Override
	public void putString(String key, String value) 
	{
		defaultRedisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void putObject(String key, MyPhone object) 
	{
		objectRedisTemplate.opsForValue().set(key, object);
	}

}
