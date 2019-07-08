package com.zxf.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
@Api(value="UploadController",tags= {"上传文件文档接口"})
@RestController
public class UploadController {
	@Value("${file.path}")
	private String realPath;
	
	
	
	/**
	 * 上传单个文件
	 * @param uploadFile
	 * @param request
	 * @return
	*/
	@ApiOperation(value = "一次性上传单个文件")
	@PostMapping("/upload")
	public String upload(MultipartFile uploadFile,HttpServletRequest request) {
//		String realPath = request.getSession().getServletContext().getRealPath("/uploadFile");
		String realPath = "E:/uploadFile";
		System.out.println(request.getSession().getServletContext().toString());
		File dir = new File(realPath);
		if(!dir.isDirectory()) {//文件目录不存在
			dir.mkdirs();//创建文件夹
		}
		try {
			//文件名
			String fileName = uploadFile.getOriginalFilename();
			//服务端保存的文件对象
			File fileServer = new File(dir,fileName);
			System.out.println("file文件真实路劲"+fileServer.getAbsolutePath());
			//实现上传
			uploadFile.transferTo(fileServer);
			String filePath = request.getScheme()+"://"+request.getServerName()
			+":"+request.getServerPort()+"/uploadFile/"+fileName;
			return filePath; 
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "上传失败！";
	}
	/**
	 * 上传多个文件
	 * @param uploadFiles
	 * @param request
	 * @return
	*/
	@ApiOperation(value = "一次性上传多个文件")
	@PostMapping("/uploads")
	public String uploads(MultipartFile[] uploadFiles,HttpServletRequest request) {
		//1、对文件数组做判空
		if(uploadFiles.length<1 || uploadFiles==null) {
			
			return "文件不能为空！！！";
		}
		//2、定义文件的存储路径
		//String realPath = request.getSession().getServletContext().getRealPath("/uploadFile/");
		//String realPath = "E:/uploadFile/";
		//System.out.println(request.getServletContext().getRealPath("")+"/WEB-INF/classes/static/");
		File dir = new File(realPath);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		try {
			//3、遍历文件数组，一个个上传
			for(int i=0;i<uploadFiles.length;i++) {
				MultipartFile uploadFile = uploadFiles[i];
				String fileName = uploadFile.getOriginalFilename();
				//服务端保存文件对象
				File fileServer = new File(dir, fileName);
				System.out.println("file文件真实路劲"+fileServer.getAbsolutePath());
				//4、实现上传
				uploadFile.transferTo(fileServer);
				//http://127.0.0.1:8091/uploadFile/2.JPG
				String filePath = request.getScheme()+"://"+request.getServerName()
								  +":"+request.getServerPort()+"/uploadFile/"+fileName;
			}
			return "上传成功！上传文件至"+realPath;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "上传失败！";
		
	}

}
