package com.mynacos.entity;

import java.util.List;

public class Apinfo {

	private String nameSpace;
	private String groupName;
	private String serviceName;
	private String ip;
	private int port;
	private List<String> apiInterface;
	
	
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public List<String> getApiInterface() {
		return apiInterface;
	}
	public void setApiInterface(List<String> apiInterface) {
		this.apiInterface = apiInterface;
	}
	@Override
	public String toString() {
		return "Apinfo [nameSpace=" + nameSpace + ", groupName=" + groupName + ", serviceName=" + serviceName + ", ip="
				+ ip + ", port=" + port + ", apiInterface=" + apiInterface + "]";
	}
	
	
	
}
