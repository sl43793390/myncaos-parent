package com.example.application.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mynacos.entity.Apinfo;
import com.mynacos.entity.Instance;
import com.mynacos.exception.MyNacosException;
import com.mynacos.service.MynacosNamingService;
import com.mynacos.util.Constants;

/**
 * 服务注册、拉取、查询等功能
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/service")
public class ServiceController {
	
	private static final Logger log = LoggerFactory.getLogger(ServiceController.class);

	private static final String OK = "ok";
	
	@Autowired
	private MynacosNamingService mynacosNamingService;
	/**
	 * 注册服务
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/reg")
	public String registerService(HttpServletRequest req,@RequestBody Instance instance) {
//		String ins = req.getParameter("reg");
//		Instance instance = JSONUtil.toBean(ins, Instance.class);
		instance.setLatestTimeStamp(System.currentTimeMillis());
		try {
			mynacosNamingService.registerInstance(instance);
		} catch (MyNacosException e) {
			log.error("服务注册失败：{}",e);
			e.printStackTrace();
		}
		return OK;
	}
	
	@PostMapping(value = "/dereg")
	public String deRegisterService(HttpServletRequest req,@RequestBody Instance instance) {
//		String ins = req.getParameter("dereg");
//		Instance instance = JSONUtil.toBean(ins, Instance.class);
		instance.setLatestTimeStamp(System.currentTimeMillis());
		try {
			mynacosNamingService.deRegisterInstance(instance);
		} catch (MyNacosException e) {
			log.error("服务移除失败：{}",e);
			e.printStackTrace();
		}
		return OK;
	}
	/**
	 * 查询实例
	 * @param req
	 * @return
	 */
	@GetMapping("/allInstances")
	public List<Instance> getAllInstances(HttpServletRequest req) {
		try {
			return mynacosNamingService.getAllInstance();
		} catch (MyNacosException e) {
			log.error("查询服务列表错误{}",e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查询实例
	 * @param req
	 * @return
	 */
	@GetMapping("/insNamespace")
	public List<Instance> getInstancesByNameSpace(HttpServletRequest req,@RequestParam String namespace) {
		try {
			return mynacosNamingService.getInstanceByNameSpace(namespace);
		} catch (MyNacosException e) {
			log.error("查询服务列表错误{}",e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查询实例
	 * @param req
	 * @return
	 */
	@GetMapping("/insGroup")
	public List<Instance> getInstancesByNameSpaceAndGroup(@RequestParam String namespace,@RequestParam String groupName) {
		try {
			return mynacosNamingService.getInstanceByNameAndGroup(namespace,groupName);
		} catch (MyNacosException e) {
			log.error("查询服务列表错误{}",e);
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/getapi")
	@ResponseBody
	public List<Instance> getInterServices(@RequestBody Apinfo info){
		try {
			return mynacosNamingService.getApiInfo(info);
		} catch (MyNacosException e) {
			log.error("查询服务接口错误{}",e);
			e.printStackTrace();
		}
		return null;
	}
	@PostMapping("/regapi")
	public String regInterServices(@RequestBody Apinfo info){
		try {
			if (null == info || info.getApiInterface().isEmpty()) {
				log.error("注册接口不能为空");
				return Constants.FAILURE;
			}
			mynacosNamingService.registerApiInfo(info);
		} catch (MyNacosException e) {
			log.error("查询服务接口错误{}",e);
			e.printStackTrace();
		}
		return "ok";
	}
	/**
	 * 心跳,刷新时间/为客户端预留模式
	 * @param req
	 * @return
	 */
//	@PostMapping("/heartbeat")
//	public String heartbeat(HttpServletRequest req) {
//		
//		return OK;
//	}
	
	@GetMapping("/health")
	public String healthCheck() {
		return "ok";
	}
	
}
