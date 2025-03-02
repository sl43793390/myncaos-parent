package com.mynacos.entity;

import java.util.Map;

public class BeatInfo {

	private int port;

	private String ip;

	private double weight;

	private String serviceName;

	private Map<String, String> metadata;

	private volatile boolean scheduled;

	private volatile long period;

	private volatile boolean stopped;

	@Override
	public String toString() {
		return "BeatInfo{" + "port=" + port + ", ip='" + ip + '\'' + ", weight=" + weight + ", serviceName='"
				+ serviceName + '\'' + ", metadata=" + metadata + ", scheduled=" + scheduled + ", period=" + period
				+ ", stopped=" + stopped + '}';
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

}
