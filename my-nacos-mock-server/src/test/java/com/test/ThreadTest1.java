//package com.test;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.TimeUnit;
//
//public class ThreadTest1 {
//
//	
//	public static void main(String[] args) {
//
//		 ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(2, new ThreadFactory() {
//	            @Override
//	            public Thread newThread(Runnable r) {
//	                Thread t = new Thread(r);
//	                t.setName("com.alibaba.nacos.client.naming.updater");
//	                t.setDaemon(true);
//	                return t;
//	            }
//	        });
//		 
//		 threadPoolExecutor.schedule(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				System.out.println("==555==="+Thread.currentThread().getName());
//			}
//		}, 5, TimeUnit.SECONDS);
//		 threadPoolExecutor.schedule(new Runnable() {
//			 
//			 @Override
//			 public void run() {
//				 // TODO Auto-generated method stub
//				 System.out.println("===33333=="+Thread.currentThread().getName());
//			 }
//		 }, 3, TimeUnit.SECONDS);
//		 
//		 
//		 try {
//			Thread.sleep(6000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	
//}
