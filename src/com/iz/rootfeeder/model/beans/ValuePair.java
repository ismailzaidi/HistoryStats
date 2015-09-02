package com.iz.rootfeeder.model.beans;

import java.io.Serializable;

/**
 * This class aims to provide informationa about the country, it will be used as a super class to two subclasses 
 * @author Team V
 * @version 1.00
 * 
 */
public class ValuePair implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private String name;

	/**
	 * Constructor will pass in code and name for both indicators and country
	 * lists
	 * 
	 * @param code
	 * @param name
	 */
	public ValuePair(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	/**
	 * It will return country code
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setting the code, this can be indicator id, or countries isocode 
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * returning the country or indicator name
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * This will set the country name and indicator name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Overriding the toString method to return the name of the parsed in String, this can be indicator name or country name
	 */
	@Override
	public String toString() {
		return name;
	}
}
