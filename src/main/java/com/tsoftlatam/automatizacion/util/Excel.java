package com.tsoftlatam.automatizacion.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	/** 
	 * Function: getDatosHojaExcel
	 * Description: Retorna lista con los datos extra�os desde el archivo excel
	 * @param nombreDatapool (String)
	 * @return List<List<String>>
	 * @author Daniela Trincado
	 * @throws IOException 
	 **/
	public static List<List<String>> getDatosHojaExcel(String nombreDatapool, int nrohoja) throws IOException {
	
		// Crea un ArrayList donde almacenara la data leida desde la hoja
		// del archivo excel
		List<List<String>> datosExcel = new ArrayList<List<String>>();
		String curDir = System.getProperty("user.dir");
			
		// Ruta archivo
		String archivoExcel = curDir +"/src/main/resources/Datapool/"+nombreDatapool; 
		
		//System.out.println("ruta : "+archivoExcel);
		
		try {			
			//Crea un FileInputStream para leer el archivo excel
			FileInputStream archivo = new FileInputStream(archivoExcel);

			// Create un libro excel desde el archivo.
			HSSFWorkbook libro = new HSSFWorkbook(archivo);
	
			//Formateo de celdas
			DataFormatter formatter = new DataFormatter();

			//Obtiene la hoja a leer desde el excel
			Sheet sheet = libro.getSheetAt(nrohoja);
			
			//Recorre las filas de la hoja
			for (int rowNumber = sheet.getFirstRowNum(); rowNumber <= sheet.getLastRowNum(); rowNumber++) {
			    Row row = sheet.getRow(rowNumber);
			    List<String> data = new ArrayList<String>();
			    if (row != null) {
			    	// Recorre las celdas de la fila
			    	for (int cellNumber = row.getFirstCellNum(); cellNumber <= row.getLastCellNum(); cellNumber++) {
			    		Cell cell = row.getCell(cellNumber);
			    		//Si la celda esta en blanco guardar el valor ""
			            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			            	data.add("");
			            } else {
			            	//Si la celda no esta vacía agregar valor a la lista
			            	data.add(formatter.formatCellValue(cell));
			            }
			         }
			    }
			    //Almacena la lista con valores de la fila dentro de otra lista
			    datosExcel.add(data);
			 }
			libro.close();
		}catch (Exception e) {
			System.out.println("Exception getDatosHojaExcel: "+e);
		}
		return datosExcel;
	}	

	/** 
	 * Function: getDatosHojaExcelXSSF
	 * Description: Retorna lista con los datos extraios desde el archivo excel con extensión xlsx
	 * @param nombreDatapool (String)
	 * @return List<List<String>>
	 * @throws IOException 
	 **/
	public static List<List<String>> getDatosHojaExcelXSSF(String nombreDatapool, int nrohoja) throws IOException {
	
		// Crea un ArrayList donde almacenara la data leida desde la hoja
		// del archivo excel
		List<List<String>> datosExcel = new ArrayList<List<String>>();
		String curDir = System.getProperty("user.dir");
			
		// Ruta archivo
		String archivoExcel = curDir +"/src/main/resources/Datapool/"+nombreDatapool; 
		
		//System.out.println("ruta : "+archivoExcel);
		
		try {			
			//Crea un FileInputStream para leer el archivo excel
			FileInputStream archivo = new FileInputStream(archivoExcel);

			// Create un libro excel desde el archivo.
			XSSFWorkbook libro = new XSSFWorkbook(archivo);
	
			//Formateo de celdas
			DataFormatter formatter = new DataFormatter();

			//Obtiene la hoja a leer desde el excel
			Sheet sheet = libro.getSheetAt(nrohoja);
			
			//Recorre las filas de la hoja
			for (int rowNumber = sheet.getFirstRowNum(); rowNumber <= sheet.getLastRowNum(); rowNumber++) {
			    Row row = sheet.getRow(rowNumber);
			    List<String> data = new ArrayList<String>();
			    if (row != null) {
			    	// Recorre las celdas de la fila
			    	for (int cellNumber = row.getFirstCellNum(); cellNumber <= row.getLastCellNum(); cellNumber++) {
			    		Cell cell = row.getCell(cellNumber);
			    		//Si la celda esta en blanco guardar el valor ""
			            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			            	data.add("");
			            } else {
			            	//Si la celda no esta vacía agregar valor a la lista
			            	data.add(formatter.formatCellValue(cell));
			            }
			         }
			    }
			    //Almacena la lista con valores de la fila dentro de otra lista
			    datosExcel.add(data);
			 }
			libro.close();
		}catch (Exception e) {
			System.out.println("Exception getDatosHojaExcel: "+e);
		}
		return datosExcel;
	}	
	
}
