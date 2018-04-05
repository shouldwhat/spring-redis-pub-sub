# spring-redis-pub-sub

* **프로젝트 소개**
```
	*. Redis를 이용하여 Publish/Subscribe 기능 구현.

	*. Restful API을 사용한 기능 테스트.
```
---

* **프로젝트 환경**
```
	*. spring framework 4.3.6
	
	*. apache tomcat 8
	
	*. JDK 1.8
	
	*. spring-data-redis 1.8.0
```
---

* **클래스 설명**
```
	*. RedisController.java : Redis 테스트를 위한 데이터 추가, 조회, publish 기능의 Restful API 제공. 

	*. RedisPublisher.java : 데이터 publishing 작업 수행.
	
	*. RedisSubscriber.java : publishing 작업을 listening 하는 클래스. 
	
	*. RedisServiceImpl.java : 데이터 추가, 조회, publishing 작업에 대한 비지니스 로직 수행.

	*. RedisConfiguration.java : Redis Connection, Subscribe 관련 Bean 설정.
	
	*. JacksonMessageConfiguration.java : http request/response에 대한 josn mapper Bean 설정.
```
---