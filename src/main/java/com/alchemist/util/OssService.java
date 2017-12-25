package com.alchemist.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.joda.time.DateTime;

import java.io.*;

/**
 * Created by Baowen on 2017/12/23.
 */
public class OssService {

//    private static String endpoint = PropertiesUtil.getProperty("endpoint");
//    private static String accessKeyId = PropertiesUtil.getProperty("accessKeyId");
//    private static String accessKeySecret = PropertiesUtil.getProperty("accessKeySecret");
//    private static String bucket = PropertiesUtil.getProperty("bucket");

    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "RzRNcJnObRvLin4z";
    private static String accessKeySecret = "WU9J0hvuFXzfDCo6z7Dj6gTw54q4Uf";
    private static String bucket = "alchemist-chmall-private";

    private static OSSClient ossClient;

    static {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    public static String updateImage(String path, byte[] bytes) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ossClient.putObject(bucket, path, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static byte[] getImage(String filePath) {
        byte[] imageBytes;
        OSSObject object = ossClient.getObject(bucket, filePath);
        InputStream inputStream = object.getObjectContent();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n = 0;
        try {
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageBytes = out.toByteArray();
        return imageBytes;
    }


    public static void main(String[] args) throws IOException {
//        byte[] bytes = getBytes("F:\\1.jpg");
        String path = "conghua_mall/header/1.jpg";
//        updateImage(path, bytes);

        byte[] bytes1 = getImage(path);
        OutputStream out = new FileOutputStream("F://idcard//" + new DateTime().toString("yyyyMMddHHmmss") + ".jpg");
        out.write(bytes1);
        out.flush();
        out.close();

    }
}
