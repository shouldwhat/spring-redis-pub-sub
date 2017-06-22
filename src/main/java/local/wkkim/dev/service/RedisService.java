package local.wkkim.dev.service;

public interface RedisService 
{
	public String getString(String key);
	
	public Object getObject(String key);
	
	public void putString(String key, String value);
	
	public void putObject(String key, String value);
	
	public void publish(String message);
}
