package com.itmyhome;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;


/**
 * 文件上传类
 * 2019-05-22
 * @author itmyhome
 *
 */
public class FileAction {
	private File[] file;              //文件  
    private String[] fileFileName;    //文件名   
    private String[] filePath;        //文件路径
    private String downloadFilePath;  //文件下载路径
    private InputStream inputStream; 
    
    public static String BASE_PATH = SystemConfig.getString("ftpBashPath");        //FTP服务器文件上传基本路径
    
    
    /**
     * 使用FTP方式上传
     */
    public void ftpFileUpload(){
    	String suffix;
    	String path = "\\upload";
		File file = new File(path); // 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			if (this.file != null) {
				File f[] = this.getFile();
				filePath = new String[f.length];
				for (int i = 0; i < f.length; i++) {
					String fileName = java.util.UUID.randomUUID().toString(); // 采用时间+UUID的方式随即命名
					String name = fileName + fileFileName[i].substring(fileFileName[i].lastIndexOf(".")); // 保存在硬盘中的文件名

					suffix = suffix(name);
					
					FileInputStream inputStream = new FileInputStream(f[i]);
					
					FtpUtil.uploadFile(BASE_PATH, path, fileName+"."+suffix, inputStream);
					
					inputStream.close();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	
    }
    
    public static String suffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
	}
    /**
     * 普通方式上传
     * @return
     */
	public void fileUpload() {
		String path = ServletActionContext.getServletContext().getRealPath("/upload");
		File file = new File(path); // 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			if (this.file != null) {
				File f[] = this.getFile();
				filePath = new String[f.length];
				for (int i = 0; i < f.length; i++) {
					String fileName = java.util.UUID.randomUUID().toString(); // 采用时间+UUID的方式随即命名
					String name = fileName + fileFileName[i].substring(fileFileName[i].lastIndexOf(".")); // 保存在硬盘中的文件名

					FileInputStream inputStream = new FileInputStream(f[i]);
					FileOutputStream outputStream = new FileOutputStream(path+ "\\" + name);
					byte[] buf = new byte[(int)f[i].length()];
					int length = 0;
					while ((length = inputStream.read(buf)) != -1) {
						outputStream.write(buf, 0, length);
					}
					inputStream.close();
					outputStream.flush();
					//文件保存的完整路径
					// 如：D:\tomcat6\webapps\struts_ajaxfileupload\\upload\a0be14a1-f99e-4239-b54c-b37c3083134a.png
					filePath[i] = path + "\\" + name;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 文件下载
	 * @return
	 */
	public String downloadFile() {
		String path = downloadFilePath;
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			String filenameString = new String(filename.getBytes("gbk"),"iso-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="+ filenameString);
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response
					.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}
	
	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}
	

	public String[] getFilePath() {
		return filePath;
	}

	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getDownloadFilePath() {
		return downloadFilePath;
	}
	
	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}
	
}
