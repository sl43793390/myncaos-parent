package com.mynacos.service.proxy;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.mynacos.entity.Apinfo;
import com.mynacos.entity.Instance;
import com.mynacos.entity.InvokeInfo;
import com.mynacos.exception.MyNacosException;
import com.mynacos.service.MynacosNamingService;
import com.mynacos.service.loadbalance.BalanceUtil;
import com.mynacos.util.Constants;
import com.mynacos.util.JacksonUtils;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

/**
 * 接口中每个方法的动态实现逻辑
 * 
 * @author Administrator
 *
 */
public class MethodImpl<T> {
	
	private static final Logger log = LoggerFactory.getLogger(MethodImpl.class);

	private final Class<T> mapperInterface;
	private final Method method;
	private MynacosNamingService namingService;
	private Apinfo invokeApi;

	public MethodImpl(Class<T> mapperInterface, Method method,Apinfo info) {
		this.mapperInterface = mapperInterface;
		this.method = method;
		this.invokeApi = info;
	}

	/**
	 * 先查询api服务器信息，然后调用指定的服务器
	 * @param args
	 * @return
	 */
	public Object execute(Object[] args) {
		List<Instance> apiInfo = null;
		try {
			if (null == namingService) {
				namingService = SpringUtil.getBean(MynacosNamingService.class);
			}
			apiInfo = namingService.getApiInfo(invokeApi);
			if (null == apiInfo) {
				log.error("未获取到可调用的服务器=================");
				return null;
			}
		} catch (MyNacosException e1) {
			log.error("获取服务api列表失败：{}",e1);
			e1.printStackTrace();
		}//从服务端获取服务列表
		BalanceUtil.invokeApiInstances = apiInfo;
		Instance roundForInvokerIns = BalanceUtil.roundForInvokerIns();
		
		try {
			String url = Constants.HTTP + roundForInvokerIns.getIp()+":"+roundForInvokerIns.getPort()+"/invokeService";
			InvokeInfo invoke = new InvokeInfo();
			invoke.setInterfaceName(mapperInterface.getSimpleName());
			invoke.setInterfaceClass(mapperInterface.getName());
			invoke.setMethodName(method.getName());
			invoke.setReturnType(method.getReturnType().getName());
			
			Map<String,Object> list = new LinkedHashMap<String, Object>();
			for (int i = 0; i < args.length; i++) {
				list.put(args[i].getClass().getName(),args[i]);
			}
			invoke.setArgslist(list);
			String jsonStr = JSONUtil.toJsonStr(invoke);
			String results = HttpUtil.post(url, jsonStr);
			Class<?> returnType = method.getReturnType();
			JavaType constructJavaType = JacksonUtils.constructJavaType(returnType);
			return JacksonUtils.mapper.readValue(results, constructJavaType);
		} catch (Exception e) {
			log.error(ExceptionUtil.getSimpleMessage(e));
		}
		return null;

	}
	
}
