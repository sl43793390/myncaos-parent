package com.mynacos.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mynacos.annotation.MyNacosReference;
import com.mynacos.annotation.MyNacosService;
import com.mynacos.entity.Apinfo;
import com.mynacos.service.ApiLoadManager;
import com.mynacos.service.MynacosNamingService;
import com.mynacos.service.proxy.MethodImpl;
import com.mynacos.service.proxy.ProxyFactory;
import com.mynacos.service.proxy.ProxyImplHandler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
/**
 * 将指定包下的需要提供服务的接口类全部扫描出来，注册到服务端，方便其它调用者查询
 * 并在最后将带有@mynacosReference的接口注入一个代理对象，调用代理对象时调用对应的远程接口返回
 * @author Administrator
 *
 */
public class ApiLoadManagerImpl implements ApiLoadManager,ApplicationContextAware{

	
	private static final Logger log = LoggerFactory.getLogger(ApiLoadManagerImpl.class);

	private String [] servicePackages;
	private String [] refPackages;
	//需要注册的服务
	private Apinfo serviceProvider;
	//需要调用的服务
	private Apinfo invokeProvider;
	//哪些需要调用远程服务
	private Map<Class<?>, List<Field>> refService = new HashMap<>();
	
	private ApplicationContext applicationContext;
	public String serverUrl;
	
	public String getServerUrl() {
		if (null == serverUrl) {
			serverUrl = SpringUtil.getProperty("mynacos.server.url");
		}
		return serverUrl;
	}
	@Override
	public void onLoad() {
		scanService();
		createProxy();
		registerApi();
	}

	private void scanService() {
		String ip = SystemUtil.getHostInfo().getAddress();
		String port = ResourceBundle.getBundle("application").getString("server.port");
		if (null == servicePackages || servicePackages.length == 0) {
			throw new RuntimeException("servicePackages can not be null");
		}
		List<String> inters = new ArrayList<>();
		for (int i = 0; i < servicePackages.length; i++) {
			String pak = servicePackages[i];
			Set<Class<?>> scanPackage = ClassUtil.scanPackage(pak);
			for (Class<?> class1 : scanPackage) {
				if (!class1.isInterface() && class1.isAnnotationPresent(MyNacosService.class)) {
					inters.add(class1.getInterfaces()[0].getName());
				}
			}
		}
		serviceProvider = new Apinfo();
		serviceProvider.setIp(ip);
		serviceProvider.setPort(Integer.parseInt(port));
		serviceProvider.setApiInterface(inters);
		invokeProvider = new Apinfo();
		invokeProvider.setIp(ip);
		invokeProvider.setPort(Integer.parseInt(port));
		if (null == refPackages || refPackages.length == 0) {
			throw new RuntimeException("refpackages can not be null");
		}
		List<String> invokeInters = new ArrayList<>();
		for (int i = 0; i < refPackages.length; i++) {
			String pak = refPackages[i];
			Set<Class<?>> scanPackage = ClassUtil.scanPackage(pak);
			for (Class<?> class1 : scanPackage) {
				if (!class1.isInterface()) {
					Field[] declaredFields = class1.getDeclaredFields();
					for (Field f : declaredFields) {
						if (f.isAnnotationPresent(MyNacosReference.class)) {
							invokeInters.add(f.getType().getName());
							if (null == refService.get(class1)) {
								List<Field> lists = new ArrayList<>();
								lists.add(f);
								refService.put(class1, lists);
							}else {
								refService.get(class1).add(f);
							}
						}
					}
				}
			}
		}
		invokeProvider.setApiInterface(invokeInters);
	}
	
	@Override
	public void createProxy() {
		Set<Entry<Class<?>,List<Field>>> entrySet = refService.entrySet();
		for (Entry<Class<?>, List<Field>> entry : entrySet) {
			Object bean = this.applicationContext.getBean(entry.getKey());
			List<Field> value = entry.getValue();
			for (Field f : value) {
				Field field;
				try {
					field = bean.getClass().getDeclaredField(f.getName());
					ProxyFactory factory = new ProxyFactory<>(field.getType());
					Map<Method, MethodImpl> mapperImpl = getMapperImpl(field.getType());
					ProxyImplHandler proxyImplHandler = new ProxyImplHandler<>(field.getType(), mapperImpl,invokeProvider);
					Object newInstance = factory.newInstance(proxyImplHandler);
					field.setAccessible(true);
					field.set(bean, newInstance);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private Map<Method, MethodImpl> getMapperImpl(Class<?> interface1) {
		Map<Method, MethodImpl> map = new HashMap<Method, MethodImpl>();
		Method[] methods = interface1.getMethods();
		for (int i = 0; i < methods.length; i++) {
			MethodImpl impl = new MethodImpl<>(interface1, methods[i],invokeProvider);
			map.put(methods[i], impl);
		}
		return map;
	}
	
	@Override
	public boolean registerApi() {
		try {
			MynacosNamingService bean = applicationContext.getBean(MynacosNamingService.class);
			
			boolean registerApiInfo = bean.registerApiInfo(serviceProvider);
			if (registerApiInfo) {
				log.warn("接口注册成功=====");
				return true;
			}
		} catch (Exception e) {
			log.error(ExceptionUtil.getSimpleMessage(e));
			try {
				String serverUrl = getServerUrl()+"/service/regapi";
				String post = HttpUtil.post(serverUrl, JSONUtil.toJsonStr(serviceProvider));
				if (post.equals("ok")) {
					log.warn("接口注册成功=====");
					return true;
				}
			} catch (Exception e1) {
				log.error(ExceptionUtil.getSimpleMessage(e1));
				return false;
			}
			return false;
		}
		return false;
	}

	public String[] getServicePackages() {
		return servicePackages;
	}

	public void setServicePackages(String[] servicePackages) {
		this.servicePackages = servicePackages;
	}

	public String[] getRefPackages() {
		return refPackages;
	}

	public void setRefPackages(String[] refPackages) {
		this.refPackages = refPackages;
	}


	public Apinfo getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(Apinfo serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	
	public Map<Class<?>, List<Field>> getRefService() {
		return refService;
	}

	public void setRefService(Map<Class<?>, List<Field>> refService) {
		this.refService = refService;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
}
