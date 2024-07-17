package com.example.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.config.SchedulerService;
import com.example.service.beat.HeartBeatManager;
import com.mynacos.entity.Apinfo;
import com.mynacos.entity.Instance;
import com.mynacos.entity.InvokeInfo;
import com.mynacos.exception.MyNacosException;
import com.mynacos.service.MynacosNamingService;
import com.mynacos.util.Constants;

import cn.hutool.core.collection.ConcurrentHashSet;
import net.sf.ehcache.util.concurrent.ConcurrentHashMap;

@Service
public class MynacosNamingServiceImpl implements MynacosNamingService{

	
	private static final Logger log = LoggerFactory.getLogger(MynacosNamingService.class);

	// namespace@@groupId = list<instance>
	public static volatile ConcurrentHashMap<String, Set<Instance>> servicesMap = new ConcurrentHashMap<>();
	//ip@@port = instance
	public static volatile ConcurrentHashMap<String, Instance> servicesIpMap = new ConcurrentHashMap<>();
	
	public static volatile ConcurrentHashMap<String, Set<Instance>> serviceInterfaceMapping = new ConcurrentHashMap<>();
	
	@Autowired
	private HeartBeatManager heartBeatManager;
	/**
	 * 注册服务：
	 * 1、保存服务
	 * 2、添加心跳
	 * @param ins
	 * @return
	 */
	public boolean registerInstance(Instance ins) {
		if (null == ins) {
			return false;
		}
		log.info("注册实例：=="+ins.toString());
		servicesIpMap.put(ins.getIp()+Constants.SPLITER+ins.getPort(), ins);
		Set<Instance> set = servicesMap.get(ins.getNameSpace()+Constants.SPLITER+ins.getGroupName());
		if (null == set) {
			Set<Instance> hset = new ConcurrentHashSet<>();
			hset.add(ins);
			servicesMap.put(ins.getNameSpace()+Constants.SPLITER+ins.getGroupName(), hset);
		}else {
			if (set.contains(ins)) {
				set.remove(ins);
			}
			set.add(ins);
		}
		SchedulerService.pushFlag = true;
		return heartBeatManager.addBeatInfo(ins);
	}
	
	public static void refreshInstance(String ip,Integer port) {
		Instance instance = servicesIpMap.get(ip+Constants.SPLITER+port);
		if (null != instance) {
			instance.setLatestTimeStamp(System.currentTimeMillis());
		}else {
			log.error("unknow host:ip:{},port:{}",ip,port);
		}
	}
	
	public void removeService(String serviceName, String ip, int port) {
		Instance instance = servicesIpMap.get(ip+Constants.SPLITER+port);
		if (null != instance) {
			instance.setAlive(false);
			heartBeatManager.removeBeatInfo(instance);
		}else {
			log.error("unknow host:ip:{},port:{}",ip,port);
		}
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
	@Override
	public boolean deRegisterInstance(Instance ins) throws MyNacosException {
		removeService(ins.getServiceName(), ins.getIp(), ins.getPort());
		return true;
	}
	@Override
	public boolean deRegisterInstance(String serviceName, String ip, int port) throws MyNacosException {
		removeService(serviceName,ip,port);
		return true;
	}
	@Override
	public boolean deRegisterInstance(String serviceName, String groupName, String ip, int port) throws MyNacosException {
		removeService(serviceName,ip,port);
		return true;
	}

	@Override
	public boolean deRegisterInstance(String namespace, String serviceName, String groupName, String ip, int port)
			throws MyNacosException {
		removeService(serviceName,ip,port);
		return true;
	}
	@Override
	public List<Instance> getAllInstance() throws MyNacosException {
		ArrayList<Instance> list = new ArrayList<Instance>();
		list.addAll(servicesIpMap.values());
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

	/**
	 * 只做缓存逻辑
	 */
	@Override
	public boolean registerApiInfo(Apinfo ins) throws MyNacosException {
		System.out.println("apiinfo====="+ins.toString());
		List<String> apiInterface = ins.getApiInterface();
		for (String inter : apiInterface) {
			if (serviceInterfaceMapping.get(inter) == null) {
				Set<Instance> result = new HashSet<>();
				result.add(servicesIpMap.get(ins.getIp()+Constants.SPLITER+ins.getPort()));
				serviceInterfaceMapping.put(inter, result);
			}else {
				Set<Instance> set = serviceInterfaceMapping.get(inter);
				if (set.contains(servicesIpMap.get(ins.getIp()+Constants.SPLITER+ins.getPort()))) {
					set.remove(servicesIpMap.get(ins.getIp()+Constants.SPLITER+ins.getPort()));
					set.add(servicesIpMap.get(ins.getIp()+Constants.SPLITER+ins.getPort()));
				}else {
					set.add(servicesIpMap.get(ins.getIp()+Constants.SPLITER+ins.getPort()));
				}
				
			}
		}
		return true;
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

	/**
	 * 根据接口名查询服务，同时过滤掉alive为FALSE的实例
	 */
	@Override
	public List<Instance> getApiInfo(String interFaceName) throws MyNacosException {
		Set<Instance> set = serviceInterfaceMapping.get(interFaceName);
		if (null == set || set.isEmpty()) {
			return new ArrayList<Instance>();
		}
		set.removeIf(e ->!e.isAlive());//移除无法接受链接的实例
		return new ArrayList<Instance>(set);
	}

	/**
	 * 根据接口名查询服务，同时过滤掉alive为FALSE的实例
	 */
	@Override
	public List<Instance> getApiInfo(Apinfo info) throws MyNacosException {
		List<String> apiInterface = info.getApiInterface();
		List<Instance> results = new ArrayList<Instance>();
		for (String string : apiInterface) {
			results.addAll(getApiInfo(string));
		}
		return results;
	}

	@Override
	public Object invokeService(InvokeInfo invokeInfo) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
