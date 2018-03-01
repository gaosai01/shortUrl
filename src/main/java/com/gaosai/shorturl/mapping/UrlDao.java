package com.gaosai.shorturl.mapping;

import org.apache.ibatis.annotations.Param;

import com.gaosai.shorturl.entity.TableUrl;

public interface UrlDao {

	public TableUrl selectByCode(@Param("table_id") long table_id, @Param("code") long code);

	public TableUrl selectByLong(@Param("table_id") long table_id, @Param("longurl") String longurl);

	public void insert(@Param("table_id") long table_id, @Param("code") long code, @Param("shorturl") String shorturl,
			@Param("longurl") String longurl);

	public void createTable(@Param("table_id") long table_id);

}
