package com.pig4cloud.pig.admin.service;

/**
 * <p>Title: FileClient</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月03日
 * @since 1.8
 */
public interface FileClient {

	String upload(byte[] bytes, String fileName);

	String getFileExtension(String path);

	byte[] download(String path);

	void delete(String path);
	
    public String uploadByDomain(byte[] bytes, String fileName, String domain);
}
