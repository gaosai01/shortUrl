package com.gaosai.shorturl.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaosai.shorturl.entity.Result;
import com.gaosai.shorturl.entity.TableUrl;
import com.gaosai.shorturl.mapping.UrlDao;
import com.gaosai.shorturl.util.ShortUrlGenerator;

@Controller
public class ShortUrlRedirectAction {

	private static Logger log = Logger.getLogger(ShortUrlRedirectAction.class);

	@Autowired
	private UrlDao urlDao;

	@RequestMapping("/s/{shorturl}")
	public String get(@PathVariable String shorturl) {
		if (shorturl == null || shorturl.length() != 6) {
			return "redirect:/nourl";
		}
		long code = ShortUrlGenerator.shorturl2long(shorturl);
		long table_id = ShortUrlGenerator.code2tableid(code);
		TableUrl tu = urlDao.selectByCode(table_id, code);
		if (tu == null) {
			return "redirect:/nourl";
		}
		String longurl = tu.getLongurl();
		return "redirect:" + longurl;
	}

	@RequestMapping("getlongurl")
	@ResponseBody
	public Object getsho(@RequestParam(value = "url", required = false) String url) {
		if (url == null || url.isEmpty()) {
			return Result.error("请输入短链接", null);
		}
		url = url.substring(url.lastIndexOf("/") + 1);
		log.error(url);
		long code = ShortUrlGenerator.shorturl2long(url);
		long table_id = ShortUrlGenerator.code2tableid(code);
		TableUrl tu = urlDao.selectByCode(table_id, code);
		if (tu == null) {
			return Result.error("您输入的短链接不存在长链接", null);
		}
		String longurl = tu.getLongurl();
		return Result.success(longurl);
	}

	@RequestMapping("nourl")
	@ResponseBody
	public Object nourl() {
		return Result.error("暂无url", null);
	}

}
