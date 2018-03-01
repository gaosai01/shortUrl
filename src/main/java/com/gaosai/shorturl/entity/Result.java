package com.gaosai.shorturl.entity;

public class Result {

	private int code;
	private String msg;
	private Object data;

	public static Result success(Object data) {
		Result result = new Result();
		result.setCode(200);
		result.setMsg("成功");
		result.setData(data);
		return result;
	}

	public static Result error(String msg, Object data) {
		Result result = new Result();
		result.setCode(400);
		result.setMsg(msg);
		result.setData(data);
		return result;
	}

	public static Result error(Object data) {
		Result result = new Result();
		result.setCode(400);
		result.setMsg("失败");
		result.setData(data);
		return result;
	}

	private Result() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
