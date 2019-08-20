package com.pig4cloud.pig.admin.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.pig4cloud.pig.admin.service.FileClient;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title: DiskFileClient</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月03日
 * @since 1.8
 */
@Slf4j
public class DiskFileClient implements FileClient {

	@Value("${file.disk.path:}")
	private String filePath;
	@Value("${file.disk.pathFormat:yyyy-MM}")
	private String pathFormat;

	@PostConstruct
	void init() {
		if (StringUtils.isEmpty(filePath)) {
			filePath = SystemUtil.get(SystemUtil.USER_HOME) + File.separator + "uploadFiles";
		}
	}

	@Override
	public String upload(byte[] bytes, String fileName) {
		String ext = getFileExtension(fileName);
		String newFileName = UUID.randomUUID().toString() + ext, lastFilePath = DateUtil.format(new Date(), pathFormat) + File.separator;
		saveAsFile(bytes,newFileName,this.filePath + File.separator + lastFilePath);
		return lastFilePath + newFileName;
	}
	
	private void  saveAsFile(byte[] bytes, String fileName,String absPath){
		File dir = new File(absPath);
		dir.mkdirs();
		try {
			IoUtil.write(new FileOutputStream(new File(dir, fileName)), true, bytes);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public byte[] download(String lastFilePath) {
		File file = new File(filePath + File.separator + lastFilePath);
		if (file.exists() && file.isFile()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				return IoUtil.readBytes(fis);
			} catch (FileNotFoundException e) {
				return null;
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		} else {
			return null;
		}
	}

	@Override
	public void delete(String lastFilePath) {
		File file = new File(filePath + File.separator + lastFilePath);
		if (file.exists() && file.isFile())
			if (!file.delete())
				log.error("文件删除失败，{}", lastFilePath);

	}

	@Override
	public String getFileExtension(String path) {
		String ext = "", separator = ".";
		if (StringUtils.hasText(path) && path.indexOf(separator) > -1) {
			int idx = path.lastIndexOf(separator);
			ext = path.substring(idx);
		}
		if (StringUtils.isEmpty(ext)) {
			ext = ".tmp";
		}
		return ext;
	}

	public String uploadByDomain(byte[] bytes, String fileName, String domain) {
		String ext = getFileExtension(fileName);
		String newFileName = UUID.randomUUID().toString() + ext, lastFilePath = DateUtil.format(new Date(), pathFormat) + File.separator;
		domain = StrUtil.isNotBlank(domain) ? domain + File.separator : "";
		saveAsFile(bytes,newFileName,this.filePath + File.separator + domain + lastFilePath);
		return domain + lastFilePath + newFileName;
	}
}
