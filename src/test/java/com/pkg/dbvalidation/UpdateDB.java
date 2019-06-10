package com.pkg.dbvalidation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import com.pkg.base.ReusableMethods;
import com.pkg.repositories.Configuration;
import com.pkg.testcases.DataSetter;
import com.pkg.testcases.SetSystemStatus;
import com.pkg.utilities.DataSetUp;

public class UpdateDB {
	static Connection connection = DataSetter.connection;
	static ArrayList<String> data = new ArrayList<String>();

	static String resultdata;
	static String sqlQuery;
	static String date;

	public static String updateDB(String tablename, String coloumnName, String value, String identifier, String dbName,
			String filtercolumn, String storeColumn) throws ClassNotFoundException, SQLException {
		connection = DataBaseConnection.setUp_DBConnection(dbName);
		Statement st = connection.createStatement();
		switch (value) {

		case "currentdate":
			date = ReusableMethods.getUTCdatetime();
			System.out.println(date);
			sqlQuery = updateQuery(tablename, coloumnName, date, identifier, dbName, filtercolumn, storeColumn);
			DataSetter.updatevalue = date;
			System.out.println(DataSetter.updatevalue);
			break;

		case "currentdate+1":
			date = ReusableMethods.customizingDays(1);
			System.out.println(date);
			sqlQuery = updateQuery(tablename, coloumnName, date, identifier, dbName, filtercolumn, storeColumn);
			DataSetter.updatevalue = date;
			System.out.println(DataSetter.updatevalue);
			break;

		case "currentdate-1":
			date = ReusableMethods.customizingDays(-1);
			System.out.println(date);
			sqlQuery = updateQuery(tablename, coloumnName, date, identifier, dbName, filtercolumn, storeColumn);
			DataSetter.updatevalue = date;
			System.out.println(DataSetter.updatevalue);
			break;

		case "null":
			sqlQuery = updateQueryfornull(tablename, coloumnName, value, identifier, dbName, filtercolumn, storeColumn);
			break;

		default:
			sqlQuery = updateQuery(tablename, coloumnName, value, identifier, dbName, filtercolumn, storeColumn);
			break;

		}

		System.out.println(sqlQuery);
		st.execute(sqlQuery);
		String sqlQuery2;
		sqlQuery2 = selectQuery(tablename, coloumnName, value, identifier, dbName, filtercolumn, storeColumn);
		System.out.println(sqlQuery2);
		ResultSet result = st.executeQuery(sqlQuery2);
		while (result.next()) {
			if (!storeColumn.equalsIgnoreCase("NA")) {
				if(storeColumn.equalsIgnoreCase("ISSIdentifier")){
					resultdata = result.getString("Identifier");
					ReusableMethods.storeValue(storeColumn, resultdata);
					break;
					
				}
				resultdata = result.getString(storeColumn);
				ReusableMethods.storeValue(storeColumn, resultdata);
			}
			resultdata = result.getString(coloumnName);
			System.out.println(resultdata);
			break;
		}
		return resultdata;

	}

	public static String updateQuery(String tablename, String coloumnName, String value, String identifier,
			String dbName, String filtercolumn, String storeColumn) {
		String query;

		switch (identifier) {

		case "GSV1":
			query = "Update " + tablename + " set " + coloumnName + "='" + value + "' where " + filtercolumn + "="
					+ ReusableMethods.getValue("Identifier");
			break;

		case "GSV2":
			query = "Update " + tablename + " set " + coloumnName + "='"+ value + "' where " + filtercolumn + "="
					+ ReusableMethods.getValue("RevenueWayBillid");
			break;
			
		case "GSV3":
			query="Update"+tablename+"set"+coloumnName + "='"+ value + "' where " + filtercolumn + "="
					+ ReusableMethods.getValue("ISSIdentifier");

		default:
			query = "Update " + tablename + " set " + coloumnName + "='"+ value + "' where " + filtercolumn + "="
					+ identifier;
			break;

		}
		return query;

	}

	public static String updateQueryfornull(String tablename, String coloumnName, String value, String identifier,
			String dbName, String filtercolumn, String storeColumn) {
		String query;

		switch (identifier) {

		case "GSV1":
			query = "Update " + tablename + " set " + coloumnName + "=" + value + " where " + filtercolumn + "="
					+ ReusableMethods.getValue("Identifier");
			break;
		
		case "GSV2":
			query = "Update " + tablename + " set " + coloumnName + "=" + value + " where " + filtercolumn + "="
					+ ReusableMethods.getValue("RevenueWayBillid");
			break;
			
		case "GSV3":
			query="Update"+tablename+"set"+coloumnName + "='"+ value + "' where " + filtercolumn + "="
					+ ReusableMethods.getValue("ISSIdentifier");
			
		default:
			query = "Update " + tablename + " set " + coloumnName + "=" + value + " where " + filtercolumn + "="
					+ identifier;
			break;

		}
		return query;

	}
	
	public static String selectQuery(String tablename, String coloumnName, String value, String identifier,
			String dbName, String filtercolumn, String storeColumn) {
		String query;

		switch (identifier) {

		case "GSV1":
			query = "Select * from " + tablename + " where " + filtercolumn + "="
					+ ReusableMethods.getValue("Identifier");
			break;

		case "GSV2":
			query = "Select * from " + tablename + " where " + filtercolumn + "="
					+ ReusableMethods.getValue("RevenueWayBillid");
			break;
			
		case "GSV3":
			query="Select * from " + tablename + " where " + filtercolumn + "="
					+ ReusableMethods.getValue("ISSIdentifier");

		default:
			query = "Select * from " + tablename + " where " + filtercolumn + "=" + identifier;
			break;

		}
		return query;

	}

}
