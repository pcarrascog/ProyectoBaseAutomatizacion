package com.tsoftlatam.automatizacion.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommUtil {

	/**
	 * Function: implicitWait Description: Espera implicitamente por un tiempo
	 * determinado - SEGUNDOS.
	 * 
	 * @param time
	 *            (long)
	 * @return boolean
	 * @author Date:
	 **/
	public static boolean implicitWait(long time) {
		try {
			// Espera implicitamente por un tiempo determinado
			DriverNavegador.driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Function: launchBrowser Description: Despliega el browser con URL a
	 * testear
	 * 
	 * @param url
	 *            (String)
	 * @return boolean
	 * @author
	 * @Date:
	 **/
	public static boolean launchBrowser(String url) {
		try {
			// Despliega el browser con URL a testear
			DriverNavegador.driver.get(url);
			// Maximiza la ventana del navegador
			DriverNavegador.driver.manage().window().maximize();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * BORRAR SOLO DE PRUEBA Function: showExelData Description: Imprime la
	 * lista con los datos leidos desde excel
	 * 
	 * @param sheetData
	 *            (List<List<String>> )
	 * @return
	 * @author Daniela Trincado Date: 26-04-2016
	 **/
	public static void showExelData(List<List<String>> sheetData) {
		// Itera los datos e imprime la salida por consola
		for (int i = 1; i < sheetData.size(); i++) {
			List<?> list = (List<?>) sheetData.get(i);
			for (int j = 0; j < list.size(); j++) {
				System.out.print(" " + i + ": " + list.get(j));
			}
			System.out.println("");
		}
	}

}
