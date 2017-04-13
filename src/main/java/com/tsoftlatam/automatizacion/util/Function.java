package com.tsoftlatam.automatizacion.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Function {

    private static long timeout = 60;

    /** 
   	 * Function: verifyColumnsTable
   	 * Description: Verifica el nombre de las columnas de un objeto y/o elemento tipo table
   	 * @param driver (WebDriver) Driver con el cual se ejecuta la prueba
   	 * @param elements List<WebElement> Elementos a encontrar 
   	 * @param columns String[] Arreglo con los nombre de las columnas a buscar
     * @param cantIntentos (int) nro de intentos que se realizara para buscar el objeto en la pantalla, ya que a veces el objeto no es encontrado debido a que la pagina aun no termina de cargar
   	 * @param sleepTimeMs (long) tiempo de espera entre los intentos de busqueda del objecto
   	 * @return boolean
   	 * @author 
   	 * Date: 03-06-2016
   	 **/
   	public static boolean verifyColumnsTable(WebDriver driver, List<WebElement> elements, String[] columns, 
   			int cantIntentos,long sleepTimeMs, String fileReport){

   		String resultado = "";
   		boolean estadoVP = false;
   		//Contador errores durante la comparaci�n
   		int countError = 0;
   		WebElement nameColumns = null;
   		try{
   			//Espera que se cargue la pagina
   			waitForLoad(driver);

			//Se recorre arreglo con las opciones que se espera que posea el combobox
			for(int i=0; i< columns.length; i++){
				//Asigna la opción a verificar
				String columnEsperada = columns[i];
				//Flag indica si fue encontrada la opcion en el combo box o no
				boolean existe = false;
		   		//Contador de indices webelement
		   		int j=0;
				//recorre las opciones que posee el combobox
				while(j<elements.size()) {
					//Obtiene el campo segun indice
					nameColumns = elements.get(j);
				   //verifica si existe la opci�n esperada en el combobox
					if (columnEsperada.equalsIgnoreCase(nameColumns.getText())) {
				    	existe = true;
				    	break;
				    }
					j++;
				}
				//Almacena el resultado de la comparacion
				if(!existe)
					UtilitiesFile.writeFile("Columnas en grilla de Load File app Web; No se encontro Columna: "+columnEsperada,
							fileReport);				
			}	
			//Detiene la ejecuci�n por unos milisegundos para poder realizar la captura de la pantalla
			Thread.sleep(sleepTimeMs);
			
			//Genera el resultado de la prueba
			if(countError == 0){
				estadoVP = true;
			}

   		}	        
   		catch(Exception ex){
   			System.out.println("Warning verifyColumnsTable() "+ex.toString());
   		}		
   		
   		return estadoVP;     		
   	}
    /** 
   	 * Function: verifyOptionComboBox
   	 * Description: Verifica el contenido del combobox
   	 * @param driver (WebDriver) Driver con el cual se ejecuta la prueba
   	 * @param we (WebElement) Elemento a buscar 
   	 * @param opciones String[] Arreglo con opciones esperadas para el combobox
   	 * @param titleVp (String) titulo del punto de verificaci�n
   	 * @param detalleVp (String) detalle del punto de verificacion
   	 * @param cantIntentos (int) nro de intentos que se realizara para buscar el objeto en la pantalla, ya que a veces el objeto no es encontrado debido a que la pagina aun no termina de cargar
   	 * @param sleepTimeMs (long) tiempo de espera entre los intentos de busqueda del objecto
   	 * @param logResult (LogResult) variable del log de la prueba
   	 * @return boolean
   	 * @author 
   	 * Date: 02-06-2016
   	 **/
   	public static boolean verifyOptionComboBox(WebDriver driver, WebElement we, String[] opciones, String titleVp, String detalleVp,
   			int cantIntentos,long sleepTimeMs){

   		String resultado = "";
   		boolean objectPresent = false;
   		boolean estadoVP = false;
   		//Contador errores durante la comparaci�n
   		int countError = 0;
   		try{
   			//Espera que se cargue la pagina
   			waitForLoad(driver);
   			//Espera que se despligue el objeto
   			objectPresent = waitforExistsObject(driver, we, cantIntentos, sleepTimeMs);
   			
   			//si el objeto a verificar se encontro en la pagina se valida su contenido
   			if (objectPresent){					
   				Select mySelect= new Select(we);
   				//Se obtienen las opciones que posee el combobox
   				List<WebElement> options = mySelect.getOptions();
   				//Se recorre arreglo con las opciones que se espera que posea el combobox
   				for(int i=0; i< opciones.length; i++){
   					//Asigna la opci�n a verificar
   					String optionEsperada = opciones[i];
   					//Flag indica si fue encontrada la opcion en el combo box o no
   					boolean existe = false;
   					//recorre las opciones que posee el combobox
   					for(WebElement optionObtenida : options) {
   					   //verifica si existe la opci�n esperada en el combobox
   						if (optionEsperada.equalsIgnoreCase(optionObtenida.getText())) {
   					    	existe = true;
   					    	break;
   					    }
   					}
   					//Almacena el resultado de la comparacion
   					if(existe)
   						resultado = resultado + "" +i+". Opcion Esperada: "+optionEsperada+" . Estado: Encontrada";
   					else{
   						resultado = resultado + "" +i+". Opcion Esperada: "+optionEsperada+" . Estado: NO encontrada" ;
   						countError ++;
   					}	
   				}	
   				Function.scrollTo(driver, we);
   				Thread.sleep(sleepTimeMs);
   				//clic para visualizar opciones del combo box
   				we.click();
   				
   				//Genera el resultado de la prueba
   				if(countError == 0){
   					System.out.println("VP "+titleVp+" "+detalleVp+" fue detectado correctamente. "+ resultado);
   					estadoVP = true;
   				}else{
   					System.out.println("VP "+titleVp+" "+detalleVp+" no fue detectado correctamente. "+ resultado);
   				}
   			}
   			else{		
   				System.out.println("VP "+titleVp+" "+detalleVp+" no fue detectado correctamente.");
   				return false;
   			}
   		}	        
   		catch(Exception ex){
   			System.out.println("Warning VP "+titleVp+" warning: "+ex.toString());
   			return false;
   		}		
   		
   		return estadoVP;     		
   	}
   	
    /** 
	 * Function: findObjectPresent
	 * Description: busca que el elemento se encuentre desplegado en la pagina
	 * @param driver (WebDriver) Driver con el cual se ejecuta la prueba
	 * @param we (WebElement) Elemento a buscar 
	 * @param titleVp (String) titulo del punto de verificaci�n
	 * @param detalleVp (String) detalle del punto de verificacion
	 * @param cantIntentos (int) nro de intentos que se realizara para buscar el objeto en la pantalla, ya que a veces el objeto no es encontrado debido a que la pagina aun no termina de cargar
	 * @param sleepTimeMs (long) tiempo de espera entre los intentos de busqueda del objecto
	 * @param logResult (LogResult) variable del log de la prueba
	 * @return boolean
	 * @author 
	 * Date: 02-06-2016
	 **/
	public static boolean findObjectPresent(WebDriver driver, WebElement we, String titleVp, String detalleVp,
				int cantIntentos,long sleepTimeMs){
		
		boolean objectPresent = false;

		try{
			//Espera que se cargue la pagina
			waitForLoad(driver);
			//Espera que se despligue el objeto
			objectPresent = waitforExistsObject(driver, we, cantIntentos, sleepTimeMs);
			
			if (objectPresent){
				MarcoObjeto en=new MarcoObjeto();
				en.enmarcarObjeto(driver, we);
			    System.out.println("VP "+titleVp+" "+detalleVp+" fue detectado correctamente");
			    en.desenmarcarObjeto(driver, we);
			}
			else{		
				System.out.println("VP "+titleVp+" "+detalleVp+" no fue detectado correctamente.");
				return false;
			}
		}	        
		catch(Exception ex){
			System.out.println("Warning VP "+titleVp+" warning: "+ex.toString()); 
			return false;
		}		
		return objectPresent;  	 
	}	
	
	/** 
	 * @Function: findObjectForGetText
	 * Description: Busca un objeto identificando e identifica que el texto desplegado sea correcto
	 * @param (WebDriver) driver, (WebElement) we, (WebElement) we2,(String) textObject 
	 * @param int cantIntentos,long sleepTimeMs
	 * @return boolean
	 * @author 
	 * Date:: 17-05-2016
	 **/
	public static boolean findObjectForGetText(WebDriver driver, WebElement we, WebElement we2,String textObject,
				int cantIntentos,long sleepTimeMs){
		boolean objectPresent = false;
		String textObtenido = "";
		try{
			//Espera que se cargue la pagina
			waitForLoad(driver);
			//Espera que se despligue el objeto
			objectPresent = waitforExistsObject(driver, we, cantIntentos, sleepTimeMs);

			textObtenido = we.getText().trim().replaceAll("  "," ") + " " +we2.getText().trim().replaceAll("  "," ");
			System.out.println("gettex(): "+textObtenido);
			if (textObtenido.contains(textObject) && objectPresent){
				objectPresent = true;
			}
			
		}	        
		catch(Exception ex){
			System.out.println("Warning findObjectForGetText() warning: "+ex.toString());
			return false;
		}		
		return objectPresent;  	 
	}	
	
	/** 
	 * @Function: findObjectForGetText
	 * Description: Busca un objeto identificando e identifica que el texto desplegado sea correcto
	 * @param (WebDriver) driver, (WebElement) we, (String) textObject 
	 * @param int cantIntentos,long sleepTimeMs
	 * @return boolean
	 * @author 
	 * Date:: 17-05-2016
	 **/
	public static String findObjectForGetText(WebDriver driver, WebElement we,
				int cantIntentos,long sleepTimeMs){
		boolean objectPresent = false;
		String textObtenido = "";
		try{
			//Espera que se cargue la pagina
			waitForLoad(driver);
			//Espera que se despligue el objeto
			objectPresent = waitforExistsObject(driver, we, cantIntentos, sleepTimeMs);

			if (objectPresent)
				textObtenido = we.getText().trim().replaceAll("  "," ");
			
		
		}	        
		catch(Exception ex){
			return "";
		}		
		return textObtenido;  	 
	}	
	
   
	
	/** 
	 * Function: findImagePresent
	 * Description: Busca que un objeto tipo imagen se encuentre presenta en la pagina.
	 * @param (WebDriver) driver, (WebElement) we, (String) textObject 
	 * @param (String) titleVp, (String) detalleVp, (LogResult) logResul
	 * @return boolean
	 * @author 
	 * Date: 03-05-2016
	 **/
	public static boolean findImagePresent(WebDriver driver, WebElement we, String titleVp, String detalleVp,
			int cantIntentos,long sleepTimeMs){ 
		
		boolean ImagePresent = false;
		
		try{
			waitForLoad(driver);
			
			//Verifica a traves de JavaScript que la imagen exista en la pagina
			ImagePresent = findforExistsImg(driver, we, cantIntentos, sleepTimeMs);
			
			//dependiendo del resultado de la verificaci�n anterior ingresa los parametros al
			//log de resultados de la prueba
	        if (ImagePresent){
//				System.out.println("Pass VP "+titleVp+" "+we.getText());
				MarcoObjeto en=new MarcoObjeto();
				en.enmarcarObjeto(driver, we);
			    System.out.println("VP "+titleVp+" "+detalleVp+" fue detectado correctamente");
			    en.desenmarcarObjeto(driver, we);
			}
			else{		
				System.out.println("Fail VP "+titleVp);
				System.out.println("VP "+titleVp+" "+detalleVp+" no fue detectado correctamente");
				return false;
			}
	        
		}catch(Exception ex){
			System.out.println("Warning VP "+titleVp+" warning: "+ex.toString());
			return false;
		}		
		return ImagePresent;  	
	}
	
	/** 
	 * Function: waitforExistsObject
	 * Description:Espera que un objeto se cargue en la pagina, realiza hasta 15 intentos
	 * para encontrar el objeto y realiza una pausa en la ejecuciÃ³n del script durante 2500 milisegundos
	 * @param (WebDriver) driver, (WebElement) we
	 * @param (String) titleVp, (String) detalleVp, (LogResult) logResul
	 * @return boolean
	 * @author 
	 * Date update: 10-05-2016
	 **/
	public static boolean waitforExistsObject(WebDriver driver, WebElement we, 
			int cantIntentos,long sleepTimeMs){		
		
		Boolean existsObject = false;
		int cont=0;	
		try {			
			
			//Mientas el objeto no exista y no se haya llegado al total de intentos de
			//busqueda del objeto, se espera y verifica que el objeto se despliege
			while (!existsObject && cont <= cantIntentos){
				try {
					//Verifica si esta desplegado el elemento
					if(we.isDisplayed())
						existsObject = true;				
					
				} catch (Exception ex) {
					//
				}	
				//Se detiene la ejecucion del script segun tiempo determinado
				if(!existsObject)
					Thread.sleep(sleepTimeMs);
				//Cuenta la cantidad de intentos de verificacion del elemento
				cont++;
			}
		} catch (Exception ex) {
			System.out.println("Fnc waitforExistsObject. error: "+ex.toString());
			return false;
		}
		
		return existsObject;
		
	}	

	/** 
	 * Function: findforExistsImg
	 * Description: Verifica a traves de JavaScript que la imagen exista en la pagina, realiza hasta 15 intentos
	 * para encontrar el objeto y realiza una pausa en la ejecuci�n del script durante 2500 milisegundos
	 * @param (WebDriver) driver, (WebElement) we, (String) textObject 
	 * @return boolean
	 * @author 
	 * Update Date: 17-05-2016
	 **/
	public static Boolean findforExistsImg(WebDriver driver, WebElement we,
			int cantIntentos,long sleepTimeMs){			
		int cont=0;		
		Boolean ImagePresent = false;		
		try {
		
			while (!ImagePresent && cont <= cantIntentos){
				try{						
					ImagePresent = (Boolean) ((JavascriptExecutor)driver).executeScript(
							"return arguments[0].complete && typeof arguments[0].naturalWidth != "
							+ "\"undefined\" && arguments[0].naturalWidth > 0", we);
				}catch (Exception ex){
//					System.out.println("Fnc findforExistsImg. No se detecto la imagen: "+
//							ex.toString().substring(ex.toString().lastIndexOf("{"), 
//									ex.toString().lastIndexOf("}")));
				}
				Thread.sleep(sleepTimeMs);
				cont= cont+1;	
			}			
		} catch (Exception ex) {
			System.out.println("Fnc findforExistsImg. No se detecto el elemento: "+ex.toString());
			return ImagePresent;
		}	
		
		return ImagePresent;
	}

	
	/** 
	 * Function: waitForLoad
	 * Description: Espera a que la pagina termine de cargar.
	 * @param (WebDriver) driver
	 * @return boolean
	 * @author 
	 * Date: 03-05-2016
	 **/
	public static void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wd) {
                //this will tel if page is loaded
                return "complete".equals(((JavascriptExecutor) wd).executeScript("return document.readyState"));
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        //wait for page complete
        wait.until(pageLoadCondition);
        //lower implicitly wait time
        driver.manage().timeouts().implicitlyWait(4000, TimeUnit.MILLISECONDS);
    }
	
	/** 
	 * Function: verifyTitlePage
	 * Description: Espera a que la pagina termine de cargar.
	 * @param WebDriver driver,
	 * @param String titleVp, String detalleVp
	 * @return boolean
	 * @author 
	 * Date: 11-05-2016
	 **/
	public static boolean verifyTitlePage(WebDriver driver, String textObject,
			String titleVp, String detalleVp){
		
		String tituloObtenido= "";
		
		//Espera que se cargue la pagina
		waitForLoad(driver);

		try{		
			tituloObtenido = driver.getTitle();
			if (tituloObtenido.contains(textObject)) {
				System.out.println("Pass VP "+titleVp);
			    System.out.println("VP "+titleVp+" "+detalleVp+" fue detectado correctamente");
			}
			else{		
				System.out.println("Fail VP "+titleVp);
				System.out.println("VP "+titleVp+" "+detalleVp+" no fue detectado correctamente."
						+ "Resultado Esperado: "+textObject
						+ "Resultado Obtenido: "+tituloObtenido);
				return false;
			}
		}  	        
		catch(Exception ex){
			System.out.println("Warning VP "+titleVp);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @Description: Funci󮠱ue comprueba que el titulo y 'HREF' de un elemento estꮠpresentes en la pagina actual (al finalizar retoricede a la pagina anterior).
	 * @Function: checkLinkByUrlAndTitle
	 * @param (WebDriver) driver, (WebElement) we, (String) textTitle 
	 * @param (String) titleVp, (String) detalleVp, (LogResult) logResul
	 * @return boolean
	 * @author Sergio Quezada
	 * @Date: 03-05-2016
	 */
	public static boolean checkLinkByUrlAndTitle(WebDriver driver, WebElement we, String textTitle, String titleVp, String detalleVp,
			int menu){

		boolean estado = false;
		try{
			String url = we.getAttribute("href").toLowerCase();
			we.click();
			Thread.sleep(3000);
			if (driver.getPageSource().toLowerCase().contains(textTitle.toLowerCase()) && 
					driver.getCurrentUrl().toLowerCase().contains(url)){
				Thread.sleep(3000);
				driver.navigate().back();
				Thread.sleep(3000);
				Actions builder = new Actions(driver);
				builder.moveToElement(we).perform();
				we.click();
				System.out.println("Pass VP "+titleVp+" "+we.getText());
				MarcoObjeto en=new MarcoObjeto();
				en.enmarcarObjeto(driver, we);
			    System.out.println("VP "+titleVp +" "+detalleVp+" fue detectado correctamente");
			    en.desenmarcarObjeto(driver, we);
			    
			    estado= true;
			}
			else{		
				System.out.println("VP "+titleVp +" "+ detalleVp+" no fue detectado correctamente");
			}
		}	        
		catch(Exception ex){
			System.out.println("Warning VP "+titleVp+" warning: "+ex.toString());
		}	
		
		
		return estado; 
	}
		
	/**	 * 
	 * @Description: Funci󮠱ue comprueba que la imagen y 'HREF' de un elemento estꮠpresentes en la pagina actual (al finalizar retoricede a la pagina anterior).
	 * @Function: checkLinkByUrlAndTitle
	 * @param (WebDriver) driver, (WebElement) we,(String) url, (String) textTitle 
	 * @param (String) titleVp, (String) detalleVp, (LogResult) logResul
	 * @return boolean
	 * @author Sergio Quezada
	 */
	public static boolean checkImageLinkByUrl(WebDriver driver, WebElement we,String url, String titleVp, String detalleVp,
			int cantIntentos,long sleepTimeMs){
		Boolean error = false;
		try{
			waitForLoad(driver);
			if (findforExistsImg(driver, we, cantIntentos, sleepTimeMs ) ){
				we.click();
				Thread.sleep(1000);
				if(driver.getCurrentUrl().toLowerCase().contains(url)){
					driver.navigate().back();
					Thread.sleep(1000);
					System.out.println("Pass VP "+titleVp);
					MarcoObjeto en=new MarcoObjeto();
					en.enmarcarObjeto(driver, we);
				    System.out.println("VP "+titleVp +" "+detalleVp+" fue detectado correctamente");
				    en.desenmarcarObjeto(driver, we);
				}
			}
			else{		
				error = true;
			}
		}	        
		catch(Exception ex){
			error = true;
		}	
		
		if (error){
			System.out.println("VP "+titleVp+" "+detalleVp+" no fue detectado correctamente");
		}
		
		return !error;
		 
	}
	
	/** 
	 * @Description: Funci󮠱ue comprueba que la redirecci󮠤e un elemento concuerden con la pagina a la cual redirecciona (al finalizar retoricede a la pagina anterior).
	 * @Function: checkElementLinkByUrl
	 * @param (WebDriver) driver, (WebElement) we,(String) url, (String) textTitle 
	 * @param (String) titleVp, (String) detalleVp, (LogResult) logResul
	 * @return boolean
	 * @author Sergio Quezada
	 * */
	public static boolean checkElementLinkByUrl(WebDriver driver, WebElement we,String url, String titleVp, String detalleVp
			){
		Boolean error = false;
		try{
			waitForLoad(driver);
			we.click();
			Thread.sleep(1000);
			if(driver.getCurrentUrl().toLowerCase().equals(url.toLowerCase())){
				driver.navigate().back();
				Thread.sleep(1000);
				System.out.println("Pass VP "+titleVp);
				MarcoObjeto en=new MarcoObjeto();
				en.enmarcarObjeto(driver, we);
			    System.out.println("VP "+titleVp+" "+detalleVp+" fue detectado correctamente");
			    en.desenmarcarObjeto(driver, we);
			}
			
		}	        
		catch(Exception ex){
			error = true;
		}	
		
		if (error){
			System.out.println("Fail VP "+titleVp);
			System.out.println("VP "+titleVp+" "+detalleVp+" no fue detectado correctamente");
		}
		
		return !error;
		 
	}
	
	/** 
	 * @Description: Funcion que comprueba que el atributo HREF de un elemento concuerden con la direcci�n enviada por parametros (no redirecciona).
	 * @Function: checkLinkByUrlAndTitle
	 * @param (WebDriver) driver, (WebElement) we,(String) href, (String) textTitle 
	 * @param (String) titleVp, (String) detalleVp, (LogResult) logResul
	 * @return boolean
	 * @author Sergio Quezada
	 */	
	public static boolean checkHrefOfElement(WebDriver driver, WebElement we,String href, String titleVp, String detalleVp
			){
		Boolean error = false;
		try{
			waitForLoad(driver);
			Thread.sleep(1000);
			if(we.getAttribute("href").contains(href)){
				Thread.sleep(1000);
				System.out.println("Pass VP "+titleVp);
				MarcoObjeto en=new MarcoObjeto();
				en.enmarcarObjeto(driver, we);
			    System.out.println("VP "+titleVp+" "+detalleVp+" fue detectado correctamente");
			    en.desenmarcarObjeto(driver, we);
			}			
		}	        
		catch(Exception ex){
			error = true;
		}	
		
		if (error){
			System.out.println("VP "+titleVp+" "+detalleVp+" no fue detectado correctamente");
		}
		
		return !error;		 
	}
	

	/** 
	 * @Description: Realiza scroll en la pagina para mostrar elemento
	 * @Function: scrollTo
	 * @param (WebDriver driver, WebElement we
	 * @return boolean
	 */	
	public static void scrollTo(WebDriver driver, WebElement element) {
       ((JavascriptExecutor) driver).executeScript(
               "arguments[0].scrollIntoView();", element);
   }
	
	public static boolean checkDateFormat (WebDriver driver, WebElement we, String titleVp, String detalleVp){
		
		return false;
		
	}
	
	/** 
	 * Function: isAlertPresent
	 * Description: Verifica si se despliega una alerta durante la ejecuci�n de la prueba
	 * @return boolean
	 * @author 
	 * Date: 18-05-2016
	 **/
	public static boolean isAlertPresent() {
        try {
            DriverNavegador.driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
	}

	/** 
	 * Function: acceptAlert
	 * Description: Hace clic en bot�n Aceptar del mensaje de alerta que se despleg� en la pantalla
	 * @author 
	 * Date: 18-05-2016
	 **/
	public static void acceptAlert(){
		DriverNavegador.driver.switchTo().alert();
		DriverNavegador.driver.switchTo().alert().accept();
		DriverNavegador.driver.switchTo().defaultContent();
	}
	
	/** 
	 * Function: cancelAlert
	 * Description: Hace clic en bot�n Cancelar del mensaje de alerta que se despleg� en la pantalla
	 * @author 
	 * Date: 18-05-2016
	 **/
	public static void cancelAlert(){
		DriverNavegador.driver.switchTo().alert();
		DriverNavegador.driver.switchTo().alert().dismiss();
		DriverNavegador.driver.switchTo().defaultContent();
	}
	
    /** 
   	 * Function: selectRbLblEmpresa
   	 * Description: Selecciona Radio Button, seg�n datos del label asociado al Radio Button
   	 * @param textRb (String) Texto del radio Button a seleccionar
   	 * @param radioBtns (List<WebElement>) Listado de radio buttons 
   	 * @param lblRadioBtns (List<WebElement>) Listado de label asociados al radio button
   	 * @author 
   	 * Date: 
   	 **/
	public static boolean selectRbLblEmpresa(String textRb, List<WebElement>radioBtns,  List<WebElement> lblRadioBtns){
		//Contador de radioButton
		int i = 0;
		//estado de la seleccion del radio button si existe elemento a seleccionar es igual true de lo contrario false
		boolean estado = false;
		//Busca el radio Button ha selecciobar segun parametro enviado
		for (WebElement radioBtn : radioBtns) {

			if (lblRadioBtns.get(i).getText().contains(textRb)){
				//existe elemento buscado
				estado = true;
				//Selecciona radio button
				if (!radioBtn.isSelected())
					radioBtn.click();
				//termina for
				break;
			}	
			i++;
		}
		
		return estado;
	}
	
	
	public static List<List<String>> leerArchivo (String nomArchivo, int hoja) throws IOException{
		
		List<List<String>> datosExcel = new ArrayList<List<String>>();
		datosExcel = Excel.getDatosHojaExcel(nomArchivo, hoja);
		
		return datosExcel;
		
	}
	public static List<List<String>> leerArchivoXLSX (String nomArchivo, int hoja) throws IOException{
		
		List<List<String>> datosExcel = new ArrayList<List<String>>();
		datosExcel = Excel.getDatosHojaExcelXSSF(nomArchivo, hoja);
		
		return datosExcel;
		
	}
	
//	public static void verificarFomatoArchivo (List<List<String>> datosArchivo,
//			List<List<String>> formatoArchivo){
//		
//		
//		for (int i = 1; i < datosArchivo.size(); i++) {
//			for (int j = 1;  j < datosArchivo.size(); i++){
//				List<?> list = (List<?>) datosArchivo.get(i);
//				
//				patronesBusqueda(list.get(j).toString(), "");
//				
//			}
//		}
//		
//
//	}
	
	
	public static boolean patronesBusqueda(String sValue, String patron, String sValueBlank){
		
		try{
			
			if (sValue.trim().equals("") && sValueBlank.equals("S")){
				return true;
			} else {
				Pattern pat = Pattern.compile(patron);
				Matcher mat = pat.matcher(sValue);
				if (mat.matches())
					return true;
				else
					return false;
		      
			}
		}catch (Exception e) {
			return false;
		}		
		
	}
	
	public static boolean verificaLargoString(String sValue, String largoMax, String sValueBlank){
		boolean flagLargo = false;
		try{
			
			if (sValue.length() <= Integer.parseInt(largoMax) && sValue.length()>0 && !sValueBlank.equals("S") ){
				flagLargo = true;
			} else if (sValue.length()==0 && sValueBlank.equals("S") ){					
				flagLargo = true;
			} else if (sValue.length() <= Integer.parseInt(largoMax) && sValue.length()>0 && sValueBlank.equals("S") ){
				flagLargo = true;
			}
		}catch (Exception e) {
			return false;
		}
		return flagLargo;		
		
	}
	
//	public static boolean verificaCampoVacio(String sValue, String sCampoDependencia, 
//			String sValueDependencia, String sValueDefault, String sValueBlank){
//		
//		boolean flagVacio = false;
//		try{
//			if(!sCampoDependencia.equals(sValueDependencia)){
//				flagVacio = sValue.length() > 0;
//			}else if(sCampoDependencia.trim().equals(sValueDependencia)){
//				flagVacio = sValue.equals(sValueDefault);
//		}
//				
//		}catch (Exception e) {
//			return false;
//		}		
//		
//		return flagVacio;
//	}
	
	public static boolean verificaCampoValorDefecto(String sValue, String sValueDefault){
		
		boolean flagDefault = false;
		try{
			if(sValue.equals(sValueDefault))
				flagDefault = true;
				
		}catch (Exception e) {
			return false;
		}		
		
		return flagDefault;
	}
	
	public static boolean verificaCampoExtra(String sValue, String sValueEsperado){
		
		boolean flag = false;
		try{
			if(sValue.equals(sValueEsperado))
				flag = true;
				
		}catch (Exception e) {
			return false;
		}		
		
		return flag;
	}
	
	public static boolean verificaLargoString(String sValue, String largoMax, String largoMin, String sValueBlank){
		boolean flagLargo = false;
		try{
			
			if (sValue.length() <= Integer.parseInt(largoMax) && sValue.length()>=Integer.parseInt(largoMin)){
				flagLargo = true;		
			}
		}catch (Exception e) {
			return false;
		}
		return flagLargo;		
		
	}
	
}
