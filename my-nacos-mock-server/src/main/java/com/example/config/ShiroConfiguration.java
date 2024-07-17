package com.example.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShiroConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

	/*
	* ehcache 主要的管理器
	*/
//	@Bean(name = "appEhCacheCacheManager")
//	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
//	 return new EhCacheCacheManager (bean.getObject());
//	}
	
	    @Bean
	    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
	        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
	        daap.setBeanName("lifecycleBeanPostProcessor");
	        daap.setProxyTargetClass(true);
	        daap.setUsePrefix(true);
	        return daap;
	    }

	    @Bean(name = "securityManager")
	    public DefaultWebSecurityManager getDefaultWebSecurityManager(ShiroProcess shiroProcess) {
	        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
	        dwsm.setRealm(shiroProcess);
//	      <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 --> 
//	        dwsm.setCacheManager(ehCacheManager());
	        return dwsm;
	    }
	    
//	    @Bean
//	    public DefaultWebSessionManager sessionManager() { // 配置默认的sesssion管理器
//	        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//	        sessionManager.setGlobalSessionTimeout(3600 * 1000);
//	        sessionManager.setSessionDAO(sessionDAO());
//	        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
//	        listeners.add(new MySessionListener());
//	        sessionManager.setSessionListeners(listeners);
//	        return sessionManager;
//	    }

	    @Bean
	    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
	        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
	        aasa.setSecurityManager(securityManager);
	        return aasa;
	    }
	    /**
	     * anon 开放，游客可直接访问
	     * authc 需要身份认证
	     * logout 注销，
	     * 
	     * @return
	     */
//	    @Bean
//	    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
//	        DefaultShiroFilterChainDefinition sfcd = new DefaultShiroFilterChainDefinition();
//
//	        sfcd.addPathDefinition("/","anon");
//	        sfcd.addPathDefinition("/login","anon");
//	        sfcd.addPathDefinition("/login.html","anon");
//	        sfcd.addPathDefinition("/css/**","anon");
//	        sfcd.addPathDefinition("/js/**","anon");
//	        sfcd.addPathDefinition("/images/**","anon");
//	        sfcd.addPathDefinition("/img/**","anon");
//	        sfcd.addPathDefinition("/fonts/**","anon");
//	        sfcd.addPathDefinition("/html/**","anon");
//
//	        sfcd.addPathDefinition("/logout","logout");
//	        sfcd.addPathDefinition("/grid", "authc");
//	        sfcd.addPathDefinition("/hello", "authc");
//	        sfcd.addPathDefinition("/gridpro", "authc");
//	        sfcd.addPathDefinition("/gridpro", "authc");
//	        sfcd.addPathDefinition("/**","user");
//
//	        return sfcd;
//	    }
	    
	    @Bean(name = "shiroFilterFactoryBean")
	    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {

	        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
	        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
	        // 必须设置 SecurityManager  
	        shiroFilterFactoryBean.setSecurityManager(securityManager);
	        // 登录成功后要跳转的连接
	        shiroFilterFactoryBean.setSuccessUrl("/main");
	        shiroFilterFactoryBean.setLoginUrl("/");//登录页面默认为空
	        shiroFilterFactoryBean.setUnauthorizedUrl("/");
//	        shiroFilterFactoryBean.setLoginUrl("/login");
//	        shiroFilterFactoryBean.setUnauthorizedUrl("/login");
	        
	        filterChainDefinitionMap.put("/","anon");
	        filterChainDefinitionMap.put("/service/*","anon");
//	        filterChainDefinitionMap.put("/login","anon");
	        filterChainDefinitionMap.put("/css/**","anon");
	        filterChainDefinitionMap.put("/js/**","anon");
	        filterChainDefinitionMap.put("/images/**","anon");
	        filterChainDefinitionMap.put("/img/**","anon");
	        filterChainDefinitionMap.put("/fonts/**","anon");
	        filterChainDefinitionMap.put("/html/**","anon");
	        filterChainDefinitionMap.put("/VAADIN/**","anon");
	        filterChainDefinitionMap.put("/themes/**","anon");

	        filterChainDefinitionMap.put("/logout","logout");
	        filterChainDefinitionMap.put("/main", "authc");
	        filterChainDefinitionMap.put("/menu", "authc");
	        filterChainDefinitionMap.put("/**","user");
	        
	        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

	        return shiroFilterFactoryBean;
	    }
	    

}
