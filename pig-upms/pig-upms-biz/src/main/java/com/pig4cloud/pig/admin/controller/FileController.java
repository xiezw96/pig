package com.pig4cloud.pig.admin.controller;

import cn.hutool.core.codec.Base64;
import com.pig4cloud.pig.admin.service.FileClient;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * <p>Title: FileController</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月03日
 * @since 1.8
 */
@Api(value = "filenamager", tags = {"文件管理"})
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
	@Autowired
	private FileClient fileClient;

	@Value("${alibaba.oss.maskbucket:fx-mask}")
	String maskBucket;

	@ApiOperation(value = "文件上传", notes = "文件上传")
	@PostMapping
	public R<?> upload(@RequestParam("file") MultipartFile multipartFile) {

		if (multipartFile != null && !multipartFile.isEmpty()) {
			try {
				String path = fileClient.upload(multipartFile.getBytes(), multipartFile.getOriginalFilename());
				return new R<>(Base64.encode(path));
			} catch (Exception e) {
				log.error("文件上传异常，" + e.getMessage(), e);
				return new R<>(e);
			}
		} else {
			return new R<>(Boolean.FALSE, "文件为空");
		}
	}
	
	@ApiOperation(value = "敏感文件上传接口", notes = "敏感文件上传接口")
	@PostMapping(value="/mask")
	public R<?> uploadByDomain(@RequestParam("file") MultipartFile multipartFile) {

		if (multipartFile != null && !multipartFile.isEmpty()) {
			try {
				String ext = fileClient.getFileExtension(multipartFile.getOriginalFilename());
				String newFileName = UUID.randomUUID().toString() + ext;
				String path = fileClient.uploadByDomain(multipartFile.getBytes(), newFileName, maskBucket);
				return new R<>(Base64.encode(path));
			} catch (Exception e) {
				log.error("文件上传异常，" + e.getMessage(), e);
				return new R<>(e);
			}
		} else {
			return new R<>(Boolean.FALSE, "文件为空");
		}
	}

	@ApiOperation(value = "下载文件", notes = "下载文件")
	@GetMapping(value = "/{p}")
	public ResponseEntity download(@PathVariable("p") @ApiParam("路径") String path, @RequestParam(name = "n", required = false) @ApiParam("文件名") String fileName) {
		try {
			String newPath = Base64.decodeStr(path);
			String ext = fileClient.getFileExtension(newPath);
			if (StringUtils.isEmpty(fileName)) {
				fileName = System.currentTimeMillis() + ext;
			} else {
				fileName = new String(fileName.getBytes(), "ISO-8859-1") + ext;
			}
			byte[] buffer = fileClient.download(newPath);
			if (buffer == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(buffer.length).header("Content-Disposition", "attachment;filename=" + fileName).body(buffer);
		} catch (Exception e) {
			log.error("文件下载异常，" + e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation(value = "删除文件", notes = "删除文件")
	@DeleteMapping(value = "/{p}")
	public R delete(@PathVariable("p") @ApiParam("路径") String path) {
		try {
			fileClient.delete(Base64.decodeStr(path));
			return new R<>(Boolean.TRUE);
		} catch (Exception e) {
			log.error("文件删除异常，" + e.getMessage(), e);
			return new R<>(e);
		}
	}

}
