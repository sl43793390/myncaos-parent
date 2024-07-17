package com.mynacos.listener;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.mynacos.client.service.ClientNamingServiceImpl;
import com.mynacos.controller.ClientServiceController;
import com.mynacos.entity.Instance;
import com.mynacos.exception.MyNacosException;
import com.mynacos.service.ApiLoadManager;
import com.mynacos.service.MynacosNamingService;
import com.mynacos.util.Constants;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.system.SystemUtil;

/**
 * 用于启动spring后向服务端注册本地服务
 * @author Administrator
 *
 */
public class HeartListerner{

	private static final Logger log = LoggerFactory.getLogger(HeartListerner.class);

	private MynacosNamingService clientNamingServiceImpl;
	
	public void onStartUp() {

		Instance ins = populateInstance();
		try {
			boolean registerInstance = clientNamingServiceImpl.registerInstance(ins);
			if (!registerInstance) {
				retryRegister();
			}else {
				ClientServiceController.currentTime = System.currentTimeMillis();
			}
		} catch (Exception e) {
			retryRegister();
			log.error("链接服务器失败，开始再次尝试。。。");
		}
	}
	
	public void retryRegister() {
		Instance ins = populateInstance();
		while (true) {
			boolean flag;
			try {
				flag = clientNamingServiceImpl.registerInstance(ins);
				if (flag) {
					log.warn("服务器连接成功。。。");
					ClientServiceController.currentTime = System.currentTimeMillis();
					ApiLoadManager bean = SpringUtil.getBean(ApiLoadManager.class);
					if (null != bean) {
						bean.registerApi();
					}
					break;
				}
				ThreadUtil.sleep(5000);
				log.error("链接服务器失败，开始再次尝试。。。");
			} catch (MyNacosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	private Instance populateInstance() {
		String ip = SystemUtil.getHostInfo().getAddress();
		String port = ResourceBundle.getBundle("application").getString("server.port");
		String serviceName = ResourceBundle.getBundle("application").getString("spring.application.name");
		Instance ins = new Instance();
		ins.setNameSpace(Constants.DEFAULT_NAMESPACE);
		ins.setGroupName(Constants.DEFAULT_GROUPID);
		ins.setServiceName(serviceName);
		ins.setIp(ip);
		ins.setPort(Integer.parseInt(port));
		return ins;
	}

	public MynacosNamingService getClientNamingServiceImpl() {
		return clientNamingServiceImpl;
	}

	public void setClientNamingServiceImpl(MynacosNamingService clientNamingServiceImpl) {
		this.clientNamingServiceImpl = clientNamingServiceImpl;
	}

	
}
