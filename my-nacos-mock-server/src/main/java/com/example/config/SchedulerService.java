package com.example.config;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.service.MynacosNamingServiceImpl;
import com.example.service.beat.HeartBeatManager;
import com.mynacos.entity.Instance;
import com.mynacos.exception.MyNacosException;
import com.mynacos.util.Constants;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

@Component
public class SchedulerService {

	@Autowired
	private MynacosNamingServiceImpl mynacosNamingServiceImpl;
	private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);
	public static volatile boolean pushFlag = false;

	private static final int ORANGE_SEC = 10*1000;
	private static final int RED_SEC = 20*1000;
	private static final int REMOVE_SEC = 30*1000;
	@Scheduled(cron = "0/5 * * * * ?")
	public void scheduleInstances() {
		Set<Entry<String,Set<Instance>>> entrySet = MynacosNamingServiceImpl.servicesMap.entrySet();
		for (Entry<String, Set<Instance>> entry : entrySet) {
			Iterator<Instance> value = entry.getValue().iterator();
			while (value.hasNext()) {
				Instance ins = (Instance) value.next();
				if (!ins.isAlive()) {
					if (log.isDebugEnabled()) {
						log.debug("主机被标记为下线，准备移除服务主机：ip{},port:{}",ins.getIp(),ins.getPort());
					}
					value.remove();
					MynacosNamingServiceImpl.servicesIpMap.remove(ins.getIp()+Constants.SPLITER+ins.getPort());
					HeartBeatManager.dom2Beat.remove(ins.getIp()+ins.getPort());
					pushServiceList(ins);
					continue;
				}
				long period = System.currentTimeMillis() - ins.getLatestTimeStamp();
				if (period > REMOVE_SEC) {
					log.warn("时间戳超过30秒，准备移除服务主机：ip{},port:{}",ins.getIp(),ins.getPort());
					ins.setAlive(false);
					value.remove();
					HeartBeatManager.dom2Beat.remove(ins.getIp()+ins.getPort());
					MynacosNamingServiceImpl.servicesIpMap.remove(ins.getIp()+Constants.SPLITER+ins.getPort());
					pushServiceList(ins);
					continue;
				}else if (period > RED_SEC) {
					log.warn("超过20秒，标记为红色");
					ins.setStatus(Constants.STATUS_RED);
				}else if (period > ORANGE_SEC) {
					log.warn("超过11秒，标记为橙色色");
					ins.setStatus(Constants.STATUS_ORANGE);
				}else {
					ins.setStatus(Constants.STATUS_GREEN);
				}
				//如果需要推送更新，则推送
				pushServiceList(ins);
			}
			pushFlag = false;
		}
	}

	private void pushServiceList(Instance ins) {
		if (pushFlag) {
			String url = null;
			try {
				List<Instance> allInstance = mynacosNamingServiceImpl.getAllInstance();
				String body = JSONUtil.toJsonStr(allInstance);
				url = Constants.HTTP_PUSH_URL.replace("ip", ins.getIp()).replace("port", ins.getPort()+"").trim();
				log.info("开始更新服务列表到客户端：{}",url);
				String res = HttpUtil.post(url, body);
				if (res.equals("ok")) {
					log.info("推送成功");
					pushFlag = false;
				}
			} catch (MyNacosException e) {
				pushFlag = false;
				log.error("推送服务列表失败：无法连接服务器{}",url);
//				log.error(ExceptionUtils.getStackTrace(e));
			}
		}
	}
}
