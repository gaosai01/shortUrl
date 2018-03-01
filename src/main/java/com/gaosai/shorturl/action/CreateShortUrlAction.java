package com.gaosai.shorturl.action;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaosai.shorturl.entity.Result;
import com.gaosai.shorturl.entity.TableUrl;
import com.gaosai.shorturl.mapping.UrlDao;
import com.gaosai.shorturl.util.ShortUrlGenerator;

@Controller
public class CreateShortUrlAction {

	private static Logger log = Logger.getLogger(CreateShortUrlAction.class);

	@Autowired
	private UrlDao urlDao;

	@RequestMapping("createshorturl")
	@ResponseBody
	public Object create(@RequestParam(value = "url", required = false) String url, HttpServletRequest request) {

		if (url == null) {
			return Result.error("请输入要简化的url地址", null);
		}
		url = url.trim();
		if (url.length() > 100) {
			return Result.error("对不起，系统支持的url地址长度不可大于100", null);
		}
		if (!checkurl(url)) {
			return Result.error("对不起，请输入正确的url地址", null);
		}

		String[] shorturls = ShortUrlGenerator.shortUrl(url);

		int length = shorturls.length;
		int i = 0;
		for (i = 0; i < length; ++i) {
			long code = ShortUrlGenerator.shorturl2long(shorturls[i]);
			long table_id = ShortUrlGenerator.code2tableid(code);
			try {
				urlDao.insert(table_id, code, shorturls[i], url);
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
			TableUrl tu = urlDao.selectByCode(table_id, code);
			if (tu != null) {
				if (tu.getLongurl().equals(url)) {
					String domain = getShortDomian(request);
					return Result.error("您所输入的url地址存在简化的字符串地址", domain + shorturls[i]);
				}
			}
		}
		if (i != length) {
			String shorturl = shorturls[i];
			String domain = getShortDomian(request);
			return Result.success(domain + shorturl);
		}
		log.error("需要简化的url地址(" + url + ")未简化成功");
		return Result.error("未知原因请联系管理员", null);

	}

//	private static Pattern pattern = Pattern.compile("^((https|http|ftp|rtsp|mms)?://)"
//			+ "+(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" + "|"
//			+ "([0-9a-z_!~*'()-]+\\.)*" + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." + "[a-z]{2,6})" + "(:[0-9]{1,10})?"
//			+ "((/?)|" + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");

	private boolean checkurl(String url) {
//		return pattern.matcher(url).find();
		if( url.startsWith( "http://" ) ){
			return true;
		}
		if( url.startsWith( "https://" ) ){
			return true;
		}
		return false;
	}

	private String getShortDomian(HttpServletRequest request) {

		String url = request.getRequestURL().toString();
		url = url.substring(0, url.lastIndexOf("/") + 1);
		url += "s/";

		return url;
	}

}
