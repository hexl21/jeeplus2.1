package com.jeeplus.wxapi.util;

import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.modules.weixin.entity.WxAccount;
import com.jeeplus.wxapi.process.WxApi;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeixinFileUtil {
    private static Logger logger = Logger.getLogger(WeixinFileUtil.class.getName());


    // 素材下载:不支持视频文件的下载
    private static final String DOWNLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";


    public static String getDownloadUrl(String token, String mediaId) {
        return String.format(DOWNLOAD_MEDIA, token, mediaId);
    }

    /**
     * 以http方式发送请求,并将请求响应内容输出到文件
     *
     * @param path   请求路径
     * @param method 请求方法
     * @param body   请求数据
     * @return 返回响应的存储到文件
     */
    public static File httpRequestToFile(String fileName, String path, String method, String body) {
        if (fileName == null || path == null || method == null) {
            return null;
        }

        File file = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            inputStream = conn.getInputStream();
            if (inputStream != null) {
                file = new File(fileName);
            } else {
                return file;
            }

            //写入到文件
            FileOutputStream fileOut = new FileOutputStream(file);
            if (fileOut != null) {
                int c = inputStream.read();
                while (c != -1) {
                    fileOut.write(c);
                    c = inputStream.read();
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return file;
    }

    /**
     * 多媒体下载接口
     *
     * @param fileName 素材存储文件路径
     * @param mediaId  素材ID（对应上传后获取到的ID）
     * @return 素材文件
     * @comment 不支持视频文件的下载
     */
    public static File downloadMedia(String fileName,
                                     String mediaId) {
        WxAccount wxAccount = (WxAccount) CacheUtils.get("WxAccount");//获取缓存中的唯一账号
        String tocken = WxApi.getTokenUrl(wxAccount.getAppid(), wxAccount.getAppsecret());
        String url = getDownloadUrl(tocken, mediaId);
        return httpRequestToFile(fileName, url, "GET", null);
    }
}