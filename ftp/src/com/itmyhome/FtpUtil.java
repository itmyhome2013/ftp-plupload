package com.itmyhome;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

	public static String FTP_HOST = SystemConfig.getString("ftpHost"); // FTP 主机名
	public static int FTP_PORT = Integer.parseInt(SystemConfig.getString("ftpPort")); // FTP端口
	public static String FTP_USERNAME = SystemConfig.getString("ftpUsername"); // FTP 用户名
	public static String FTP_PASSWORD = SystemConfig.getString("ftpPassword"); // FTP  用户密码

	/**
	 * Description: 向FTP服务器上传文件
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input  输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String basePath, String filePath,
			String filename, InputStream input) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(FTP_HOST, FTP_PORT);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			} else {
				ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
				ftp.enterLocalPassiveMode();
			}
			// 切换到上传目录
			if (!ftp.changeWorkingDirectory(filePath)) {
				// 如果目录不存在创建目录
				String[] dirs = filePath.split("\\\\");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir))
						continue;
					tempPath += "\\" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						ftp.makeDirectory(dir);
						ftp.changeWorkingDirectory(dir);
					}
				}
			}
			// 设置上传文件的类型为二进制类型
			ftp.enterLocalPassiveMode();
			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			result = ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

			// 上传文件
			if (!ftp.storeFile(filename, input)) {
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	public static FTPClient getFtp() throws SocketException, IOException {
		FTPClient ftp = new FTPClient();
		ftp.connect(FTP_HOST, FTP_PORT);// 连接FTP服务器
		ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
		ftp.enterLocalPassiveMode();
		return ftp;
	}

	public static void main(String[] args) {
		try {
			System.out.println(getFtp().getReplyCode());
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
