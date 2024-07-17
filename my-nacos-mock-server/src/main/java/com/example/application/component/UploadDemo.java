package com.example.application.component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import com.example.application.component.util.FileReceiverImpl;
import com.example.application.component.util.RquestComponent;
import com.example.application.component.util.UploadExamplesI18N;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.upload.FinishedEvent;
import com.vaadin.flow.component.upload.MultiFileReceiver;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.StartedEvent;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileData;
import com.vaadin.flow.component.upload.receivers.MultiFileBuffer;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

public class UploadDemo extends VerticalLayout{

//	private MultiFileMemoryBuffer buffer;
	private MultiFileBuffer buffer;
	private File fileUploaded;

	public UploadDemo() {
		super();
		 buffer = new MultiFileBuffer();
//		 MultiFileBuffer buffer = new MultiFileBuffer();
	        Upload upload = new Upload(buffer);
	        upload.setAutoUpload(true); //自动开始上传

	        UploadExamplesI18N i18n = new UploadExamplesI18N();
	        upload.setI18n(i18n);
//限制文件类型
	        upload.setAcceptedFileTypes("application/pdf", ".pdf",".jpg",".png");
//	        int maxFileSizeInBytes = 100* 1024 * 1024; // 100MB/注意此处设置不一定起作用，spring本身会有大小拦截，需要配置在application.properties中
//	        upload.setMaxFileSize(maxFileSizeInBytes);
//	        upload.setMaxFiles(5);
	        FileReceiverImpl fileReceiverImpl = new FileReceiverImpl("D:\\");
	        //添加监听器
	        upload.addFileRejectedListener(event -> {
	            String errorMessage = event.getErrorMessage();
	            Notification notification = Notification.show(errorMessage, 5000,
	                    Notification.Position.MIDDLE);
	            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
	        });
	        
	        upload.addFinishedListener(fileReceiverImpl);
	        upload.setReceiver(fileReceiverImpl);

	        add(upload);
	        //进度条
	        ProgressBar progressBar = new ProgressBar();
	        progressBar.setIndeterminate(true);

	        Div progressBarLabel = new Div();
	        progressBarLabel.setText("上传进行中，请稍等...");

	        add(progressBarLabel, progressBar);
	        
//	        完成后进行关闭或隐藏进度条
	        Button primaryBtn = RquestComponent.getPrimaryBtn("完成");
//	        primaryBtn.addClickListener(e -> {progressBar.setVisible(false);progressBarLabel.setVisible(false)});
	        primaryBtn.addClickListener(e -> this.remove(progressBar,progressBarLabel));//完成后直接移除进度条组件
	        add(primaryBtn);
	        
	        
	}

}
