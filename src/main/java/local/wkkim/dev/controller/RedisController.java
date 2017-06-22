package local.wkkim.dev.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import local.wkkim.dev.service.RedisService;

@Controller
@ResponseBody
public class RedisController 
{
	private static final Logger LOG = LoggerFactory.getLogger(RedisController.class);
	
	@Autowired
	private RedisService redisService;
	
	@RequestMapping(value = "/key/{key}/string", method = RequestMethod.GET)
	public String getString(HttpServletRequest request, @PathVariable String key)
	{
		LOG.debug("---------------- getString = {}", key);
		
		return redisService.getString(key);
	}
	
	@RequestMapping(value = "/key/{key}/object", method = RequestMethod.GET)
	public Object getObject(HttpServletRequest request, @PathVariable String key)
	{
		LOG.debug("---------------- getObject = {}", key);
		
		return redisService.getObject(key);
	}
	
	@RequestMapping(value = "/key/{key}/value/{value}/string", method = RequestMethod.POST)
	public void putString(HttpServletRequest request, @PathVariable String key, @PathVariable String value)
	{
		LOG.debug("---------------- putString = {}, {}", key, value);
		
		redisService.putString(key, value);
	}
	
	@RequestMapping(value = "/key/{key}/value/{value}/object", method = RequestMethod.POST)
	public void putObject(HttpServletRequest request, @PathVariable String key, @PathVariable String value)
	{
		LOG.debug("---------------- putObject = {}, {}", key, value);
		
		redisService.putObject(key, value);
	}
	
	@RequestMapping(value = "/message/{message}/publish", method = RequestMethod.POST)
	public void publishMessage(HttpServletRequest request, @PathVariable String message)
	{
		LOG.debug("---------------- publishMessage = {}", message);
		
		redisService.publish(message);
	}
}
