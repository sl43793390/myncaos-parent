package com.example.application.views;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "logout")
@AnonymousAllowed
public class LogOutView extends VerticalLayout {

    private static final long serialVersionUID = 8728059005569047780L;

    public LogOutView() {
    	setJustifyContentMode(JustifyContentMode.CENTER);
      setAlignItems(Alignment.CENTER);
      Label lb = new Label("推出成功,谢谢！");
      add(lb);
    }
	


}
