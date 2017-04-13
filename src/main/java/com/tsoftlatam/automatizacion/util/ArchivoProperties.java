package com.tsoftlatam.automatizacion.util;

import java.io.IOException;
import java.util.Properties;

public class ArchivoProperties {

	public Properties getProperties(String archivoProperties) {
		try {
			// se crea una instancia a la clase Properties
			Properties propiedades = new Properties();
			// se leen el archivo .properties
			propiedades.load(getClass().getResourceAsStream("/" + archivoProperties));
			// si el archivo de propiedades NO esta vacio retornan las propiedes
			// leidas
			if (!propiedades.isEmpty()) {
				return propiedades;
			} else {
				// sino retornara NULL
				return null;
			}
		} catch (IOException ex) {
			return null;
		}
	}
}
