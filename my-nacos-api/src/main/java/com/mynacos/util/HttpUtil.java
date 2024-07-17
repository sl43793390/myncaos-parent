package com.mynacos.util;

import java.util.Map;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

public class HttpUtil {

	public static String get(String url) {
		String string = cn.hutool.http.HttpUtil.get(url, 5000);
		return string;
	}
	
	public static String get(String url,Map<String, Object> parameter) {
		return cn.hutool.http.HttpUtil.get(url, parameter);
	}
	/**
	 * 直接获取body
	 * @param url
	 * @param headers
	 * @param body
	 * @return
	 */
	public static String get(String url,Map<String, String> headers,String body) {
		HttpRequest httpRequest = HttpRequest.get(url);
		String execute = httpRequest.addHeaders(headers).body(body).charset("UTF-8").execute().body();
		return execute;
	}
	
	public static HttpResponse post(String url,Map<String, String> headers,String body) {
		HttpRequest post = HttpRequest.post(url);
		HttpResponse execute = post.addHeaders(headers).body(body).charset("UTF-8").execute();
		return execute;
	}
	public static HttpResponse post(String rul,Map<String, String> headers) {
		HttpRequest post = HttpRequest.post(rul);
		HttpResponse execute = post.addHeaders(headers).charset("UTF-8").execute();
		return execute;
	}
	
	public static HttpResponse post(String rul,String body) {
		HttpRequest post = HttpRequest.post(rul);
		HttpResponse execute = post.body(body).charset("UTF-8").execute();
		return execute;
	}
	
}
