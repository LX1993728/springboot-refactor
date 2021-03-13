package com.refactor.spring.boot.domains;

import java.io.StringWriter;
import java.io.Writer;

public class JsonResult {

	private String code;
	private String msg;
	private Object obj;
	private Integer count;// add by CT 2015-10-26 分页处理的总行数

	public JsonResult() {
	}

	public JsonResult(String msg) {
		this.msg = msg;
		this.obj = "";
	}


	public JsonResult(String code, String msg) {
		this.code = code;
		this.msg = msg;
		this.obj = "";
	}

	public JsonResult(String code, String msg, Object obj) {
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}

	public JsonResult(String code, String msg, Object obj, Integer count) {
		this.code = code;
		this.msg = msg;
		this.obj = obj;
		this.count = count;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
