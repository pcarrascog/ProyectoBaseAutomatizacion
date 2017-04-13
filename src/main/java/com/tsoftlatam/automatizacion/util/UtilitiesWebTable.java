package com.tsoftlatam.automatizacion.util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class UtilitiesWebTable {
	
	public static List<TableDataCollection> tableDataCollection = new ArrayList<TableDataCollection>();
	
    /** 
   	 * Function: readTable
   	 * Description: lee el contenido de una tabla HTML "table"
   	 * @param table (WebElement) elemento y/o objeto tipo tabla a leer
   	 * @param tagNameSpecialColumn1 (String) columna especial con elementos y/o objetos, es decir, por ej. Tag HTML "input"
   	 * @param specialColumn1 (int) posición dentro de la tabla donde se debe buscar el campo especial 1
   	 * @param tagNameSpecialColumn2 (String) Busca una segunda columna especial con elementos y/o objetos, es decir, por ej.Tag HTML "select". 
   	 * @param specialColumn2 (int) posición dentro de la tabla donde se debe buscar el campo especial 2
   	 * @author Daniela Trincado
   	 * Date: 03-06-2016
   	 **/
	public static void readTable(WebElement table, String tagNameSpecialColumn1, int specialColumn1,
			String tagNameSpecialColumn2, int specialColumn2){
		
		//Obtener todas las columnas de la tabla
		List<WebElement> columns = readNameColumnsTable(table);
		
		//Obtener todas las filas
		List<WebElement> rows =  table.findElements(By.tagName("tr"));
		
		//Indice de filas
		int rowIndex = 0;
		
		for (WebElement row : rows) {
			
			int colIndex = 0;

			List<WebElement> colDatas =  row.findElements(By.tagName("td"));
			
			for (WebElement colValue : colDatas) {				
				try{
					List<WebElement>  columnSpecial = null;
					//Verifica si la tabla tiene columnas especiales por ej. input, radiobuton, select
					if (!tagNameSpecialColumn1.equals("") && specialColumn1 == colIndex ){
						if(tagNameSpecialColumn1.equals("select")){
							columnSpecial = colValue.findElements(By.tagName(tagNameSpecialColumn1));
							if (columnSpecial.size()>0){
								Select optSelect= new Select(columnSpecial.get(0));
								//Se obtienen las opciones que posee el combobox
								columnSpecial = optSelect.getOptions();	
								optSelect = null;
							}else
								columnSpecial = null;
							
						}else
							columnSpecial = colValue.findElements(By.tagName(tagNameSpecialColumn1));
						
					}else{
						if (!tagNameSpecialColumn2.equals("") && specialColumn2 == colIndex)
							columnSpecial = colValue.findElements(By.tagName(tagNameSpecialColumn2));	
					}
					
					//crea un nuevo objeto tipo Table Data Collection
					tableDataCollection.add(new TableDataCollection (
						rowIndex,
						colIndex, 
						columns.get(colIndex).getText() != "" ?
								columns.get(colIndex).getText() : Integer.toString(colIndex), 
						colValue.getText(),
						columnSpecial
						));
				}catch (Exception e) {
					System.out.println("class "+e.toString());
				}			
				//Moverse a la siguiente columna
				colIndex++;
			}
			//Moverse a la siguiente fila
			rowIndex++;
		}		
	}
	
    /** 
   	 * Function: readCell
   	 * Description: lee el contenido de una celda perteneciente a una tabla HTML "table", antes se debe haber leido la tabla readTable
   	 * @param columnName (String) nombre de la columna especifica que se quiere leer
   	 * @param rowNumber (int) nro de fila que se requiere leer
   	 * @return String devuelve el valor de la celda (no devuelve contenido de celdas especiales por ej. que contiene un input)
   	 * @author Daniela Trincado
   	 * Date: 03-06-2016
   	 **/
	public static String readCell(String columnName, int rowNumber){
		
		String columValue = "";		
		for (TableDataCollection table : tableDataCollection) {	
			if (table.getColumnaName().equals(columnName)&& table.getRowNumber()==rowNumber)		
				columValue = table.columnValue; 
		}		
		return columValue;		
	}
	
	/** 
   	 * Function: readCell
   	 * Description: lee el contenido de una celda perteneciente a una tabla HTML "table", antes se debe haber leido la tabla readTable
   	 * @param columnName (String) nombre de la columna especifica que se quiere leer
   	 * @param rowNumber (int) nro de fila que se requiere leer
   	 * @return String devuelve el valor de la celda (no devuelve contenido de celdas especiales por ej. que contiene un input)
   	 * @author Daniela Trincado
   	 * Date: 03-06-2016
   	 * @author Rodrigo Miranda
   	 * Date: 07-10-2016
   	 * Descripcion : Sobre carga al metodo, lee todas las celdas de una columna en especifico
   	 **/
	public static List<String> readCell(String columnName){
		
		List<String> columValue = new ArrayList<String>();		
		for (TableDataCollection table : tableDataCollection) {	
			if (table.getColumnaName().equals(columnName))		
				columValue.add(table.columnValue); 
		}		
		return columValue;		
	}
	
	public static List<String> readCells(int columnIndex) {
		List<String> columValue = new ArrayList<String>();
		for (TableDataCollection table : tableDataCollection) {
			if (table.getColNumber() == columnIndex)
				columValue.add(table.columnValue);
		}
		return columValue;
	}
	
	/** 
	 * Function: readSpecialCell
	 * Description: lee el contenido de una celda perteneciente a una tabla HTML "table", antes se debe haber leido la tabla readTable
	 * @param columnNumber (int) nro de la columna especifica que se quiere leer
	 * @param rowNumber (int) nro de fila que se requiere leer
	 * @author Daniela Trincado
	 * Date: 03-06-2016
	 **/
	public static List<WebElement> readSpecialCell(int columnNumber, int rowNumber){
		
		List<WebElement> cell = null;		
		for (TableDataCollection table : tableDataCollection) {	
			if (table.getColNumber() == columnNumber && table.getRowNumber() == rowNumber)		
				cell = table.columnSpecialValues; 
		}		
		return cell;		
	}

	/** 
	 * Function: PerformanceActionOnCellClic
	 * Description: realiza un clic sobre un elemento ubicado dentro de una celda de una tabla
	 * @param  nameSpecialColumn (String) Nombre de la columna donde se ubica el elemento
	 * @param  controlToOperate(String) Atributo value del elemento
	 * @param  refColumnName (String) Nombre de una columna referencial donde se requiere realizar la acción
	 * @param  refColumnValue (String) valor de la celda referencial donde requiere realizar la acción
	 * @author Daniela Trincado
	 * Date: 03-06-2016
	 **/
	public static void performanceActionOnCellClic(String nameSpecialColumn, String controlToOperate,
			String refColumnName, String refColumnValue){
		
		List<WebElement> cell = null;		
		//Obtiene la fila donde se ubican los datos referenciados
		int rowNumber = getRowNumber(refColumnName, refColumnValue);
		
		for (TableDataCollection table : tableDataCollection) {	
			if (table.getColumnaName().equals(nameSpecialColumn)&& table.getRowNumber()== rowNumber){		
				cell = table.columnSpecialValues; 
				break;
			}			
		}
		//Realiza click sobre elemento
		if(controlToOperate != null && cell != null){
			for(WebElement element : cell){				
				element.getAttribute("value").equals(controlToOperate);
				element.click();
			}
		}
	}
	
	/** 
	 * Function: getRowNumber
	 * Description: obtiene la fila donde se ubica el valor consultado (columnName - columnValue)
	 * @param  columnName (String) Nombre de la columna donde se ubica el elemento a buscar
	 * @param  columnValue (String) valor que se requiere buscar
	 * @author Daniela Trincado
	 * Date: 03-06-2016
	 **/
	private static int getRowNumber(String columnName, String columnValue){
		
		int rowsNumber = -1;
		
		for (TableDataCollection table : tableDataCollection) {
			if(table.getColumnaName().equals(columnName.trim()) &&
					table.columnValue.equals(columnValue.trim())){
				rowsNumber = table.getRowNumber();			
				break;
			}				
		}
		return rowsNumber;
	}

	/** 
	 * Function: readNameColel nombre de las columnas de una tabla web
	 * @param  table (WebElement) elemento tipo tabla
	 * @author Daniela Trincado
	 * Date: 03-06-2016
	 **/
	public static List<WebElement> readNameColumnsTable(WebElement table){
		
		//Obtiene solamente el nombre de las columnas de la tabla web
		List<WebElement> columns = table.findElements(By.tagName("th"));
		return columns;
		
	}
	
	/** 
	 * Function: PerformanceActionOnCellClic
	 * Description: realiza un clic sobre un elemento ubicado dentro de una celda de una tabla
	 * @param  nameSpecialColumn (String) Nombre de la columna donde se ubica el elemento
	 * @param  controlToOperate(String) Atributo value del elemento
	 * @param  refColumnName (String) Nombre de una columna referencial donde se requiere realizar la acción
	 * @param  refColumnValue (String) valor de la celda referencial donde requiere realizar la acción
	 * @author Daniela Trincado
	 * Date: 03-06-2016
	 **/
	public static void PerformanceActionOnCellClic(String nameSpecialColumn, String controlToOperate,
			String refColumnName, String refColumnValue){
		
//		boolean existsElement = false;
		List<WebElement> cell = null;		
		//Obtiene la fila donde se ubican los datos referenciados
		int rowNumber = getRowNumber(refColumnName, refColumnValue);
		
		for (TableDataCollection table : tableDataCollection) {	
//			if(!existsElement && table.getColumnaName().equals(refColumnName.trim()) &&
//					table.columnValue.equals(refColumnValue.trim())){
//				existsElement = true;	
//			}				
//			if (existsElement && table.getColumnaName().equals(columnIndex)&& table.getRowNumber()== rowNumber){	
			if (table.getColumnaName().equals(nameSpecialColumn)&& table.getRowNumber()== rowNumber){		
				cell = table.columnSpecialValues; 
				break;
			}			
		}
		//Realiza click sobre elemento
		if(controlToOperate != null && cell != null){
			for(WebElement element : cell){				
				element.getAttribute("value").equals(controlToOperate);
				element.click();
			}
		}
	}
	
	/** 
	 * Function: PerformanceActionOnCellClic
	 * Description: realiza un clic sobre un elemento ubicado dentro de una celda de una tabla
	 * @param  nameSpecialColumn (String) Nombre de la columna donde se ubica el elemento
	 * @param  controlToOperate(String) Atributo value del elemento
	 * @param  refColumnName (String) Nombre de una columna referencial donde se requiere realizar la acción
	 * @param  refColumnValue (String) valor de la celda referencial donde requiere realizar la acción
	 * @author Daniela Trincado
	 * Date: 03-06-2016
	 **/
	public static String getAttributeSpecialCell(String nameSpecialColumn, String controlToOperate,
			String refColumnName, int rowNumber, String sAttribute){

		String vAttribute = "";
		List<WebElement> cell = null;		
		//Obtiene la fila donde se ubican los datos referenciados
//		int rowNumber = getRowNumber(refColumnName, refColumnValue);
		
		for (TableDataCollection table : tableDataCollection) {	
			if (table.getColumnaName().equals(nameSpecialColumn)&& table.getRowNumber()== rowNumber){		
				cell = table.columnSpecialValues; 
				break;
			}			
		}
		//Realiza click sobre elemento
		if(controlToOperate != null && cell != null){
			for(WebElement element : cell){				
				vAttribute = element.getAttribute(sAttribute);
			}
		}
		
		return vAttribute;
		
	}
	
	/** 
	 * Function: getCountSpecialCell
	 * Description: Cuenta una columna especifica cuantas veces se despliega el elemento.
	 * @param  nameSpecialColumn (String) Nombre de la columna donde se ubica el elemento
	 * @param  rowNumber (int) Numero de fila
	 * @author Yhoel Candia
	 * Date: 08/09/2016
	 **/
	
	public static int getCountSpecialCell(String nameSpecialColumn, String sAttribute){
		int contador = 0;
		int fila = 2;
		String vAttribute = "";		

		for (TableDataCollection table : tableDataCollection) {	
			if (table.getColumnaName().equals(nameSpecialColumn) && table.getRowNumber() == fila){		
				for (WebElement webElement : table.columnSpecialValues) {
					vAttribute = webElement.getAttribute(sAttribute);
					if(vAttribute.equals("false")){
						contador++;
					}
				}
				fila++;
			}	
		}
		return contador;	
	}
	
	public static void clearTableDataCollection(){
		tableDataCollection.clear();
	}
}
