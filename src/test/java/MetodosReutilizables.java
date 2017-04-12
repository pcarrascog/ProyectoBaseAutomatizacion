
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class MetodosReutilizables {

	public StringBuffer passLogVP = new StringBuffer();
	public StringBuffer passLogDescripcion = new StringBuffer();
	public StringBuffer passLogEvidencia = new StringBuffer();
	public StringBuffer passLogVPTotales = new StringBuffer();
	public ArrayList<String> passlist = new ArrayList<String>();
	public static String fecha_hora_Ejecucion = "";
	public static String curDir = System.getProperty("user.dir");

	public void crearCarpetaReporte(String nombreCarpeta) {
		File carpeta = new File(curDir + "\\" + nombreCarpeta);

		if (!carpeta.exists()) {
			carpeta.mkdir();
			System.out.println("Se ha creado carpeta " + nombreCarpeta);
		} else {
			System.out.println("Carpeta " + nombreCarpeta + " ya existe.");
		}
	}

	public void crearCarpetaDePruebas(String nombreCarpetaReporte, String nombreCarpetaPruebas) {
		File carpeta = new File(curDir + "\\" + nombreCarpetaReporte + "\\" + nombreCarpetaPruebas);

		if (!carpeta.exists()) {
			carpeta.mkdir();
			System.out.println("Se ha creado carpeta " + nombreCarpetaPruebas);
		} else {
			System.out.println("Carpeta " + nombreCarpetaPruebas + " ya existe.");
		}
	}

	public void crearCarpetaDeEvidencias(String nombreCarpetaReporte, String nombreCarpetaPruebas) {
		File carpeta = new File(curDir + "\\" + nombreCarpetaReporte + "\\" + nombreCarpetaPruebas + "\\Evidencias");

		if (!carpeta.exists()) {
			carpeta.mkdir();
			System.out.println("Se ha creado carpeta Evidencias");
		} else {
			System.out.println("Carpeta Evidencias ya existe.");
		}
	}

	public static void EvidenciaEspecial(String nombrearchivo) throws IOException, HeadlessException, AWTException {
		File carpetaArchivos = new File(curDir + "\\CapturaEvidenciasEspeciales");
		if (!carpetaArchivos.exists()) {
			carpetaArchivos.mkdir();
		}

		BufferedImage imagen = new Robot()
				.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(imagen, "png", new File(curDir + "\\CapturaEvidenciasEspeciales\\" + nombrearchivo));
	}

	public void passLog(String NombreVP, String Mensaje, WebDriver driver) {

		String Evidencia = capturaEvidencia(driver, "VP - " + NombreVP);

		// Se guardan la informaci�n del VP en el ArrayList
		passlist.add(NombreVP);
		passlist.add(Mensaje);
		passlist.add(Evidencia);

		// Ingresar Nombre del punto de verificaci�n en el textarea ubicado al
		// lado izquierdo del index
		if (passLogVP.length() == 0) {
			passLogVP.append(NombreVP);
		} else {
			passLogVP.append("\\n" + NombreVP);// Agrega un salto de linea en el
												// textarea de VP exitosos
		}
		passLogVPTotales.append("1");// Se suma 1 al StringBuffer con el total
										// de VP Exitosos
	}

	public static String capturaEvidencia(WebDriver driver, String imageName) {

		java.util.Date today2 = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("_dd_MM_yyyy_MM_dd-hh_mm_ss");
		String fecha_hora = formatter.format(today2);

		// Directorio donde quedaran las imagenes guardadas
		File directory = new File(curDir + "\\ReporteResultados\\Ejecuci�n" + fecha_hora_Ejecucion + "\\Evidencias");

		try {
			if (!directory.exists()) {
				directory.mkdir();
			}

			// Toma la captura de imagen
			File imagen = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// Mueve el archivo a la carga especificada con el respectivo nombre
			// FileUtils.copyFile(imagen, new File(directory.getAbsolutePath() +
			// "\\" + imageName + fecha_hora +".png"));
			FileUtils.copyFile(imagen, new File(directory.getAbsolutePath() + "\\" + imageName + ".png"));

			// String Imagen = "Evidencia/"+imageName + fecha_hora +".png";
			String Imagen = "Evidencia/" + imageName + ".png";

			return Imagen;
		} catch (IOException e) {
			// Impresion de Excepciones
			System.out.println(e);
			return null;
		}
	}

	/*
	 * public void copiarCssImagenes(String ruta) throws IOException { //Se
	 * crean los directorios donde se guardaran los archivos necesarios String
	 * directorioPadre = ruta; File directory1 = new
	 * File(directorioPadre+"\\Formato"); if (!directory1.exists()) {
	 * directory1.mkdir(); } File directory2 = new
	 * File(directorioPadre+"\\Formato\\images"); if (!directory2.exists()) {
	 * directory2.mkdir(); } File directory3 = new
	 * File(directorioPadre+"\\Formato\\js"); if (!directory3.exists()) {
	 * directory3.mkdir(); } File directory4 = new
	 * File(directorioPadre+"\\Formato\\css"); if (!directory4.exists()) {
	 * directory4.mkdir(); }
	 * 
	 * 
	 * public void crearLog(String nombreCaso){
	 * 
	 * 
	 * 
	 * //Directorio donde quedaran las imagenes guardadas File directory = new
	 * File(curDir+"\\log"); File directoryEjecucion = new
	 * File(curDir+"\\log\\Ejecuci�n"+fecha_hora_Ejecucion); File f = new
	 * File(directoryEjecucion+"\\index.html"); //
	 * System.out.println(directoryEjecucion); //Escritura try{ if
	 * (!directory.exists()) { directory.mkdir(); }
	 * 
	 * if (!directoryEjecucion.exists()) { directoryEjecucion.mkdir(); }
	 * 
	 * FinScript();
	 * 
	 * copiarCssImagenes(directoryEjecucion.toString());
	 * 
	 * Desktop desktop; if (Desktop.isDesktopSupported()){// En caso que el host
	 * esta API desktop = Desktop.getDesktop();//Se obtiene una instancia del
	 * Desktop(Escritorio)de mi host try { desktop.open(f);//Se abre el archivo
	 * con el programa predeterminado } catch (IOException ex){
	 * System.out.println(ex); } } else{ warningLog("Apertura Navegador",
	 * "Lo sentimos,no se puede abrir el archivo; �sta Maquina no soporta la API Desktop"
	 * , driverwarning); }
	 * 
	 * }catch(IOException e){ System.out.println(e); };
	 * 
	 * }
	 */

}
