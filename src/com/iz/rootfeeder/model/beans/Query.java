package com.iz.rootfeeder.model.beans;

import java.io.Serializable;

public class Query implements Serializable {

	private static final long serialVersionUID = 1L;

	private CodeCountryPair codeCountryPair;
	private CodeIndicatorPair codeIndicatorPair;
	private Integer startYear, finishYear;

	public Query(CodeCountryPair codeCountryPair,
			CodeIndicatorPair codeIndicatorPair, Integer startYear,
			Integer finishYear) {
		super();
		this.codeCountryPair = codeCountryPair;
		this.codeIndicatorPair = codeIndicatorPair;
		this.startYear = startYear;
		this.finishYear = finishYear;
	}

	public CodeCountryPair getCodeCountryPair() {
		return codeCountryPair;
	}

	public void setCodeCountryPair(CodeCountryPair codeCountryPair) {
		this.codeCountryPair = codeCountryPair;
	}

	public CodeIndicatorPair getCodeIndicatorPair() {
		return codeIndicatorPair;
	}

	public void setCodeIndicatorPair(CodeIndicatorPair codeIndicatorPair) {
		this.codeIndicatorPair = codeIndicatorPair;
	}

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getFinishYear() {
		return finishYear;
	}

	public void setFinishYear(Integer finishYear) {
		this.finishYear = finishYear;
	}
	
	@Override
	public String toString(){
		return codeCountryPair.toString()+", "+codeIndicatorPair.toString()+", "+startYear+", "+finishYear;		
	}

}
