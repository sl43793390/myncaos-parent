package com.mynacos.service.loadbalance;

import java.util.ArrayList;
import java.util.List;

import com.mynacos.entity.Instance;

import cn.hutool.core.util.RandomUtil;

public class BalanceUtil {
	public static List<Instance> invokeApiInstances;
	private static int i = 0;

	private List<Instance> weightIns;
	/**
	 * 轮询
	 * @return
	 */
	public static Instance roundForInvokerIns() {
		if (null != invokeApiInstances) {
			if (i> invokeApiInstances.size() -1) {
				i = 0;
			}
			Instance instance = invokeApiInstances.get(i);
			i ++;
			return instance; 
		}
		return null;
	}
	/**
	 * 随机
	 * @return
	 */
	public static Instance randomIns() {
		int randomInt = RandomUtil.randomInt(invokeApiInstances.size());
		return invokeApiInstances.get(randomInt);
	}
	
	public void setInstanceListWithWeight(List<Instance> ins) {
		if (null == weightIns) {
			weightIns = new ArrayList<>();
		}
		int max = 0;
		for (Instance instance : ins) {
			if (instance.getWeight() >max) {
				max = instance.getWeight();
			}
		}
		for (int i = 0; i < max; i++) {
			for (Instance instance : ins) {
				if (instance.getIndex() > instance.getWeight()) {
					continue;
				}
				weightIns.add(instance);
				instance.setIndex(instance.getIndex() +1);
			}
		}
	}
	/**
	 * 加权轮询
	 * @return
	 */
	public Instance randomWeight() {
		int randomInt = RandomUtil.randomInt(weightIns.size());
		return weightIns.get(randomInt);
	}
	
}
