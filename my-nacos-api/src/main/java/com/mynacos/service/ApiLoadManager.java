package com.mynacos.service;

public interface ApiLoadManager {

	void onLoad();
	
	void createProxy();
	
	boolean registerApi();
}
