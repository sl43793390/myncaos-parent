package com.mynacos.util;

public class Constants {

    /**  
     * 无需进行复制的特殊类型数组  
     */    
	public static final Class<?>[] needlessCloneClasses = new Class[]{String.class,Boolean.class,Character.class,Byte.class,Short.class,    
        Integer.class,Long.class,Float.class,Double.class,Void.class,Object.class,Class.class    
    }; 
	public static final String SPLITER = "@@";
	public static final String SERVICE_NAME = "serviceName";

	public static final String CLUSTER_NAME = "clusterName";

	public static final String NAMESPACE_ID = "namespaceId";

	public static final String GROUP_NAME = "groupName";

	public static final String LIGHT_BEAT_ENABLED = "lightBeatEnabled";

	public static final String DEFAULT_NAMESPACE = "defaultNameSpace";

	public static final String DEFAULT_GROUPID = "default_groupId";
	
	public static final String ENCODING = "UTF-8";
	
	public static final String HTTP = "http://";
	public static final String NOT_FOUND = "NOT_FOUND";
	public static final String OK = "ok";
	public static final String FAILURE = "failure";
	
	public static final String HTTP_HEART_BEAT_URL = "http://ip:port/heartbeat";
	public static final String HTTP_PUSH_URL = "http://ip:port/updateService";
	//默认心跳间隔秒数
	public static final Integer BEAT_PERIOD = 5;

	public static final int DEFAULT_CLIENT_BEAT_THREAD_COUNT = Runtime.getRuntime().availableProcessors() > 1
			? Runtime.getRuntime().availableProcessors() / 2
			: 1;

	public static final int DEFAULT_POLLING_THREAD_COUNT = Runtime.getRuntime().availableProcessors() > 1
			? Runtime.getRuntime().availableProcessors() / 2
			: 1;

	public static final String STATUS_GREEN = "green";
	public static final String STATUS_ORANGE = "orange";
	public static final String STATUS_RED = "red";
}