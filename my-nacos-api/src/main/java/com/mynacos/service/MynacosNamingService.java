package com.mynacos.service;

import java.util.List;

import com.mynacos.entity.Apinfo;
import com.mynacos.entity.Instance;
import com.mynacos.entity.InvokeInfo;
import com.mynacos.exception.MyNacosException;

public interface MynacosNamingService {

	boolean registerInstance(Instance ins) throws MyNacosException;;

	boolean registerInstance(String serviceName, String ip, int port) throws MyNacosException;
	
	boolean registerInstance(String serviceName, String groupName, String ip, int port) throws MyNacosException;
	
	boolean registerInstance(String namespace,String serviceName, String groupName, String ip, int port) throws MyNacosException;
	
	boolean registerApiInfo(Apinfo ins) throws MyNacosException;
	
	boolean registerApiInfo(String serviceName, String ip, int port) throws MyNacosException;
	
	boolean registerApiInfo(String serviceName, String groupName, String ip, int port) throws MyNacosException;
	
	boolean registerApiInfo(String namespace,String serviceName, String groupName, String ip, int port) throws MyNacosException;
	
	List<Instance> getApiInfo(String interFaceName) throws MyNacosException;
	/**
	 * 获取多个接口对应的服务器IP
	 * @param info
	 * @return
	 * @throws MyNacosException
	 */
	List<Instance> getApiInfo(Apinfo info) throws MyNacosException;

	boolean deRegisterInstance(Instance ins) throws MyNacosException;

	boolean deRegisterInstance(String serviceName, String ip, int port) throws MyNacosException;
	
	boolean deRegisterInstance(String serviceName, String groupName, String ip, int port) throws MyNacosException;
	
	boolean deRegisterInstance(String namespace,String serviceName, String groupName, String ip, int port) throws MyNacosException;
	
	List<Instance> getAllInstance() throws MyNacosException;
	
	List<Instance> getInstanceByNameSpace(String namespace) throws MyNacosException;
	
	List<Instance> getInstanceByNameAndGroup(String namespace,String groupId) throws MyNacosException;

	Instance getInstance(String ip, String port) throws MyNacosException;
	
	public Object invokeService(InvokeInfo invokeInfo) throws ClassNotFoundException;
}
