package com.mynacos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mynacos.controller.ClientServiceController;
import com.mynacos.listener.HeartListerner;

import cn.hutool.core.thread.ThreadUtil;

public class ClientSchedulerService {

	private static final Logger log = LoggerFactory.getLogger(ClientSchedulerService.class);
	private HeartListerner heartListerner;

	private static int CHECK_PERIOD = 5000;

	public ClientSchedulerService() {
		scheduleInstances();
	}

	// @Scheduled(cron = "0/5 * * * * ?")
	public void scheduleInstances() {
//		long period = System.currentTimeMillis() - ClientServiceController.currentTime;
//		if (period > 15000) {
//			log.warn("服务器失去链接，开启重连...");
//			ClientServiceController.currentTime = System.currentTimeMillis();
//			heartListerner.retryRegister();
//		}

		Thread sch = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					long period = System.currentTimeMillis() - ClientServiceController.currentTime;
					if (period > 30000) {
						log.warn("服务器失去链接，开启重连...");
						ClientServiceController.currentTime = System.currentTimeMillis();
						heartListerner.retryRegister();
					}
					ThreadUtil.sleep(CHECK_PERIOD);
				}
			}
		});
		sch.setDaemon(true);
		sch.setName("keep-register");
		sch.start();
	}

	public HeartListerner getHeartListerner() {
		return heartListerner;
	}

	public void setHeartListerner(HeartListerner heartListerner) {
		this.heartListerner = heartListerner;
	}

}
