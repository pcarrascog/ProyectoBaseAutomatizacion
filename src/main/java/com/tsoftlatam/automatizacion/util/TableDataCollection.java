package com.tsoftlatam.automatizacion.util;

import java.util.List;

import org.openqa.selenium.WebElement;

public class TableDataCollection {
	
	public int rowNumber;
	public int colNumber;
	public String columnaName;
	public String columnValue;	
	public List<WebElement> columnSpecialValues;	
	
	public TableDataCollection(int rowNumber, int colNumber, String columnaName, String columnValue, 
			List<WebElement> columnSpecialValues) {
		super();
		this.rowNumber = rowNumber;
		this.colNumber = colNumber;
		this.columnaName = columnaName;
		this.columnValue = columnValue;
		this.columnSpecialValues = columnSpecialValues;
	}
	
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	public int getColNumber() {
		return colNumber;
	}
	public void setColNumber(int colNumber) {
		this.rowNumber = colNumber;
	}
	public String getColumnaName() {
		return columnaName;
	}
	public void setColumnaName(String columnaName) {
		this.columnaName = columnaName;
	}
	public String getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}	
	public List<WebElement> getColumnSpecialValues() {
		return columnSpecialValues;
	}
	public void setColumnSpecialValues(List<WebElement> columnSpecialValues) {
		this.columnSpecialValues = columnSpecialValues;
	}
	
	


}
