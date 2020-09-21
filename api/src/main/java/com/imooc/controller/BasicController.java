package com.imooc.controller;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.RedisOperator;
import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
@ComponentScan(value = "utils")
public class BasicController {

	@Autowired
	public RedisOperator redis;

	static final String USER_REDIS_SESSION = "user-redis-session";

	//文件保存的命名空间
	static final String FILE_SPACE="C:/imooc_videos_dev";
	//ffmpeg的位置
	static final String FFMPEG_EXE="C:/Program Files/ffmpeg/bin/ffmpeg.exe";
	//每页分页的记录数
	static final int PAGE_SIZE=5;

	boolean saveFile(String path, MultipartFile file) throws IOException {
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		File outFile = new File(path);
		if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
			// 创建父文件夹
			outFile.getParentFile().mkdirs();
		}
		try {
			fileOutputStream = new FileOutputStream(outFile);
			inputStream = file.getInputStream();
			IOUtils.copy(inputStream, fileOutputStream);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		return true;
	}

	String readFile(String path) {
		String FileContent="";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				FileContent += line;
				FileContent += "\r\n"; // 补上换行符
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return FileContent;
	}

	boolean deleteFile(String path){
		File file=new File(path);
		if (file.exists() && file.isFile()) {
			return file.delete();
		} else {
			return false;
		}
	}
}
