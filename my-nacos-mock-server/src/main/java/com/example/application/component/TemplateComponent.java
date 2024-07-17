package com.example.application.component;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.application.component.util.RquestComponent;
import com.example.entity.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Service
@Scope("prototype")
public class TemplateComponent extends VerticalLayout {

	private static final long serialVersionUID = 4512289455462777038L;

	public TemplateComponent() {
		super();
		 setDefaultHorizontalComponentAlignment(Alignment.START); 
		initHeaderLayout();
		initFilterLayout();
		initFilterLayout2();
		initBodyLayout();
		initBottomLayout();
	}

	private void initHeaderLayout() {
		add(RquestComponent.getTile("标题示例"));
	}

	private void initFilterLayout() {
		DatePicker com1 = new DatePicker();
		com1.setValue(LocalDate.now());
		ComboBox<String> com2 = new ComboBox<>();
		com2.setItems("abc","def","wer");
		ComboBox<String> com3 = new ComboBox<>();
		com3.setItems("abc","def","wer");
		ComboBox<String> com4 = new ComboBox<>();
		com4.setItems("abc","def","wer");
		ComboBox<String> com5 = new ComboBox<>();
		com5.setItems("abc","def","wer");
		HorizontalLayout horizontalLayout = RquestComponent.getResponsiveHorizontalLayoutLeft();
		horizontalLayout.add(RquestComponent.getLabel("日期选择："),com1,RquestComponent.getLabel("标签示例："),com2,RquestComponent.getLabel("标签示例："),com3,
				RquestComponent.getLabel("标签示例："),com4,RquestComponent.getLabel("标签示例："),com5);
		add(horizontalLayout);
	}
	private void initFilterLayout2() {
		ComboBox<String> com1 = new ComboBox<>();
		com1.setItems("abc","def","wer");
		ComboBox<String> com2 = new ComboBox<>();
		com2.setItems("abc","def","wer");
		ComboBox<String> com3 = new ComboBox<>();
		com3.setItems("abc","def","wer");
		Button queryBtn = RquestComponent.getPrimaryBtn("查询");
		Button resetBtn = RquestComponent.getPrimaryBtn("重置全部");
		HorizontalLayout horizontalLayout = RquestComponent.getResponsiveHorizontalLayoutLeft();
		horizontalLayout.add(RquestComponent.getLabel("标签示例："),com1,RquestComponent.getLabel("标签示例："),com2,RquestComponent.getLabel("标签示例："),com3,
				queryBtn,resetBtn);
		add(horizontalLayout);
	}

	private void initBodyLayout() {
		 Grid<User> grid = new Grid<User>();
		 grid.setSelectionMode(SelectionMode.SINGLE);
        grid.addColumn(User::getUserId).setHeader("用户ID").setFrozen(true);
        grid.addColumn(User::getUserName).setHeader("用户名");
        grid.addColumn(User::getCdPhone).setHeader("电话");
        grid.addColumn(User::getEmail).setHeader("邮箱");
        grid.addColumn(User::getOrganization).setHeader("机构");
        List<User> createDemoData = User.createDemoData(12);
        grid.setItems(createDemoData);
        add(grid);
		
	}

	private void initBottomLayout() {
		Button queryBtn = RquestComponent.getPrimaryBtn("查看");
		Button resetBtn = RquestComponent.getPrimaryBtn("删除");
		HorizontalLayout horizontalLayout = RquestComponent.getResponsiveHorizontalLayoutLeft();
		horizontalLayout.setJustifyContentMode(JustifyContentMode.END);
		horizontalLayout.add(queryBtn,resetBtn);
		add(horizontalLayout);
	}
	
}
