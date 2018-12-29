/*
 * FileName：HttpUtil.java 
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jeeplus.wxapi.util;

import com.jeeplus.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * http工具类
 */
public class HttpUtil extends org.springframework.web.util.WebUtils{
	
	public static String getDomain(HttpServletRequest request){
		return request.getServerName();
	}
	
	public static String getHttpDomain(HttpServletRequest request){
		return request.getScheme() + "://" + request.getServerName();
	}
	
	public static String getContextHttpUri(HttpServletRequest request){
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	/**
	 * 获取网站根目录
	 *
	 * @param request
	 * @return
	 */
	public static String getBaseRequestURI(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName()+ (request.getServerPort() == 80 ? "" : (":" + request.getServerPort()))+ request.getContextPath();
	}

	public static String getRealPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	public static String getRequestFullUri(HttpServletRequest request){
		String port = "";
		if(request.getServerPort() != 80){
			port = ":" + request.getServerPort();
		}
		return request.getScheme() + "://" + request.getServerName() + port + request.getContextPath() + request.getServletPath();
	}
	
	public static String getRequestFullUriNoContextPath(HttpServletRequest request){
		String port = "";
		if(request.getServerPort() != 80){
			port = ":" + request.getServerPort();
		}
		return request.getScheme() + "://" + request.getServerName() + port + request.getServletPath();
	}
	
	//获取ip地址；
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			if(ip.indexOf("::ffff:")!=-1) ip = ip.replace("::ffff:", "");
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	//判断当前请求是否为Ajax
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		return !StringUtils.isEmpty(header) && "XMLHttpRequest".equals(header);
	}
	
	/**
	 * 重定向
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param url
	 */
	public static void redirectUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String url){
		try {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重定向到http://的url
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param url
	 */
	public static void redirectHttpUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String url){
		try {
			httpServletResponse.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取编码后带参数的页面URL
	 *
	 * @param request
	 * @param url
	 * @return
	 */
	private static final String encode = "UTF-8";
	public static String getFullRequestURI(HttpServletRequest request, String page, boolean isEncode) {
		String baseRequestUri = "";
		// 对测试环境进行特殊处理
		if (request.getContextPath().contains("test")) {
			baseRequestUri = request.getScheme() + "://" + request.getServerName()
					+ (request.getServerPort() == 80 ? "" : (":" + request.getServerPort()));
		}
		String fullURI = baseRequestUri + page;
		if (request.getQueryString() != null) {
			fullURI += "?" + request.getQueryString();
		}
		if (isEncode) {
			try {
				fullURI = URLEncoder.encode(fullURI, encode);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return fullURI;
	}
	
	public static Boolean existHttpPath(String httpPath){
			URL httpurl = null;
			try {
				httpurl = new URL(httpPath);
				URLConnection rulConnection = httpurl.openConnection();
				rulConnection.getInputStream();
				return true;
				} catch (Exception e) {
				return false;
			}
		}
	
}
