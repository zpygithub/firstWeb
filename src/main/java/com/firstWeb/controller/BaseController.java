package com.firstWeb.controller;

import com.firstWeb.bean.model.Token;
import com.firstWeb.bean.response.PageInfo;
import com.firstWeb.util.ParamValidateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class BaseController {
    private static final Logger LOGGER = LogManager.getLogger(BaseController.class);

    private static final int MINCURRENTPAGE = 1;

    private static final int MAXNUMFORSEARCH = 100;

    public Token getToken(HttpServletRequest request) {
        Token token = null;
        Object tokenObj = request.getAttribute("Attr_Token");
        if (null != tokenObj) {
            token = (Token) tokenObj;
        }
        return token;
    }

    public PageInfo createPageInfo(String page, String size) {
        PageInfo pageInfo = new PageInfo();
        if ((!StringUtils.isEmpty(page) && ParamValidateUtil.isNumber(page)) && (!StringUtils.isEmpty(size) && ParamValidateUtil.isNumber(size))) {
            int pageInt = Integer.parseInt(page);
            int sizeInt = Integer.parseInt(size);
            pageInfo.setCurrentPage(pageInt);
            pageInfo.setPerPageRecords(sizeInt);
            pageInfo.setFrom(pageInt, sizeInt);
        } else {
            pageInfo.setCurrentPage(MINCURRENTPAGE);
            pageInfo.setPerPageRecords(MAXNUMFORSEARCH);
            pageInfo.setFrom(MINCURRENTPAGE, MAXNUMFORSEARCH);
        }
        return pageInfo;
    }

    protected void downloadToPage(String path, HttpServletResponse response) {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        File file = new File(path);
        String filename = file.getName();
        // 以流的形式下载文件。
//            InputStream fis = new BufferedInputStream(new FileInputStream(path));
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            fis.close();
        // 清空response
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setContentType("application/octet-stream");
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] b = new byte[4096];
            int count = 0;
            while ((count = in.read(b, 0, b.length)) != -1) {
                out.write(b, 0, count);
            }
            out.flush();
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            if (null != in) {
                closeStream(in, out);
            }
        }
    }

    private static void closeStream(InputStream in, OutputStream out) {
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            LOGGER.error("bufferedOutputStream close failed" + e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                LOGGER.error("bufferedInputStream close failed" + e);
            }
        }
    }
}
