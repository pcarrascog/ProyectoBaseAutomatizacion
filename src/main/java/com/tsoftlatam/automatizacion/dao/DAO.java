package com.tsoftlatam.automatizacion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.log4j.Logger;

import com.tsoftlatam.automatizacion.factory.DBType;
import com.tsoftlatam.automatizacion.factory.FactoryDB;
import com.tsoftlatam.automatizacion.factory.IDBAdapter;

public class DAO {
	
	public List<String> schemaTablaMySQL() throws SQLException{
		List<String> columnas = new ArrayList<>();
		IDBAdapter adaptador = FactoryDB.getDBAdapter(DBType.MySQL);		
		Connection conexion = adaptador.getConexion();
		PreparedStatement consulta = null;
		try {
			String query = "select * from TB_PERSONAS_MOODLE";
			consulta = conexion.prepareStatement(query);
			ResultSet rs = consulta.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			for (int i = 1; i < rsmd.getColumnCount(); i++) {
				columnas.add(rsmd.getColumnName(i));
			}
		} catch (Exception e) {
			Logger.getLogger(DAO.class.getName()).log(null, Level.SEVERE, e);
		}finally{
			consulta.close();
			conexion.close();			
		}
		return columnas;
	}
	
	public List<String> schemaTablaOracle() throws SQLException{
		List<String> columnas = new ArrayList<>();
		IDBAdapter adaptador = FactoryDB.getDBAdapter(DBType.Oracle);		
		Connection conexion = adaptador.getConexion();
		PreparedStatement consulta = null;
		try {
			String query = "select * from TB_PERSONAS_MOODLE";
			consulta = conexion.prepareStatement(query);
			ResultSet rs = consulta.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				columnas.add(rsmd.getColumnName(i));
			}			
		} catch (Exception e) {			
			Logger.getLogger(DAO.class.getName()).log(null, Level.SEVERE, e);			
		}finally{
			consulta.close();
			conexion.close();
		}
		return columnas;
	}
}
