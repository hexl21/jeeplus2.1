package com.jeeplus.modules.weixin.util;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.core.web.Servlets;
import com.jeeplus.wxapi.process.MediaType;
import com.jeeplus.wxapi.process.WxApi;
import com.jeeplus.wxapi.util.JSONUtil;
import com.jeeplus.wxapi.util.WeixinFileUtil;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Controller
@RequestMapping(value = "/weixin/attach/")
public class WxAttchController {

    @ResponseBody
    @RequestMapping({"upfile"})
    public void upfile(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
            throws Exception {
        String json = null;
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> result = new HashMap();
        try {
            String attUrl = null;
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile imgFile = multipartRequest.getFile(key);
                System.out.print(imgFile.getSize());
                if (imgFile.getOriginalFilename().length() > 0) {
                    String originName = imgFile.getOriginalFilename();
                    // 取得文件后缀
                    String fileExt = originName.substring(originName.lastIndexOf(".") + 1);
                    // 文件名
                    originName = System.currentTimeMillis() + "." + fileExt;

                    String realPath = Global.USERFILES_BASE_URL + "upload/" +
                            DateUtils.getYear() + "/" + DateUtils.getMonth() + "/" + DateUtils.getDay() + "/";
                    System.out.println(Global.getUserfilesBaseDir());
                    FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);

                    File file = new File(Global.getUserfilesBaseDir() + realPath + originName);
                    imgFile.transferTo(file);
                    if (WxApi.type_fix.get(MediaType.Voice.toString()).indexOf(fileExt) > -1) {
                        Encoder encoder = new Encoder();
                        MultimediaInfo m = encoder.getInfo(file);
                        long duration = m.getDuration() / 1000;
                        if (duration > 60 || file.length() > WxApi.type_length.get(MediaType.Voice.toString())) {
                            result.put("success", false);
                            result.put("msg", "上传失败，音频大小不能超过2M，播放长度不超过60s");
                            json = JSONUtil.writeObject2Json(result);
                            response.getWriter().print(json);
                        } else {
                            attUrl = realPath + originName;
                            result.put("filesPath", file);
                            result.put("success", true);
                            result.put("msg", "上传成功");
                            result.put("code", "-1");
                            result.put("AttUrl", attUrl);
                            json = JSONUtil.writeObject2Json(result);
                            response.getWriter().print(json);
                        }
                    } else if (WxApi.type_fix.get(MediaType.Video.toString()).indexOf(fileExt) > -1) {
                        if (file.length() > WxApi.type_length.get(MediaType.Video.toString())) {
                            result.put("success", false);
                            result.put("msg", "上传失败，视频大小不能超过10M");
                            json = JSONUtil.writeObject2Json(result);
                            response.getWriter().print(json);
                        } else {
                            attUrl = realPath + originName;
                            result.put("filesPath", file);
                            result.put("success", true);
                            result.put("msg", "上传成功");
                            result.put("code", "-1");
                            result.put("AttUrl", attUrl);
                            json = JSONUtil.writeObject2Json(result);
                            response.getWriter().print(json);
                        }
                    } else if (WxApi.type_fix.get(MediaType.Image.toString()).indexOf(fileExt) > -1) {
                        if (file.length() > WxApi.type_length.get(MediaType.Image.toString())) {
                            result.put("success", false);
                            result.put("msg", "上传失败，图片大小不能超过2M");
                            json = JSONUtil.writeObject2Json(result);
                            response.getWriter().print(json);
                        } else {
                            attUrl = realPath + originName;
                            result.put("filesPath", file);
                            result.put("success", true);
                            result.put("msg", "上传成功");
                            result.put("code", "-1");
                            result.put("AttUrl", attUrl);
                            json = JSONUtil.writeObject2Json(result);
                            response.getWriter().print(json);
                        }
                    } else {
                        attUrl = realPath + originName;
                        result.put("filesPath", file);
                        result.put("success", true);
                        result.put("msg", "上传成功");
                        result.put("code", "-1");
                        result.put("AttUrl", attUrl);
                        json = JSONUtil.writeObject2Json(result);
                        response.getWriter().print(json);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping({"upfileBase64"})
    public void upfileBase64(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String serverId = request.getParameter("serverId");
        String json = null;
        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> result = new HashMap();
        try {
            String realPath = Global.getAttachmentDir() + "upload/" +
                    DateUtils.getYear() + "/" + DateUtils.getMonth() + "/" + DateUtils.getDay() + "/";
            FileUtils.createDirectory(Global.getAttachmentDir() + realPath);
            realPath += System.currentTimeMillis() + ".jpg";
            FileUtils.createFile(Global.getAttachmentDir() + realPath);
            File file = WeixinFileUtil.downloadMedia(Global.getAttachmentDir() + realPath, serverId);
            System.err.println(file);
            result.put("status", "1");
            result.put("url", realPath);
            json = JSONUtil.writeObject2Json(result);
            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "0");
            result.put("url", null);
        }
    }

    public double getSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小，如果是文件则直接返回其大小
            if (!file.isFile()) {
                //获取文件大小
                File[] fl = file.listFiles();
                double ss = 0;
                for (File f : fl)
                    ss += getSize(f);
                return ss;
            } else {
                double ss = (double) file.length() / 1024 / 1024;
                System.out.println(file.getName() + " : " + ss + "MB");
                return ss;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }

    // 保存文件
    @SuppressWarnings("unused")
    private File saveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
        try {
            File file = new File(path + "/" + filename);

            FileOutputStream fs = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 1024];
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = stream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
                fs.flush();
            }

            fs.close();
            stream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
