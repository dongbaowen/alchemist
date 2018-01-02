package com.alchemist.util;

import com.alchemist.common.FileTypes;
import com.aliyun.oss.OSSClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.UUID;

/**
 * Created by Baowen on 2017/12/23.
 */
public class OssService {

    private static String endpoint = PropertiesUtil.getProperty("aliyun.endpoint");
    private static String accessKeyId = PropertiesUtil.getProperty("aliyun.accessKeyId");
    private static String accessKeySecret = PropertiesUtil.getProperty("aliyun.accessKeySecret");
    private static String bucket = PropertiesUtil.getProperty("aliyun.bucket");
    private static String URL_PREFIX = "http://" + bucket + "." + "oss-cn-beijing.aliyuncs.com/";

    private static final Logger logger = LoggerFactory.getLogger(OssService.class);

    private static OSSClient ossClient;

    static {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    public static String updateImage(FileTypes type, String fileName, byte[] bytes) {
        DateTime now = new DateTime();
        String path = String.format(type.getPath(), now.toString("yyyyMMddHHmmss"), fileName);
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ossClient.putObject(bucket, path, inputStream);
        } catch (Exception e) {
            logger.error("OSS上传文件异常。", e);
        }
        return getFilePath(type, fileName, now);
    }

    public static String uploadImage(FileTypes type, String fileName, MultipartFile file) {
        DateTime now = new DateTime();
        String path = getFilePath(type, fileName, now);
        try {
            ossClient.putObject(bucket, path, file.getInputStream());
        } catch (Exception e) {
            logger.error("OSS上传文件异常。", e);
        }
        return URL_PREFIX + path;
    }

    /**
     * 获取图片路径
     * http://alchemist-chmall-private.oss-cn-beijing.aliyuncs.com/conghua_mall/header/1.jpg
     *
     * @return
     */
    private static String getFilePath(FileTypes type, String fileName, DateTime time) {
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        fileName = UUID.randomUUID().toString().replaceAll("-", "") + time.toString("yyyyMMddHHmmss");
        return String.format(type.getPath(), fileName + suffix);
    }

    public static void main(String[] args) {
        String fileName = "asndf.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        System.out.println(suffix);
    }
}
