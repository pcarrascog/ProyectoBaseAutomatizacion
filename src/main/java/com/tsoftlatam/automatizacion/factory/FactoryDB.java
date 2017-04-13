package com.tsoftlatam.automatizacion.factory;

public class FactoryDB {
	
	public static IDBAdapter getDBAdapter(DBType dbType){
		switch (dbType) {
		case MySQL:
			return new MySQLAdapter();
		case Oracle:
			return new OracleAdapter();	
		default:
			throw new IllegalArgumentException();
		}
	}
}
