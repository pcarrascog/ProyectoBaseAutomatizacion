package com.tsoftlatam.automatizacion.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;


public class UtilitiesFile {
	
	protected static final String DIR_DOWNLOAD = System.getProperty("user.home", "C:") + File.separator + "Downloads" + File.separator;
	
	public static void setClipboardData(String string) {
		   //Ubicaci�n del archivo y su extensi�n
		   StringSelection stringSelection = new StringSelection(string);
		   //Copiar a portapapeles
		   Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	public static boolean uploadFile(String ruta)
	{
		boolean flagFile = false;
		//ruta = "C:\\path to file\\example.jpg";
		
		if (filesExists(ruta)){
			setClipboardData(ruta);
			//native key strokes for CTRL, V and ENTER keys
			Robot robot;
			try {
				robot = new Robot();
				Thread.sleep(1000);
				//Presione CTRL+V
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);			
				//Libere CTRL+V
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_V);
				Thread.sleep(1000);
				//Presione Enter
				robot.keyPress(KeyEvent.VK_ENTER);
				//Libere Enter
				robot.keyRelease(KeyEvent.VK_ENTER);
				
				flagFile = true;
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		return flagFile;
	}
	
	public static boolean filesExists(String ruta){
		
		Path nuevoDirectorio = Paths.get(ruta);
		 
		if (Files.exists(nuevoDirectorio))
		    return true;
		else
			return false;
		    	
	}
	
	public static String waitForNewFile(String nombreArchivo, long timeOut) {
		String downloadedFileName = null;				
		
		try{ 
			Path folder = Paths.get(DIR_DOWNLOAD);
			WatchService watcher = FileSystems.getDefault().newWatchService();
			folder.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE);
			for (WatchKey key; null != (key = watcher.poll(timeOut - System.currentTimeMillis(),
					TimeUnit.MILLISECONDS)); key.reset()) {
				for (WatchEvent<?> event : key.pollEvents()) {

					WatchEvent.Kind<?> kind = event.kind();
					if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) 
					{
						String fileName = event.context().toString();
						System.out.println("New File Created:" + fileName);
						if(fileName.toLowerCase().startsWith(nombreArchivo.toLowerCase()))
						{
							downloadedFileName = fileName;
							System.out.println("Downloaded file name is " + fileName);
							Thread.sleep(500);
							break;
						}
					}
		
				}
			}
		}catch (InterruptedException e) {
			System.out.println("Error interrumpido - " + e.getMessage());
			e.printStackTrace();
		}catch (NullPointerException e){
			System.out.println("Tiempo de descarga de archivo agotado.. Archivo esperado no se ha descargado");
		}catch (Exception e){
			System.out.println("Ha ocurrido un error - " + e.getMessage());
			e.printStackTrace();
		}
		return downloadedFileName;
	}
	
	public static String getDownloadedDocumentName1(String fileExtension)
	{	
		String downloadedFileName = null;
		boolean valid = true;
		boolean found = false;
	
		//default timeout in seconds
		long timeOut = 2000; 
		try 
		{					
			Path downloadFolderPath = Paths.get(DIR_DOWNLOAD);
			WatchService watchService = FileSystems.getDefault().newWatchService();
			downloadFolderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE);
			long startTime = System.currentTimeMillis();
			do 
			{
				WatchKey watchKey = watchService.poll();
				
//				WatchKey watchKey = watchService.poll(10,TimeUnit.MILLISECONDS);
//				watchService.poll(10, TimeUnit.SECONDS);
				
				long currentTime = (System.currentTimeMillis()-startTime)/1000;
//				if(currentTime>timeOut)
//				{
//					System.out.println("Download operation timed out.. Expected file was not downloaded");
//					return downloadedFileName;
//				}
				
				for (WatchEvent<?> event : watchKey.pollEvents())
				{
					WatchEvent.Kind<?> kind = event.kind();
					if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) 
					{
						String fileName = event.context().toString();
						System.out.println("New File Created:" + fileName);
						if(fileName.endsWith(fileExtension))
						{
							downloadedFileName = fileName;
							System.out.println("Downloaded file found with extension " + fileExtension + ". File name is " + fileName);
							Thread.sleep(500);
							found = true;
							break;
						}
					}
				}
				if(found)
				{
					return downloadedFileName;
				}
				else
				{
					currentTime = (System.currentTimeMillis()-startTime)/1000;
					if(currentTime>timeOut)
					{	
						
						System.out.println("Failed to download expected file");
						return downloadedFileName;
					}
					valid = watchKey.reset();
				}
			} while (valid);
		} 
		
		catch (InterruptedException e) 
		{
			System.out.println("Interrupted error - " + e.getMessage());
			e.printStackTrace();
		}
		catch (NullPointerException e) 
		{
			System.out.println("Download operation timed out.. Expected file was not downloaded");
		}
		catch (Exception e)
		{
			System.out.println("Error occured - " + e.getMessage());
			e.printStackTrace();
		}
		return downloadedFileName;
	}
	
	/* Get the latest file from a specific directory*/
	public static File getLatestFilefromDir(){
		File lastModifiedFile = null;		
		
	    File dir = new File(DIR_DOWNLOAD);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }
	
	    lastModifiedFile = files[0];
	    for (File archivo : files) {
	       if (lastModifiedFile.lastModified() < archivo.lastModified()) 		    	  
	    		lastModifiedFile = archivo;		    		 	           
	    } 
	    
	    System.out.println("lastModifiedFile: " +lastModifiedFile);
	    return lastModifiedFile;
	}
	
	public static boolean getDownloadedDocumentName(String nombreArchivo, long waitTime, int cantReintentosBusqueda) throws InterruptedException
	{
		boolean flagArchivo = false;
		File lastModifiedFile = null;
		
		for (int i= 0; i<= cantReintentosBusqueda && !flagArchivo; i++){
			Thread.sleep(waitTime);
			lastModifiedFile = getLatestFilefromDir();
			
			if (lastModifiedFile != null){
				if (lastModifiedFile.getName().toLowerCase().startsWith(nombreArchivo.toLowerCase())) {
					flagArchivo = true;
				}
			}
		}
		return flagArchivo;	
	}
	

	/**
	 * Function: downloadFileForIE Description: se ubica en la ventana de
	 * descarga realiza un tab para ubicar en el bot�n de guardar y
	 * posteriormente se realiza en Enter Se sale de la ventana de descarga
	 * 
	 * @param log_result
	 * @return
	 * @author Ana Quintero Date: 30-06-2016 (Adicionar m�todo)
	 */
	public static void downloadFileForIE() {
		Robot robot;
//		System.out.println("entro antes if " + Driver.BROWSER);
//		if (Driver.BROWSER.equalsIgnoreCase("internetExplorer")) {
			System.out.println("clic boton");
			try {
				robot = new Robot();
				robot.delay(2000);
				// Ubicarse en la venta de descarga
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_N);
				robot.keyRelease(KeyEvent.VK_N);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.delay(2000);

				// se presiona la tecla tabulador para llegar al bot�n de
				// Guardar
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);

				robot.delay(2000);
				// robot.keyPress(KeyEvent.VK_ALT);
				// robot.keyPress(KeyEvent.VK_G);
				// robot.keyRelease(KeyEvent.VK_G);
				// robot.keyRelease(KeyEvent.VK_ALT);
				// robot.delay(2000);

				// Se ubica de nuevo en la ventana para abrir el archivo
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_N);
				robot.keyRelease(KeyEvent.VK_N);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.delay(2000);

//				log_result.passLog("VP ", "archivo descargado correctamente", Driver.driver, true);
				// Se presiona varia veces el tabulador para llegar a la x, para
				// el cierre de la ventana
				// robot.keyPress(KeyEvent.VK_TAB);
				// robot.keyRelease(KeyEvent.VK_TAB);
				//
				// robot.keyPress(KeyEvent.VK_TAB);
				// robot.keyRelease(KeyEvent.VK_TAB);
				//
				// robot.keyPress(KeyEvent.VK_TAB);
				// robot.keyRelease(KeyEvent.VK_TAB);
				//
				// robot.keyPress(KeyEvent.VK_ENTER);
				// robot.keyRelease(KeyEvent.VK_ENTER);
				// robot.delay(2000);

			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Warning VP " + "No se puede descargar el archivo" + " warning: " + ex.toString());
//				log_result.warningLog("VP " + "Error al descargar el archivo" + " ", ex.toString(), Driver.driver);
			}
//		}

	}

	/**
	 * Function: writeFile 
	 * Description:escrbe un archivo
	 * @param String sValue
	 * @param  String nombreArchivo
	 * @return
	 * @author Daniela Trincado
	 * Date: 30-08-2016
	 */
	public static void writeFile (String sValue, String nombreArchivo) throws IOException{	
		try
		{	
			//Obtiene el archivo a escribir
			FileWriter fileWriter = new FileWriter("./reports/"+nombreArchivo, true);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			//escribe nuevo registro en el archivo
			writer.write(sValue);
			//crea una nueva linea
			writer.newLine();
			
			writer.close();
		}catch (Exception e) {
			System.out.println("writeFile: " +e);
		}
	}

	/**
	 * Function userDirProyecto
	 * Description: Funcion que retorna la ruta del directorio del proyecto
	 * @param 
	 * @return String appPath
	 * @author Rodrigo Miranda
	 * Date 29/09/2016 
	 * */
	public static String userDirProyecto(){
		String appPath = System.getProperties().getProperty("user.dir");
//		return appPath += "/scriptSQL/";
		return appPath;
	}
}
