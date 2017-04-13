package com.tsoftlatam.automatizacion.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.tsoftlatam.automatizacion.util.ArchivoProperties;

public class OracleAdapter implements IDBAdapter{

	@Override
	public Connection getConexion() {
		Connection connection = null;
		try {
			ArchivoProperties propiedades = new ArchivoProperties();
			Properties properties = propiedades.getProperties("conexion.properties");
			Class.forName(properties.getProperty("p_classNameOracle"));
			connection = DriverManager.getConnection(properties.getProperty("p_urlOracle") + properties.getProperty("p_bdOracle"),
					properties.getProperty("p_userOracle"), properties.getProperty("p_passOracle"));
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		return connection;
	}

}
