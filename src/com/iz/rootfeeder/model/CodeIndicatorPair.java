package com.iz.rootfeeder.model;

/**
 * This class contains a constructor and is a subclass of ValurPair
 * And it aims to parse in indicator code and indicator name
 * @author Team V
 * @version 1.00
 */

public class CodeIndicatorPair extends ValuePair implements Comparable<CodeIndicatorPair>{

	private static final long serialVersionUID = 1L;
	private String desc;
	
	public CodeIndicatorPair(String code, String name, String desc) {
		super(code, name);
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int compareTo(CodeIndicatorPair another) {
		return getName().compareTo(another.getName());
	}

	
}
