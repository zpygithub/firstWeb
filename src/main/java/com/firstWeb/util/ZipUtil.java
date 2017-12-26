package com.firstWeb.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ZipUtil {
    private static final Logger LOGGER = LogManager.getLogger(ZipUtil.class);

    private File zipFile;

    public ZipUtil(String filePath) {
        zipFile = new File(filePath);
    }

    /**
     * 加密压缩
     *
     * @param filePath
     * @param zipPath
     * @param pw
     */
    public void encryptCompress(String filePath, String zipPath, String pw) {
        try {
            ZipFile zipFile = new ZipFile(zipPath); // 创建zip包，指定了zip路径和zip名称
            ArrayList<File> fileAddZip = new ArrayList<>(); // 向zip包中添加文件集合
            fileAddZip.add(new File(filePath)); // 向zip包中添加文件
            ZipParameters parameters = new ZipParameters(); // 设置zip包的一些参数集合
            parameters.setEncryptFiles(true); // 是否设置密码
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 普通级别
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES); // 加密级别
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword(pw); // 压缩包密码
            zipFile.createZipFile(fileAddZip, parameters); // 创建压缩包完成
        } catch (ZipException e) {
            LOGGER.error(e);
        }
    }
}
