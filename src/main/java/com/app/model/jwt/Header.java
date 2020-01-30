package com.app.model.jwt;

public class Header {

	private String type;
	private String alg;
	
	public Header() {
		super();
	}

	public Header(String type, String alg) {
		super();
		this.type = type;
		this.alg = alg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlg() {
		return alg;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}
	
}
