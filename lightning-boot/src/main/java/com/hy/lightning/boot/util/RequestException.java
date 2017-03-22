package com.hy.lightning.boot.util;

public class RequestException extends Exception {

	private int resultstate;//错误状态
	
	private String resultdesc;//错误结果描述
	
	private Object result = "";
	
	public RequestException(int resultstate, Object result ,String resultdesc) {
		this.resultstate = resultstate;
		this.resultdesc = resultdesc;
		this.result = result;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object object) {
		this.result = object;
	}

	public int getResultstate() {
		return resultstate;
	}

	public void setResultstate(int resultstate) {
		this.resultstate = resultstate;
	}



	public String getResultdesc() {
		return resultdesc;
	}



	public void setResultdesc(String resultdesc) {
		this.resultdesc = resultdesc;
	}

	private static final long serialVersionUID = 1L;

}
