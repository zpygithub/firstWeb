package com.firstWeb.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zpy on 2017/7/26.
 */
public class ZipUtil {
    public static void main(String[] args) {
        try {
            ZipFile zipFile = new ZipFile("f:\\test\\testZip.zip"); // 創建zip包，指定了zip路徑和zip名稱
            ArrayList<File> fileAddZip = new ArrayList<File>(); // 向zip包中添加文件集合
            fileAddZip.add(new File("f:\\test\\yangjinhua.doc")); // 向zip包中添加一个word文件
            ZipParameters parameters = new ZipParameters(); // 设置zip包的一些参数集合
            parameters.setEncryptFiles(true); // 是否设置密码（此处设置为：是）
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式(默认值)
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 普通级别（参数很多）
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES); // 加密级别
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);

            parameters.setPassword("123456"); // 压缩包密码为123456
            zipFile.createZipFile(fileAddZip, parameters); // 创建压缩包完成
        } catch (ZipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
