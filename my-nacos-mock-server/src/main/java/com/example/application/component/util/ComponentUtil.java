package com.example.application.component.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.example.entity.Menu;
import com.example.entity.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.HeaderRow.HeaderCell;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

import cn.hutool.core.annotation.Link;

@Service
public class ComponentUtil implements ApplicationContextAware{
	
	private static Logger logger = LoggerFactory.getLogger(ComponentUtil.class);
	
	public static ApplicationContext applicationContext;
	
    private static final String CLASSPATH = "com.rquest.scf.ui.";

    
    

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	
	
	public static  User getCurrentUser(){
		return (User) VaadinSession.getCurrent().getAttribute("user");
	}
	
	public static  String getCurrentInstitutionRole(){
		return VaadinSession.getCurrent().getAttribute("CURRENT_INST_ROLE").toString();
	}
	
    /**
     * createComponent:(工厂模式生成对应的CommonComponent)；<br/>
     * className；<br/>
     * CommonComponent)；<br/>
     * 
     * @author baiguomin
     */
    public static Component createComponent(Menu menu) {
        String className = null;
        if (menu == null || menu.getNameClass() == null) {
            return null;
        } else {
            className = menu.getNameClass();
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(CLASSPATH + className);
        } catch (ClassNotFoundException e) {
            logger.error("className:ComponentFactory,methodName:createComponent,message:{}", e);
        }

        if (clazz == null) {
            return null;
        }

        Component bean = null;
        try {
            bean = (Component) applicationContext.getBean(clazz);
        } catch (BeansException e1) {
            try {
                bean = (Component) clazz.newInstance();
                return bean;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            e1.printStackTrace();
        }
        if (bean == null) {
            try {
                bean = (Component) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    
    /**
     * createComponent:(工厂模式生成对应的CommonComponent)；<br/>
     * className；<br/>
     * CommonComponent)；<br/>
     * 
     * @author baiguomin
     */
    public static Component createComponent(String  className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(CLASSPATH + className);
        } catch (ClassNotFoundException e) {
            logger.error("className:ComponentFactory,methodName:createComponent,message:{}", e);
        }

        if (clazz == null) {
            return null;
        }

        Component bean = null;
        try {
            bean = (Component) applicationContext.getBean(clazz);
        } catch (BeansException e1) {
            try {
                bean = (Component) clazz.newInstance();
                return bean;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            e1.printStackTrace();
        }
        if (bean == null) {
            try {
                bean = (Component) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
   
    
    
}
