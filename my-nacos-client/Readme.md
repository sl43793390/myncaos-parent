
#### 使用说明
 - 引入依赖，使用@MynacosReferece注入需要的接口
 - 
 
  ```
  <groupId>com.mynacos</groupId>
  <artifactId>my-nacos-client</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  ```
  
 2、在启动类上添加注解扫描
 
 ```
 @SpringBootApplication
@ComponentScan(basePackages = {"com.consumer","com.mynacos"})//必须指定提供服务所在的包，"com.mynacos"不能删除，因为改包有提供服务的接口需要注册 
```

3、添加配置类：

 ```
 
 
	/**
	 * 当服务器失去连接时，不断重新尝试链接所需要的bean
	 * 
	 * @param service
	 * @return
	 */
	@Bean
	public HeartListerner getHeartListener(MynacosNamingService service) {
		HeartListerner lis = new HeartListerner();
		lis.setClientNamingServiceImpl(service);
		lis.onStartUp();
		return lis;
	}

	/**
	 * 注册向外提供的接口需要的bean；
	 * 
	 * @return
	 */
	@DependsOn(value = "getHeartListener")
	@Bean
	public ApiLoadManager getApiManager() {
		ApiLoadManagerImpl ma = new ApiLoadManagerImpl();
		ma.setApplicationContext(SpringUtil.getApplicationContext());
		ma.setServicePackages(new String[] { "com.consumer.service" });// 注册自己向外提供的接口实现类所在的包
		ma.setRefPackages(new String[] { "com.consumer.service" });
		try {
			ma.onLoad();
		} catch (Exception e) {
			log.error("注册api服务错误", e.getMessage());
			e.printStackTrace();
		}
		return ma;
	}
	@Bean
	public ClientSchedulerService clientSchedulerService(HeartListerner heartListerner) {
		ClientSchedulerService service = new ClientSchedulerService();
		service.setHeartListerner(heartListerner);
		return service;
	}

	/**
	 * mynacos核心客户端服务类，可从此类中获取所有实例信息
	 * 
	 * @return
	 */
	@Bean
	public MynacosNamingService mynacosNamingService() {
		ClientNamingServiceImpl client = new ClientNamingServiceImpl();
		return client;
	}
```

4、提供服务的api接口增加注解

 ```
	@Service
	@MyNacosService
	public class TestInterface2Impl implements TestInterface2{
```

5、需要引用接口的字段上添加注解@MyNacosReference

```
	@MyNacosReference
	private TestInterface1 interface1;
```


	











