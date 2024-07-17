package com.example.application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hutool.system.SystemUtil;

/**
 * 文件下载接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("file")
public class FileController {

	// 用来处理文件下载
	// 请求对应响应输出流${pageContext.request.contextPath}/file/download?fileName=aa.txt
	/**
	 * // 4. 附件下载 attachment 附件 inline 在线打开(默认值) // 在线打开
	 * response.setHeader("content-disposition", inline + ";fileName=" + fileName);
	 * // 附件下载 response.setHeader("content-disposition", attachment + ";fileName=" +
	 * fileName);
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("download")
	public void download(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("下载文件的名称：" + fileName);
		// 1. 根据下载相对目录获取下载目录在服务器部署之后绝对目录/或者配置到配置文件进行读取
//        String realPath = request.getSession().getServletContext().getRealPath("/mybatis");
		String currentDir = SystemUtil.getUserInfo().getCurrentDir() + "\\frontend\\file";

		FileInputStream is = null;
		ServletOutputStream os = null;
		try {
			// 2. 通过文件输入流读取文件
			is = new FileInputStream(new File(currentDir, fileName));
			// 3. 获取响应输出流
			response.setContentType("text/plain;charset=UTF-8");
			// 4. 附件下载 attachment 附件 inline 在线打开(默认值)
			response.setHeader("content-disposition", "attachment;fileName=" + fileName);
//        response.setHeader("content-disposition", "inline" + ";fileName=" + URLEncoder.encode(fileName, "UTF-8"));//如果文件名有中文，需要主动进行编码处理

			// 5. 处理下载流复制
			os = response.getOutputStream();
			// 5. 处理下载流复制 // 操作io流用IOUtils 操作file用FileUtils
			// 通过工具类处理下载里复制和关闭流
//        IOUtils.copy(is, os);
//        IOUtils.closeQuietly(is);   // 优雅关闭 安静关流
//        IOUtils.closeQuietly(os);   // 优雅关闭 安静关流
			int len;
			byte[] b = new byte[1024];
			while (true) {
				len = is.read(b);
				if (len == -1)
					break;
				os.write(b, 0, len);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			os.close();
			is.close();
		}
	}
	/**
	 * 第二种下载文件的方法
	 * @param fileName
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/download2")
    public ResponseEntity<byte[]> test(String fileName, HttpServletRequest request) throws IOException{
//        String serverpath= request.getRealPath("/uploadFile");
        String currentDir = SystemUtil.getUserInfo().getCurrentDir() + "\\frontend\\file\\"+fileName;
        //真实路径
//        serverpath=serverpath+"/"+filename;
        //创建http头信息的对象
        HttpHeaders header=new HttpHeaders();
        //标记以流的方式做出响应
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        header.add("content-disposition", "attachment;fileName=" + fileName);
        //设置以弹窗的方式提示用户下载
       //attachment 表示以附件的形式响应给客户端
       // header.setContentDispositionFormData("attachment", URLEncoder.encode(filename,"utf-8"));
        File f=new File(currentDir);
        ResponseEntity<byte[]> resp=
                new ResponseEntity<byte[]>
                        (FileUtils.readFileToByteArray(f), header, HttpStatus.CREATED);
        return resp;
    }

	@RequestMapping("test")
	public String test() {
		return "success";
	}
}
