package local.wkkim.dev.configuration.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import local.wkkim.dev.configuration.redis.RedisConfiguration;

@Configuration
public class JacksonMessageConfiguration implements InitializingBean
{
	private static final Logger LOG = LoggerFactory.getLogger(RedisConfiguration.class);
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter()
	{
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		
		return mappingJackson2HttpMessageConverter;
	}
	@Override
	public void afterPropertiesSet() throws Exception 
	{
		LOG.debug("---------------------------------------------");
		LOG.debug("-------- JacksonMessageConfiguration Initialized ... -");
		LOG.debug("---------------------------------------------\n");
	}
	

}
