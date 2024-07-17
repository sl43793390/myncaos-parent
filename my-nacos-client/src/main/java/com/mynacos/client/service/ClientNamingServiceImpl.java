package com.mynacos.client.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mynacos.entity.Apinfo;
import com.mynacos.entity.Instance;
import com.mynacos.entity.InvokeInfo;
import com.mynacos.exception.MyNacosException;
import com.mynacos.service.MynacosNamingService;
import com.mynacos.util.Constants;
import com.mynacos.util.JacksonUtils;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;

/**
 * 客户端用于保存和处理服务的实现类
 * @author Administrator
 *
 */
public class ClientNamingServiceImpl implements MynacosNamingService{

	private static final Logger log = LoggerFactory.getLogger(MynacosNamingService.class);

	// namespace@@groupId = list<instance>
	public static ConcurrentHashMap<String, Set<Instance>> servicesMap = new ConcurrentHashMap<>();
	//ip@@port = instance
	public static ConcurrentHashMap<String, Instance> servicesIpMap = new ConcurrentHashMap<>();
	
	public static volatile ConcurrentHashMap<String, Set<Instance>> serviceInterfaceMapping = new ConcurrentHashMap<>();
	public static String port;
	public String serverUrl;
	
	public String getServerUrl() {
		if (null == serverUrl) {
			serverUrl = SpringUtil.getProperty("mynacos.server.url");
		}
		return serverUrl;
	}
	/**
	 * 向服务端发起注册
	 * @return
	 */
	public boolean registerInstance(Instance ins) {
		try {
			if (null == port) {
				port = SpringUtil.getProperty("server.port");
			}
		} catch (Exception e1) {
			log.error(ExceptionUtil.getSimpleMessage(e1));
			log.error("获取本机端口失败：application.properties文件中未配置server.port");
		}
		try {
			String serverUrl = getServerUrl()+"/service/reg";
			System.out.println(serverUrl);
			String post = HttpUtil.post(serverUrl, JSONUtil.toJsonStr(ins));
			if (post.equals("ok")) {
				log.warn("服务注册成功=====");
				return true;
			}
		} catch (Exception e) {
			log.error(ExceptionUtil.getSimpleMessage(e));
			return false;
		}
		return false;
	}
	
	public static void updateInstances(List<Instance> instances) {
		servicesMap.clear();
		servicesIpMap.clear();
		String ip = SystemUtil.getHostInfo().getAddress();
		
		for (Instance instance : instances) {
			if ((ip+port).equals(instance.getIp()+instance.getPort())) {
				continue;
			}
			servicesIpMap.put(instance.getIp()+Constants.SPLITER+instance.getPort(), instance);
			if (null == servicesMap.get(instance.getNameSpace()+Constants.SPLITER+instance.getGroupName())) {
				Set<Instance> set = new HashSet<Instance>();
				set.add(instance);
				servicesMap.put(instance.getNameSpace()+Constants.SPLITER+instance.getGroupName(), set);
			}else {
				servicesMap.get(instance.getNameSpace()+Constants.SPLITER+instance.getGroupName()).add(instance);
			}
		}
		log.warn("服务刷新成功=====");
	}
	
	public static void removeService(Instance instance) {
		Set<Instance> set = servicesMap.get(instance.getNameSpace()+Constants.SPLITER+instance.getGroupName());
		set.remove(instance);
		servicesIpMap.remove(instance.getNameSpace()+Constants.SPLITER+instance.getGroupName());
	}
	@Override
	public boolean registerInstance(String serviceName, String ip, int port) throws MyNacosException {
		return registerInstance(serviceName, Constants.DEFAULT_GROUPID, ip, port);
	}
	@Override
	public boolean registerInstance(String serviceName, String groupName, String ip, int port) throws MyNacosException {
		Instance ins = new Instance();
		ins.setNameSpace(Constants.DEFAULT_NAMESPACE);
		ins.setGroupName(groupName);
		ins.setServiceName(serviceName);
		ins.setIp(ip);
		ins.setPort(port);
		registerInstance(ins);
		return true;
	}
	@Override
	public boolean registerInstance(String namespace, String serviceName, String groupName, String ip, int port)
			throws MyNacosException {
		Instance ins = new Instance();
		ins.setNameSpace(namespace);
		ins.setGroupName(groupName);
		ins.setServiceName(serviceName);
		ins.setIp(ip);
		ins.setPort(port);
		registerInstance(ins);
		return true;
	}
	/**
	 * 向服务端发起取消注册
	 * @return
	 */
	@Override
	public boolean deRegisterInstance(Instance ins) throws MyNacosException {
		try {
			String serverUrl = getServerUrl()+"/service/dereg";
			String post = HttpUtil.post(serverUrl, JSONUtil.toJsonStr(ins));
			if (post.equals("ok")) {
				return true;
			}
		} catch (Exception e) {
			log.error(ExceptionUtil.getSimpleMessage(e));
			return false;
		}
		return false;
	}
	@Override
	public boolean deRegisterInstance(String serviceName, String ip, int port) throws MyNacosException {
		Instance ins = new Instance();
		ins.setNameSpace(Constants.DEFAULT_NAMESPACE);
		ins.setGroupName(Constants.DEFAULT_GROUPID);
		ins.setServiceName(serviceName);
		ins.setIp(ip);
		ins.setPort(port);
		deRegisterInstance(ins);
		return true;
	}
	@Override
	public boolean deRegisterInstance(String serviceName, String groupName, String ip, int port) throws MyNacosException {
		Instance ins = new Instance();
		ins.setNameSpace(Constants.DEFAULT_NAMESPACE);
		ins.setGroupName(groupName);
		ins.setServiceName(serviceName);
		ins.setIp(ip);
		ins.setPort(port);
		deRegisterInstance(ins);
		return true;
	}
	@Override
	public boolean deRegisterInstance(String namespace, String serviceName, String groupName, String ip, int port)
			throws MyNacosException {
		Instance ins = new Instance();
		ins.setNameSpace(namespace);
		ins.setGroupName(groupName);
		ins.setServiceName(serviceName);
		ins.setIp(ip);
		ins.setPort(port);
		deRegisterInstance(ins);
		return true;
	}
	@Override
	public List<Instance> getAllInstance() throws MyNacosException {
		ArrayList<Instance> list = new ArrayList<Instance>();
		servicesMap.values().forEach(e -> list.addAll(e));
		return list;
	}
	@Override
	public Instance getInstance(String ip, String port) throws MyNacosException {
		return servicesIpMap.get(ip+Constants.SPLITER+port);
	}

	@Override
	public List<Instance> getInstanceByNameSpace(String namespace) throws MyNacosException {
		ArrayList<Instance> list = new ArrayList<Instance>();
		list.addAll(servicesIpMap.values().stream().filter(e -> e.getNameSpace().equals(namespace)).collect(Collectors.toList()));
		return list;
	}

	@Override
	public List<Instance> getInstanceByNameAndGroup(String namespace, String groupId) throws MyNacosException {
		ArrayList<Instance> list = new ArrayList<Instance>();
		list.addAll(servicesIpMap.values().stream().filter(e -> e.getNameSpace().equals(namespace) && e.getGroupName().equals(groupId)).collect(Collectors.toList()));
		return list;
	}

	@Override
	public boolean registerApiInfo(Apinfo ins) throws MyNacosException {
		try {
			String serverUrl = getServerUrl()+"/service/regapi";
			String post = HttpUtil.post(serverUrl, JSONUtil.toJsonStr(ins));
			if (post.equals("ok")) {
				return true;
			}
		} catch (Exception e) {
			log.error(ExceptionUtil.getSimpleMessage(e));
			return false;
		}
		return false;
	}

	@Override
	public boolean registerApiInfo(String serviceName, String ip, int port) throws MyNacosException {
		Apinfo info = new Apinfo();
		info.setNameSpace(Constants.DEFAULT_NAMESPACE);
		info.setGroupName(Constants.DEFAULT_GROUPID);
		info.setServiceName(serviceName);
		info.setIp(ip);
		info.setPort(port);
		registerApiInfo(info);
		return true;
	}

	@Override
	public boolean registerApiInfo(String serviceName, String groupName, String ip, int port) throws MyNacosException {
		Apinfo info = new Apinfo();
		info.setNameSpace(Constants.DEFAULT_NAMESPACE);
		info.setGroupName(groupName);
		info.setServiceName(serviceName);
		info.setIp(ip);
		info.setPort(port);
		registerApiInfo(info);
		return true;
	}

	@Override
	public boolean registerApiInfo(String namespace, String serviceName, String groupName, String ip, int port)
			throws MyNacosException {
		Apinfo info = new Apinfo();
		info.setNameSpace(namespace);
		info.setGroupName(groupName);
		info.setServiceName(serviceName);
		info.setIp(ip);
		info.setPort(port);
		registerApiInfo(info);
		return true;
	}

	@Override
	public List<Instance> getApiInfo(String interFaceName) throws MyNacosException {
		try {
			if (null == port) {
				port = SpringUtil.getProperty("server.port");
			}
		} catch (Exception e1) {
			log.error(ExceptionUtil.getSimpleMessage(e1));
			log.error("获取本机端口失败：application.properties文件中未配置server.port");
		}
		try {
			String serverUrl = getServerUrl()+"/service/getapi";
			System.out.println(serverUrl);
			String post = HttpUtil.post(serverUrl, JSONUtil.toJsonStr(interFaceName));
			TypeReference<List<Instance>> ref = new TypeReference<List<Instance>>() {};
			List<Instance> bean = JSONUtil.toBean(post, ref, true);
			return bean;
			
		} catch (Exception e) {
			log.error(ExceptionUtil.getSimpleMessage(e));
			return null;
		}
	}

	@Override
	public List<Instance> getApiInfo(Apinfo info) throws MyNacosException {
		List<Instance> result = new ArrayList<>();
		List<String> apiInterface = info.getApiInterface();
		for (String string : apiInterface) {//优先从缓存中获取
			if (null != serviceInterfaceMapping.get(string) && !serviceInterfaceMapping.get(string).isEmpty()) {
				result.addAll(serviceInterfaceMapping.get(string));
			}else {
				try {
					String serverUrl = getServerUrl()+"/service/getapi";
					System.out.println(serverUrl);
					String post = HttpUtil.post(serverUrl, JSONUtil.toJsonStr(info));
					TypeReference<List<Instance>> ref = new TypeReference<List<Instance>>() {};
					List<Instance> bean = JSONUtil.toBean(post, ref, true);
					return bean;
					
				} catch (Exception e) {
					log.error(ExceptionUtil.getSimpleMessage(e));
					return null;
				}
			}
		}
		return result;
	}

	/**
	 * 根据接口信息调用具体的实现类
	 * @param invokeInfo
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public Object invokeService(InvokeInfo invokeInfo) throws ClassNotFoundException {
		Object bean = null;
		try {
			bean = SpringUtil.getBean(Class.forName(invokeInfo.getInterfaceClass()));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		if (null == bean) {
			return null;
		}
		Method[] declaredMethods = bean.getClass().getDeclaredMethods();
		for (Method method : declaredMethods) {
			if (method.getName().equals(invokeInfo.getMethodName())) {
				try {
					//将参数进行类型匹配
					Object[] args = new Object[invokeInfo.getArgslist().size()];
					int count = 0;
					Set<Entry<String,Object>> entrySet = invokeInfo.getArgslist().entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String type = entry.getKey();
						Class<?> forName = Class.forName(type);
						Object value = entry.getValue();
						JavaType constructJavaType = JacksonUtils.constructJavaType(forName);
						args[count] = JacksonUtils.mapper.readValue(value.toString(), constructJavaType);
						count ++;
					}
					return method.invoke(bean, args);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	 private static boolean isNeedlessConvert(Class<?> c){    
	        if(c.isPrimitive()){//基本类型    
	            return true;    
	        }    
	        for(Class<?> tmp:Constants.needlessCloneClasses){//是否在无需复制类型数组里    
	            if(c.equals(tmp)){    
	                return true;    
	            }    
	        }    
	        return false;    
	    }    
}
