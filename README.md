# spring-redis-pub-sub

-. redis를 이용하여 publish/subscribe 기능 구현.

-. restful API을 사용한 기능 테스트.

-. 프로젝트 환경

	1. spring framework 4.3.6
	
	2. apache tomcat 8
	
	3. JDK 1.8
	
	4. spring-data-redis 1.8.0
	
-. 클래스 설명

	1. RedisController.java : Redis 테스트를 위한 데이터 추가, 조회, publish 기능의 Restful API 제공. 

	1. RedisPublisher.java : 데이터 publishing 작업 수행.
	
	2. RedisSubscriber.java : publishing 작업을 listening 하는 클래스. 
	
	3. RedisServiceImpl.java : 데이터 추가, 조회, publishing 작업에 대한 비지니스 로직 수행.

	4. RedisConfiguration.java : Redis Connection, Subscribe 관련 Bean 설정.
	
	5. JacksonMessageConfiguration.java : http request/response에 대한 josn mapper Bean 설정.