package com.example.service.beat;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.service.MynacosNamingServiceImpl;
import com.mynacos.entity.BeatInfo;
import com.mynacos.entity.Instance;
import com.mynacos.util.Constants;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.http.HttpUtil;
@Service
public class HeartBeatManager {

	
	private static final Logger log = LoggerFactory.getLogger(HeartBeatManager.class);

    private final ScheduledExecutorService executorService;
    public static final Map<String, BeatInfo> dom2Beat = new ConcurrentHashMap<String, BeatInfo>();
//    public static final Set<String> beatIpPort = new ConcurrentHashSet<String>(32);
	
	public HeartBeatManager() {
		executorService = new ScheduledThreadPoolExecutor(Constants.DEFAULT_CLIENT_BEAT_THREAD_COUNT, new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				Thread th = new Thread(r);
				th.setDaemon(true);
				th.setName("mynacos-heartbeat");
				return th;
			}
		});
	}

	/**
	 * 注册心跳
	 * @param ins
	 */
	public boolean addBeatInfo(Instance ins) {
		BeatInfo buildBeatInfo = buildBeatInfo(ins);
		if (dom2Beat.containsKey(buildBeatInfo.getIp()+buildBeatInfo.getPort())) {
			return true;
		}
		dom2Beat.put(buildBeatInfo.getIp()+buildBeatInfo.getPort(), buildBeatInfo);
		executorService.schedule(new BeatTask(buildBeatInfo), Constants.BEAT_PERIOD, TimeUnit.SECONDS);
		return true;
	}
	
	public boolean removeBeatInfo(Instance ins) {
		try {
			BeatInfo beatInfo = dom2Beat.get(ins.getIp()+ins.getPort());
			beatInfo.setStopped(true);
			dom2Beat.remove(ins.getIp()+ins.getPort());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("remove beatinfo error,{}",e);
		}
		return true;
	}
	
	private BeatInfo buildBeatInfo(Instance ins) {
		BeatInfo info = new BeatInfo();
		info.setIp(ins.getIp());
		info.setPeriod(3);
		info.setPort(ins.getPort());
		info.setServiceName(ins.getServiceName());
		return info;
	}
	
	class BeatTask implements Runnable{

		private BeatInfo beatInfo;
		
		@Override
		public void run() {
			if (this.beatInfo.isStopped()) {
				return;
			}
			//请求客户端的心跳接口http://ip:port/heartbeat
			try {
				String url = Constants.HTTP_HEART_BEAT_URL.replace("ip", beatInfo.getIp()).replace("port", beatInfo.getPort()+"").trim();
				if (log.isDebugEnabled()) {
					log.debug("开始发送心跳到客户端：{}",url);
				}
//				log.info("开始发送心跳到客户端：{}",url);
				String res = HttpUtil.get(url,5000);
				if (res.equals("ok")) {
					//刷新时间戳
					MynacosNamingServiceImpl.refreshInstance(beatInfo.getIp(), beatInfo.getPort());
					//继续下一次心跳
					executorService.schedule(new BeatTask(beatInfo), Constants.BEAT_PERIOD, TimeUnit.SECONDS);
				}
			} catch (Exception e) {
				log.error(ExceptionUtils.getStackTrace(e));
				log.error("客户端心跳失败，即将移除该服务：ip:{},port:{}",beatInfo.getIp(),beatInfo.getPort());
				dom2Beat.remove(beatInfo.getIp()+beatInfo.getPort());
			}
			
		}
		public BeatTask() {
			super();
			// TODO Auto-generated constructor stub
		}
		public BeatTask(BeatInfo beatInfo) {
			super();
			this.beatInfo = beatInfo;
		}
		public BeatInfo getBeatInfo() {
			return beatInfo;
		}
		public void setBeatInfo(BeatInfo beatInfo) {
			this.beatInfo = beatInfo;
		}
		
	}
}

