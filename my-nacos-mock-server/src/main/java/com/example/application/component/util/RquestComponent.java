package com.example.application.component.util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DashStyle;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.charts.model.style.Color;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class RquestComponent {
	
	/**
	 * 创建一个水平布局layout ，该layout放入多个组合默认靠左侧依顺序放置
	 * @return
	 */
	public static HorizontalLayout  getResponsiveHorizontalLayoutLeft(){
		HorizontalLayout layout = new HorizontalLayout();
		layout.setJustifyContentMode(JustifyContentMode.START);
		layout.addClassName("list-view-horizontalLayout");
		layout.setWidth("100%");
		return layout;
	}
	/**
	 * 创建一个水平布局layout ，该layout放入多个组合默认靠右侧侧依顺序放置
	 * @return
	 */
	public static HorizontalLayout  getResponsiveHorizontalLayoutRight(){
		HorizontalLayout layout = new HorizontalLayout();
		layout.setJustifyContentMode(JustifyContentMode.END);
		layout.addClassName("list-view-horizontalLayout");
		layout.setWidth("100%");
		return layout;
	}
	/**
	 * 创建一个水平布局layout ，该layout放入多个组合默认居中侧侧依顺序放置
	 * @return
	 */
	public static HorizontalLayout  getResponsiveHorizontalLayoutMiddle(){
		HorizontalLayout layout = new HorizontalLayout();
		layout.setJustifyContentMode(JustifyContentMode.CENTER);
		layout.addClassName("list-view-horizontalLayout");
		layout.setWidth("100%");
		return layout;
	}

	public static Button getPrimaryBtn(String caption) {
		Button btn = new Button(caption);
		btn.addClassName("vaadin-button-back");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		return btn;
	}
	
	public static HorizontalLayout getTile(String title) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setHeight("40px");
		Label lb = new Label(title);
		lb.addClassName("lb-title");
		layout.add(lb);
		return layout;
	}
	
	public static Label getLabel(String text) {
		Label lb = new Label(text);
		lb.addClassName("lb-width-common");
		return lb;
	}
    
}

	