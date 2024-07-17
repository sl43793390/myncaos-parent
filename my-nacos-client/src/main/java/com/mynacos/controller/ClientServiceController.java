package com.mynacos.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mynacos.client.service.ClientNamingServiceImpl;
import com.mynacos.entity.Apinfo;
import com.mynacos.entity.Instance;
import com.mynacos.entity.InvokeInfo;
import com.mynacos.service.MynacosNamingService;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;

@RestController
public class ClientServiceController {

	@Autowired
	private MynacosNamingService clientNamingServiceImpl;
	public static long currentTime;
	@GetMapping(value = "/heartbeat")
	public String heartBeatRe() {
		currentTime = System.currentTimeMillis();
		return "ok";
	}
	
	@PostMapping(value = "/updateService")
	public String updateService(HttpServletRequest req,@RequestBody List<Instance> instances) {
		ClientNamingServiceImpl.updateInstances(instances);
		return "ok";
	}
	
	@PostMapping(value = "/invokeService")
	public Object invokeService(HttpServletRequest req) {
		try {
			BufferedReader reader = req.getReader();
			char[] buf = new char[1024];
			StringBuffer bf = new StringBuffer();
			while (reader.read(buf) != -1) {
				bf.append(buf);
			}
			InvokeInfo bean = JSONUtil.toBean(bf.toString(), InvokeInfo.class);
			return clientNamingServiceImpl.invokeService(bean);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
//	@PostMapping(value = "/invokeService")
//	public Object invokeService(HttpServletRequest req,@RequestBody InvokeInfo info) {
//		Object invokeService = clientNamingServiceImpl.invokeService(info);
//		return invokeService;
//	}
	
}
