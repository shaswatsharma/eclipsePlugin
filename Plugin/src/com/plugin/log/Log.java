package com.plugin.log;

public class Log {
	private String error;
	private boolean result;
	
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public boolean getResult() {
		return result;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String getError() {
		return error;
	}
}
