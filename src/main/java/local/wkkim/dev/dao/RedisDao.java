package local.wkkim.dev.dao;

import local.wkkim.dev.entity.MyPhone;

public interface RedisDao 
{
	public String getString(String key);
	
	public Object getObject(String key);
	
	public void putString(String key, String value);
	
	public void putObject(String key, MyPhone object);
}
