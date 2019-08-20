package com.pig4cloud.pig.tools.oss;

import cn.hutool.core.collection.CollUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.pig4cloud.pig.admin.service.FileClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AlibabaOssService implements FileClient {

    @Value("${alibaba.oss.endpoint:oss-cn-hangzhou.aliyuncs.com}")
    String endpoint;

    @Value("${alibaba.ackid:LTAIGRItbD2wce2M}")
    String accessKeyId;

    @Value("${alibaba.secret:93QkYi3qV1syX00xZEFjm9bcPPfeiz}")
    String accessKeySecret;

    @Autowired
    AlibabaOssConfig alibabaOssConfig;

    @Value("${alibaba.oss.defbucket:fx-open}")
    String defaultBucket;

    @Value("${alibaba.oss.partsize:1}")
    int partSize;

    OSS ossClient = null;
    
    static final String OSS_URI_TEMPLATE = "http://%s.%s/%s";
    static final String TMP_PATH = "fx_tmpfile";

    @PostConstruct
    void initial() {
        log.debug("OSS 准备初始化==");
        ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        if (CollUtil.isEmpty(alibabaOssConfig.getBuckets())) {
            log.error("OSS 存储空间配置为空！");
            throw new RuntimeException("OSS 存储空间配置为空！");
        }
        if (!alibabaOssConfig.getBuckets().contains(defaultBucket)) {
            log.error("OSS 默认存储空间：" + defaultBucket + ",配置不存在!,AllBuckets:" + alibabaOssConfig.getBuckets());
            throw new RuntimeException("OSS 默认存储空间：" + defaultBucket + ",配置不存在!,AllBuckets:" + alibabaOssConfig.getBuckets());
        }
        log.debug("OSS 已初始化完成！");
        
    }

    @Override
    public String upload(byte[] bytes, String fileName) {
        // 默认文件上传接口，额外生成存储URL(不体现原文件名信息)，允许重名
        String ext = getFileExtension(fileName);
        String newFileName = UUID.randomUUID().toString() + ext;
        return uploadByDomain(bytes, newFileName, defaultBucket);
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

    @Override
	@Deprecated
    public byte[] download(String path) {
        String[] keyParts = url2Key(path);
        if (keyParts != null && keyParts.length == 2) {
            File tmpDir = new File(System.getProperty("java.io.tmpdir") + File.separator + TMP_PATH);
            if (!tmpDir.exists())
                tmpDir.mkdir();
            String absFilePath = tmpDir.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + ".tmp";
            // Download From Oss
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest(keyParts[0], keyParts[1]);
            downloadFileRequest.setDownloadFile(absFilePath);
            downloadFileRequest.setTaskNum(1);
            downloadFileRequest.setPartSize(1024 * 1024 * 1);
//            downloadFileRequest.setEnableCheckpoint(true);
            DownloadFileResult downloadResult;
            try {
                downloadResult = ossClient.downloadFile(downloadFileRequest);
                ObjectMetadata objectMetadata = downloadResult.getObjectMetadata();
                log.debug("成功下载文件：" + path + ",details:" + objectMetadata);
                byte[] cBytes = IOUtils.toByteArray(new FileInputStream(absFilePath));
                new File(absFilePath).deleteOnExit();
                return cBytes;
            } catch (Throwable e) {
                log.error("下载文件：" + path + ",出错！", e);
                throw new RuntimeException("下载文件：" + path + ",出错!");
            }
        }
        throw new RuntimeException("下载文件：" + path + ",出错,名称不符合规范!");
    }

    String[] url2Key(String url) {
        String[] pts = null;
        url = url.replace("https://", "").replace("http://", "");
        int isx = url.indexOf("."), ipx = url.indexOf("/");
        if (isx > -1 && ipx > isx) {
            pts = new String[2];
            pts[0] = url.substring(0, isx);
            pts[1] = url.substring(ipx + 1);
        }
        return pts;
    }

    @Override
    public void delete(String path) {
        String[] keyParts = url2Key(path);
        if (keyParts != null && keyParts.length == 2) {
            DeleteObjectsResult deleteObjectsResult = ossClient
                .deleteObjects(new DeleteObjectsRequest(keyParts[0]).withKeys(CollUtil.newArrayList(keyParts[1])));
            List<String> delList = deleteObjectsResult.getDeletedObjects();
            log.debug("成功删除文件：" + delList);
            return ;
        }
        throw new RuntimeException("要删除的文件：" + path + ",出错,名称不符合规范!");
    }

    @Override
    public String uploadByDomain(byte[] bytes, String fileName, String domain) {
        // 默认文件上传接口，生成OSS可互联网访问的URL(按文件名信息定位)，不允许重名
        return upload2Oss(bytes, fileName, domain);
    }

    /**
     * 上传原始文件字节信息至OSS
     * 
     * @param bytes
     * @param fileName
     * @param bucket
     * @return
     */
    String upload2Oss(byte[] bytes, String fileName, String bucket) {
        if (!alibabaOssConfig.getBuckets().contains(bucket)) {
            log.error("OSS 请求的存储空间：" + bucket + ",配置不存在!,AllBuckets:" + alibabaOssConfig.getBuckets());
            throw new RuntimeException("OSS 请求的存储空间：" + bucket + ",配置不存在!,AllBuckets:" + alibabaOssConfig.getBuckets());
        }
        AppendObjectResult appendObjectResult = ossClient
            .appendObject(new AppendObjectRequest(bucket, fileName, new ByteArrayInputStream(bytes)).withPosition(0L));

        log.debug("文件：" + fileName + ",上传成功！ details:position:" + appendObjectResult.getNextPosition() + ",CRC64="
            + appendObjectResult.getObjectCRC());

        return String.format(OSS_URI_TEMPLATE, bucket, endpoint, fileName);
    }

    @PreDestroy
    void destory() {
        log.debug("OSS 准备销毁==");
        if (ossClient != null)
            ossClient.shutdown();
        log.debug("OSS 已完成销毁！");
    }

}
