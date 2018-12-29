package com.jeeplus.wxapi.api;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.wxapi.exception.WxError;
import com.jeeplus.wxapi.exception.WxErrorException;
import com.jeeplus.wxapi.util.HttpClientUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MediaApi {
    // 获取批量素材
    public static final String GET_BATCH_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=%s";

    // 上传多媒体资料接口-临时
    public static final String UPLOAD_MEDIA = "http://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";

    // 上传永久素材：图文-临时
    public static final String UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=%s";

    // 上传永久图片素材
    public static final String UPLOAD_MATERIAL_IMG = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=%s";

    // 新增其他类型永久素材
    public static final String ADD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=%s&type=%s";

    // 新增永久图文素材
    public static final String ADD_NEWS_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=%s";

    // 根据media_id来获取永久素材
    public static final String GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=%s";

    //获取临时素材  GET
    public static final String GET_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    // 根据media_id来删除永久图文素材
    public static final String DELETE_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=%s";

    // 修改永久图文url
    public static final String UPDATE_NEWS_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=%s";


    // 获取上传Media接口
    public static String getUploadMediaUrl(String token, String type) {
        return String.format(UPLOAD_MEDIA, token, type);
    }

    //素材文件后缀
    public static Map<String, String> type_fix = new HashMap<>();
    public static Map<String, String> media_fix = new HashMap<>();
    //素材文件大小
    public static Map<String, Long> type_length = new HashMap<>();

    static {
        type_fix.put("image", "bmp|png|jpeg|jpg|gif");
        type_fix.put("voice", "mp3|wma|wav|amr");
        type_fix.put("video", "mp4");
        type_fix.put("thumb", "bmp|png|jpeg|jpg|gif");
        media_fix.put("image", "png|jpeg|jpg|gif");
        media_fix.put("voice", "mp3|amr");
        media_fix.put("video", "mp4");
        media_fix.put("thumb", "jpg");
        type_length.put("image", new Long(2 * 1024 * 1024));
        type_length.put("voice", new Long(2 * 1024 * 1024));
        type_length.put("video", new Long(10 * 1024 * 1024));
        type_length.put("thumb", new Long(2 * 1024 * 1024));
    }

    /**
     * 上传多媒体文件
     * 返回media_id
     */
    public static String uploadMedia(String accessToken, String mediaType, String mediaUri) {
        String uploadMediaUrl = String.format(UPLOAD_MEDIA, accessToken, mediaType);
        String boundary = "----------" + System.currentTimeMillis();// 设置边界
        try {
            URL uploadUrl = new URL(uploadMediaUrl);
            HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
            uploadConn.setDoOutput(true);
            uploadConn.setDoInput(true);
            uploadConn.setRequestMethod("POST");
            // 设置请求头Content-Type
            uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            // 获取媒体文件上传的输出流（往微信服务器写数据）
            OutputStream outputStream = uploadConn.getOutputStream();

            URL mediaUrl = new URL(mediaUri);
            HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
            meidaConn.setDoOutput(true);
            meidaConn.setRequestMethod("GET");

            // 从请求头中获取内容类型
            String contentType = meidaConn.getHeaderField("Content-Type");
            // 根据内容类型判断文件扩展名
            String fileExt = ".jpg";
            // 请求体开始
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(
                    String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n", fileExt)
                            .getBytes());
            outputStream.write(String.format("Content-Type: %s\r\n\r\n", contentType).getBytes());

            // 获取媒体文件的输入流（读取文件）
            BufferedInputStream bis = new BufferedInputStream(meidaConn.getInputStream());
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1) {
                outputStream.write(buf, 0, size);
            }
            // 请求体结束
            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
            outputStream.close();
            bis.close();
            meidaConn.disconnect();

            // 获取媒体文件上传的输入流（从微信服务器读数据）
            InputStream inputStream = uploadConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            uploadConn.disconnect();
            // 使用JSON-lib解析返回结果
            JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
            if (jsonObject.containsKey("media_id"))
                return jsonObject.getString("media_id");
            return null;
        } catch (Exception e) {
            String error = String.format("上传媒体文件失败：%s", e);
        }
        return null;
    }

    /**
     * 新增图文永久素材
     *
     * @param materialUri
     * @param filePath
     * @return
     * @throws Exception
     */
    public static JSONObject addMaterial(String materialUri, String filePath) throws Exception {
        String result = null;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            try {
                throw new IOException("文件不存在");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**
         * 第一部分
         */
        URL urlObj = new URL(materialUri);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
            JSONObject jsonObject = JSONObject.parseObject(result.toString());

            if (jsonObject != null) {
                return jsonObject;
            }
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return null;
    }

    /**
     * 这里说下，在上传视频素材的时候，微信说不超过20M，我试了下，超过10M调通的可能性都比较小，建议大家上传视频素材的大小小于10M比交好
     *
     * @param accessToken
     * @param file         上传的文件
     * @param title        title
     * @param introduction 上传类型为video的参数
     * @param type         video/mp4
     */
    public static JSONObject uploadMediaFiles(String accessToken, File file, String title, String introduction, String type) {
        try {

            //这块是用来处理如果上传的类型是video的类型的
            JSONObject j = new JSONObject();
            j.put("title", title);
            j.put("introduction", introduction);
            String urlStr = String.format(ADD_MATERIAL, accessToken, type);
            URL url = new URL(urlStr);
            String result = null;
            long filelength = file.length();
            String fileName = file.getName();

            /**
             *  你们需要在这里根据文件后缀suffix将type的值设置成对应的mime类型的值
             */
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false); // post方式不能使用缓存
            // 设置请求头信息
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");

            // 设置边界,这里的boundary是http协议里面的分割符，不懂的可惜百度(http 协议 boundary)，这里boundary 可以是任意的值(111,2222)都行
            String BOUNDARY = "----------" + System.currentTimeMillis();
            con.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            // 请求正文信息
            // 第一部分：

            StringBuilder sb = new StringBuilder();


            //这块是post提交type的值也就是文件对应的mime类型值
            sb.append("--"); // 必须多两道线 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，不懂的可以看看http 协议头
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"type\" \r\n\r\n"); //这里是参数名，参数名和值之间要用两次
            sb.append(type + "\r\n"); //参数的值

            //这块是上传video是必须的参数，你们可以在这里根据文件类型做if/else 判断
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"description\" \r\n\r\n");
            sb.append(j.toString() + "\r\n");

            /**
             * 这里重点说明下，上面两个参数完全可以卸载url地址后面 就想我们平时url地址传参一样，
             * http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##&type=""&description={} 这样，如果写成这样，上面的
             * 那两个参数的代码就不用写了，不过media参数能否这样提交我没有试，感兴趣的可以试试
             */

            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            //这里是media参数相关的信息，这里是否能分开下我没有试，感兴趣的可以试试
            sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
                    + fileName + "\";filelength=\"" + filelength + "\" \r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            System.out.println(sb.toString());
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream out = new DataOutputStream(con.getOutputStream());
            // 输出表头
            out.write(head);
            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();
            // 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            out.close();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
            // 使用JSON-lib解析返回结果
            JSONObject jsonObject = JSONObject.parseObject(result);
            System.out.println("json:" + jsonObject.toString());
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    /**
     * 永久素材添加-不包含图文
     *
     * @param accessToken 微信token
     * @param type        素材类型（image/voice/video/thumb）
     * @param fileUrl     文件的绝对路径
     * @param params      视频数据
     * @return
     * @throws WxErrorException
     */
    public static JSONObject addMateria(String accessToken, String type, String fileUrl, Map<String, String> params) throws WxErrorException {

        File file = new File(fileUrl);
        if (!file.exists()) {
            throw new WxErrorException(WxError.newBuilder().setErrorCode(-2).setErrorMsg("文件不存在").build());
        }
        String fileName = file.getName();
        //获取后缀名
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        long length = file.length();
        //此处做判断是为了尽可能的减少对微信API的调用次数
        if (type_fix.get(type).indexOf(suffix) == -1) {
            throw new WxErrorException(WxError.newBuilder().setErrorCode(40005).setErrorMsg("不合法的文件类型").build());
        }
        if (length > type_length.get(type)) {
            throw new WxErrorException(WxError.newBuilder().setErrorCode(40006).setErrorMsg("不合法的文件大小").build());
        }
        String url = String.format(ADD_MATERIAL, accessToken, type);
        String result = HttpClientUtils.sendHttpPost(url, file, params);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }

        return JSONObject.parseObject(result);
    }

    /**
     * 永久素材下载-包含图片、语音、缩略图
     *
     * @param accessToken 微信token
     * @param mediaId     多媒体素材ID
     * @param file        文件夹目录 例如D://down
     * @return
     * @throws WxErrorException
     */
    public static File downlodMateria(String accessToken, String mediaId, File file)
            throws WxErrorException {
        String url = String.format(GET_MATERIAL, accessToken);
        Map map = new HashMap();
        map.put("media_id", mediaId);
        Object obj = HttpClientUtils.sendHttpPostFile(url, map, file);
        if (obj instanceof String) {
            WxError wxError = WxError.fromJson((String) obj);
            throw new WxErrorException(wxError);
        }
        if (null == obj) {
            throw new WxErrorException(WxError.newBuilder().setErrorCode(-3).setErrorMsg("下载出错").build());
        }

        return (File) obj;
    }

    /**
     * 临时素材添加
     *
     * @param accessToken 微信token
     * @param type        素材类型（image/voice/video/thumb）
     * @param fileUrl     文件的绝对路径
     * @return
     * @throws WxErrorException
     */
    public static JSONObject addMedia(String accessToken, String type, String fileUrl) throws WxErrorException {

        File file = new File(fileUrl);
        if (!file.exists()) {
            throw new WxErrorException(WxError.newBuilder().setErrorCode(-2).setErrorMsg("文件不存在").build());
        }
        String fileName = file.getName();
        //获取后缀名
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        //文件大小，单位：b
        long length = file.length();
        //此处做判断是为了尽可能的减少对微信API的调用次数
        if (media_fix.get(type).indexOf(suffix) == -1) {
            throw new WxErrorException(WxError.newBuilder().setErrorCode(40005).setErrorMsg("不合法的文件类型").build());
        }
        if (length > type_length.get(type)) {
            throw new WxErrorException(WxError.newBuilder().setErrorCode(40006).setErrorMsg("不合法的文件大小").build());
        }
        String url = String.format(UPLOAD_MEDIA, accessToken, type);
        String result = HttpClientUtils.sendHttpPost(url, file, null);
        WxError wxError = WxError.fromJson(result);
        if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
        }

        return JSONObject.parseObject(result);
    }

    // 获取永久素材
    public static String getMaterial(String token) {
        return String.format(GET_MATERIAL, token);
    }

    // 删除永久图文素材
    public static String getDelMaterialURL(String token) {
        return String.format(DELETE_MATERIAL, token);
    }

    // 获取新增图文素材url
    public static String getNewsMaterialUrl(String token) {
        return String.format(ADD_NEWS_MATERIAL, token);
    }

    // 获取修改图文素材url
    public static String getUpdateNewsMaterialUrl(String token) {
        return String.format(UPDATE_NEWS_MATERIAL, token);
    }

    // 上传永久图片素材
    public static String getMaterialImgUrl(String token) {
        return String.format(UPLOAD_MATERIAL_IMG, token);
    }

    // 获取新增素材url
    public static String getMaterialUrl(String token, String type) {
        return String.format(ADD_MATERIAL, token, type);
    }

}
