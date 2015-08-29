package com.iz.rootfeeder.model;

/**
 * This class contains a constructor and is a subclass of ValurPair
 * And it aims to parse in country code and country name
 * @author Team V
 * @version 1.00
 */

public class CodeCountryPair extends ValuePair implements Comparable<CodeCountryPair>{

	private static final long serialVersionUID = 1L;

	public CodeCountryPair(String code, String name) {
		super(code, name);
	}
	@Override
	public int compareTo(CodeCountryPair another) {
		return getName().compareTo(another.getName());
	}

	
}