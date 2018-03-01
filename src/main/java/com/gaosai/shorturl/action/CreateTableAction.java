package com.gaosai.shorturl.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaosai.shorturl.entity.Result;
import com.gaosai.shorturl.mapping.UrlDao;

@Controller
public class CreateTableAction {

	@Autowired
	private UrlDao urlDao;

//	@RequestMapping("createTable")
	@ResponseBody
	public Object c() {
		new Thread(new Runnable() {

			public void run() {
				for (int i = 1; i <= 137438; ++i) {
					try {
						urlDao.createTable(i);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		return Result.success(null);
	}

}
