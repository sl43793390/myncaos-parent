package com.mynacos.entity;

import java.util.Map;

public class InvokeInfo {

	private String interfaceName;
	private String interfaceClass;
	private String methodName;
	private String returnType;
	private Map<String,Object> argslist;
	
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Map<String, Object> getArgslist() {
		return argslist;
	}
	public void setArgslist(Map<String, Object> argslist) {
		this.argslist = argslist;
	}
	public String getInterfaceClass() {
		return interfaceClass;
	}
	public void setInterfaceClass(String interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	
	
}
