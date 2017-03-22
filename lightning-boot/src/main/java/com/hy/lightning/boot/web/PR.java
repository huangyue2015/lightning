package com.hy.lightning.boot.web;

import java.io.Serializable;

public class PR implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int resultstate;//返回状态
	
	private Object result;//返回结果
	
	private String resultdesc;//返回结果描述

	public int getResultstate() {
		return resultstate;
	}

	public PR setResultstate(int resultstate) {
		this.resultstate = resultstate;
		return this;
	}

	public Object getResult() {
		return result;
	}

	public PR setResult(Object result) {
		this.result = result;
		return this;
	}

	public String getResultdesc() {
		return resultdesc;
	}

	public PR setResultdesc(String resultdesc) {
		this.resultdesc = resultdesc;
		return this;
	}

	public PR(int resultstate, Object result, String resultdesc) {
		super();
		this.resultstate = resultstate;
		this.result = result;
		this.resultdesc = resultdesc;
	}

	public PR() {
		super();
	}
}
