package com.example.application.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.application.component.util.RquestComponent;
import com.example.service.MynacosNamingServiceImpl;
import com.mynacos.entity.Instance;
import com.mynacos.util.Constants;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "main")
public class MainView extends VerticalLayout implements BeforeEnterObserver{

	
	private static final Logger log = LoggerFactory.getLogger(MainView.class);

	private static final long serialVersionUID = -5085182550925514722L;
	Grid<Instance> grid;
	private Dialog dialog;
	private FeederThread thread;
	private Grid<Instance> grid2;
	
	public MainView() {
		setWidth("90%");
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		setMargin(true);
		addGrid();
		addGridLayout();
	}

	private void addGrid() {
		HorizontalLayout hl = RquestComponent.getResponsiveHorizontalLayoutLeft();
//		Span stats = new Span("服务列表");
//		stats.addClassNames("text-xl", "mt-m");
		hl.setAlignItems(Alignment.END);
		hl.add(new H5("服务注册列表："));
		
		Button bu = new Button("刷新");
		bu.setWidth("120px");
		bu.setIcon(new Icon(VaadinIcon.CONNECT));
		hl.add(bu);
		bu.addClickListener(e -> {
			refreshStatus();
		});
		Button bt = new Button("退出");
		bt.addClickListener(e -> {
			UI.getCurrent().getPage().setLocation("/");
			VaadinSession.getCurrent().getSession().invalidate();
			SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
		});
		hl.add(bt);
		add(hl);
		dialog = new Dialog();
		Button saveButton = new Button("确定", e -> dialog.close());
	    saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	    Button cancelButton = new Button("取消", e -> dialog.close());
		dialog.getFooter().add(cancelButton);
		dialog.getFooter().add(saveButton);
		dialog.add("确认信息");
		dialog.setModal(true);
		
		grid = new Grid<Instance>();
		grid.addColumn(Instance::getNameSpace).setHeader("名称空间");
		grid.addColumn(Instance::getGroupName).setHeader("group");
		grid.addColumn(Instance::getServiceName).setHeader("服务名");
		grid.addColumn(Instance::getIp).setHeader("IP");
		grid.addColumn(Instance::getPort).setHeader("端口");
		grid.addComponentColumn(in ->{
			Button b = new Button();
			if (in.getStatus().equals(Constants.STATUS_GREEN)) {
				b.setText("正常");
				b.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			}else if (in.getStatus().equals(Constants.STATUS_ORANGE)) {
				b.setText("尝试链接");
				b.addThemeVariants(ButtonVariant.LUMO_ICON);
			}else {
				b.setText("无法连接");
				b.addThemeVariants(ButtonVariant.LUMO_ERROR);
			}
			return b;
		}).setHeader("状态");
//		grid.addComponentColumn(p ->{
//        	Button b = RquestComponent.getPrimaryBtn("删除主机");
//			b.addClickListener(e -> {
//				dialog.open();
//			});
//			return b;
//        }).setHeader("操作");//该操作可以后续设计下线的服务停止服务多少分钟TODO
		add(grid);
	}

	private void addGridLayout() {
		HorizontalLayout layout = RquestComponent.getResponsiveHorizontalLayoutLeft();
		layout.add(new H5("接口注册列表："));
		add(layout);
		grid2 = new Grid<Instance>();
		grid2.addColumn(Instance::getNameSpace).setHeader("名称空间").setKey("1");
		grid2.addColumn(Instance::getGroupName).setHeader("group").setKey("2");
		grid2.addColumn(Instance::getServiceName).setHeader("服务名").setKey("3");
		grid2.addColumn(Instance::getIp).setHeader("IP").setKey("4");
		grid2.addColumn(Instance::getPort).setHeader("端口").setKey("5");
		grid2.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
		
		add(grid2);
	}

	private void refreshStatus() {
		
		List<Instance> collect = MynacosNamingServiceImpl.servicesIpMap.values().stream().collect(Collectors.toList());
		grid.setItems(collect);
		
		Set<Entry<String,Set<Instance>>> entrySet = MynacosNamingServiceImpl.serviceInterfaceMapping.entrySet();
		List<Instance> res = new ArrayList<Instance>();
		for (Entry<String, Set<Instance>> entry : entrySet) {
			Set<Instance> value = entry.getValue();
			res.addAll(value);
		}
		grid2.setItems(res);
		
	}
//	@Override
//	public void beforeEnter(BeforeEnterEvent event) {
//		Object attribute2 = VaadinSession.getCurrent().getAttribute("user");
//		if (null == attribute2) {
//			UI.getCurrent().getPage().setLocation("/");
//			return;
//		}
//		Boolean attribute = (boolean)VaadinSession.getCurrent().getAttribute("user");
//		if (!attribute) {
//			UI.getCurrent().getPage().setLocation("/");
//		}
//	}
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		// Start the data feed thread
		thread = new FeederThread(attachEvent.getUI(), this);
		thread.start();
	}
	@Override
	protected void onDetach(DetachEvent detachEvent) {
		// Cleanup
		System.out.println("线程结束。。。");
		thread.interrupt();
		thread = null;
	}

	private static class FeederThread extends Thread {
		private final UI ui;
		private final MainView view;

		private int count = 0;

		public FeederThread(UI ui, MainView view) {
			this.ui = ui;
			this.view = view;
		}

		@Override
		public void run() {
			try {
				// Update the data for a while
				while (count < 900) {
					// Sleep to emulate background work
					Thread.sleep(1000);

					ui.access(() ->view.refreshStatus());
//					ui.access(() -> view.add(new Span(message)));
					count ++;
				}

				// Inform that we're done
				ui.access(() -> {
					view.add(new Span("Done updating"));
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		System.out.println("进入页面======");
	}
}
