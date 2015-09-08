package com.iz.rootfeeder.model.beans;

public class CountryCode extends ValuePair implements Comparable<CountryCode>{

	private static final long serialVersionUID = 1L;

	public CountryCode(String code, String name) {
		super(code, name);
	}
	@Override
	public int compareTo(CountryCode another) {
		return getName().compareTo(another.getName());
	}

	
}