package com.tsoftlatam.automatizacion.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.tsoftlatam.automatizacion.util.ArchivoProperties;

public class MySQLAdapter implements IDBAdapter{

//	@Override
//	public Connection getConexion() {
//		Connection connection = null;
//		try {
//			ArchivoProperties propiedades = new ArchivoProperties();
//			//Properties properties = propiedades.getProperties("conexion.properties");
//			Class.forName("com.mysql.jdbc.Driver");
//			connection = DriverManager.getConnection("jdbc:mysql://192.168.214.4:3306/moodle",
//					"root", "Avianca2016$");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.getStackTrace();
//		}
//		return connection;
//	}
	 @Override
	    public Connection getConexion() {
	        Connection connection = null;
	        try {
	            ArchivoProperties propiedades = new ArchivoProperties(); 
	            Properties properties = propiedades.getProperties("conexion.properties");
	            Class.forName(properties.getProperty("p_className"));
	            connection = DriverManager.getConnection(properties.getProperty("p_url") + properties.getProperty("p_bd"),
	                    properties.getProperty("p_user"), properties.getProperty("p_pass"));
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.getStackTrace();
	        }
	        return connection;
	    }
}
