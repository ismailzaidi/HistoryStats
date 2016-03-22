package com.iz.rootfeeder.model.beans;

import java.io.Serializable;

public class ValuePair implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private String name;

	public ValuePair(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
