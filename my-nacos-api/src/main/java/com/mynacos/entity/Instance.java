package com.mynacos.entity;

import java.util.Objects;

import com.mynacos.util.Constants;

public class Instance {

	/**
	 * 客户端如果没有配置，默认发送default namespace and default groupId
	 */
	private String nameSpace;
	private String groupName;
	private String serviceName;
	private String ip;
	private int port;
	private long latestTimeStamp;
	private boolean isAlive = true;
	private String status = Constants.STATUS_GREEN;
	private int weight = 0;
	private Integer index = 0;
	private boolean subscribeFlag;
	
	@Override
	public int hashCode() {
		return Objects.hash(ip, port);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instance other = (Instance) obj;
		return Objects.equals(ip, other.ip) && Objects.equals(port, other.port);
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	public boolean isSubscribeFlag() {
		return subscribeFlag;
	}
	public void setSubscribeFlag(boolean subscribeFlag) {
		this.subscribeFlag = subscribeFlag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the isAlive
	 */
	public boolean isAlive() {
		return isAlive;
	}
	/**
	 * @param isAlive the isAlive to set
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
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
	public long getLatestTimeStamp() {
		return latestTimeStamp;
	}
	public void setLatestTimeStamp(long latestTimeStamp) {
		this.latestTimeStamp = latestTimeStamp;
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "Instance [nameSpace=" + nameSpace + ", groupName=" + groupName + ", serviceName=" + serviceName
				+ ", ip=" + ip + ", port=" + port + ", latestTimeStamp=" + latestTimeStamp + ", isAlive=" + isAlive
				+ ", status=" + status + "]";
	}
	
	
}
