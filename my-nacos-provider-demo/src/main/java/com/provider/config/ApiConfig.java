package com.provider.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.mynacos.client.service.ClientNamingServiceImpl;
import com.mynacos.config.ApiLoadManagerImpl;
import com.mynacos.config.ClientSchedulerService;
import com.mynacos.listener.HeartListerner;
import com.mynacos.service.ApiLoadManager;
import com.mynacos.service.MynacosNamingService;

import cn.hutool.extra.spring.SpringUtil;

@Configuration
public class ApiConfig {

private static final Logger log = LoggerFactory.getLogger(ApiConfig.class);

	/**
	 * 注册向外提供的接口需要的bean；
	 * @return
	 */
	@DependsOn(value = "getHeartListener")
	@Bean
	public ApiLoadManager getApiManager() {
		ApiLoadManagerImpl ma = new ApiLoadManagerImpl();
		ma.setApplicationContext(SpringUtil.getApplicationContext());
		ma.setServicePackages(new String[] {"com.provider.service"});//注册自己向外提供的接口实现类所在的包
		ma.setRefPackages(new String[] {"com.provider.service"});
		try {
			ma.onLoad();
		} catch (Exception e) {
			log.error("注册api服务错误",e.getMessage());
			e.printStackTrace();
		}
		return ma;
	}
	/**
	 * 当服务器失去连接时，不断重新尝试链接所需要的bean
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
	
	@Bean
	public ClientSchedulerService clientSchedulerService(HeartListerner heartListerner) {
		ClientSchedulerService service = new ClientSchedulerService();
		service.setHeartListerner(heartListerner);
		return service;
	}
	/**
	 * mynacos核心客户端服务类，可从此类中获取所有实例信息
	 * @return
	 */
	@Bean 
	public MynacosNamingService mynacosNamingService() {
		ClientNamingServiceImpl client = new ClientNamingServiceImpl();
		return client;
	}
}
