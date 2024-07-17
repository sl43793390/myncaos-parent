package com.test;

import java.util.HashSet;
import java.util.Set;

import com.mynacos.entity.Instance;

public class SetTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Instance ins = new Instance();
		ins.setIp("123");
		ins.setAlive(false);
		Instance ins2 = new Instance();
		ins2.setIp("534");
		Instance ins3 = new Instance();
		ins3.setIp("876");
		Set<Instance> sets = new HashSet<>();
		sets.add(ins3);
		sets.add(ins2);
		sets.add(ins);
		sets.removeIf(e -> !e.isAlive());
		System.out.println(sets);
	}

}
