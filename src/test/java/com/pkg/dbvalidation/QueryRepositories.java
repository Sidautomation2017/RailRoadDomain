package com.pkg.dbvalidation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;

import com.pkg.repositories.Configuration;
import com.pkg.testcases.SetSystemStatus;

public class QueryRepositories {

	static Connection connection=SetSystemStatus.connection;
	static String resultdata;
	public static int j,l;
	static int k=0;	
	
	public static void main(String []args) throws ClassNotFoundException{
		l=getDisputStatus(183);
		
	}

	public static String getWayBillStatus(int i, String column) {
		try {
				
			Statement st = connection.createStatement();
			String sqlQuery = "Select * from wayBillSettlement where Identifier=" + i;
			ResultSet resSet = st.executeQuery(sqlQuery);
			while (resSet.next()) {
				resultdata=resSet.getString(column);
				System.out.println(resultdata);
			}
			
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultdata;

	
	}
	


	public static int getPUActiveCount(int i){
		try {
			
			/*boolean result;
			CallableStatement cstmt = connection.prepareCall("{call [Sampletest](?)}");
			cstmt.setInt(1, i);		
			result=cstmt.execute();
			System.out.println(result);
			if(result){
			ResultSet rs=cstmt.getResultSet();
			while(rs.next()){
				String y=rs.getString("Identifier");
				System.out.println(y);
			}*/
							
			
			Statement st = connection.createStatement();
			String sqlQuery = "select count (*) as activeCount from PriorityURRWIN where waybillsettlementid="+i+" and isActive=1";
			ResultSet resSet = st.executeQuery(sqlQuery);
			while(resSet.next()){
				j=resSet.getInt("activeCount");
			}
			System.out.println("Active coun"+j);	
		
		
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return j;
		
				
	}
	
	public static int getISId(int i) {
		try {
			
				Statement st = connection.createStatement();
			String sqlQuery = "select * from PriorityURRWIN where waybillsettlementid="+i+" and isActive=1";
			ResultSet resSet = st.executeQuery(sqlQuery);
			while(resSet.next()){
				k=resSet.getInt("InterlineSettlementId");
			}
		
			System.out.println(k);
			
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return k;
			
	}
	
	
	public static int getDisputStatus(int i) throws ClassNotFoundException{
	
		try {
			//connection=DataBaseConnection.setUp_DBConnection(Configuration.iss_transaction);
			boolean result;
			String y=null;
			CallableStatement cstmt = connection.prepareCall("{call [1120_GetISSID](?)}");
			cstmt.setInt(1, i);		
			result=cstmt.execute();
			if(result){
			ResultSet rs=cstmt.getResultSet();
			if(rs.next()){
				y=rs.getString("identifier");
				System.out.println(y);
			}			
		
			else{
				CallableStatement cstmt1 = connection.prepareCall("{call [1110_GetISSID](?)}");
				cstmt1.setInt(1, i);		
				result=cstmt1.execute();
				if(result){
					ResultSet rs1=cstmt1.getResultSet();
					if(rs1.next()){
						y=rs1.getString("identifier");
						System.out.println(y);
					}
					
					}
				
			}
			
			/*while(rs.next()){
				y=rs.getString("identifier");
				System.out.println(y);
			}*/
			
			
			}
			
			Statement st = connection.createStatement();
		
		String sqlQuery = "select * from interlinesettlement where waybillsettlementid="+i+" and identifier="+y;
		System.out.println(sqlQuery);
		ResultSet resSet = st.executeQuery(sqlQuery);
		while(resSet.next()){
			k=resSet.getInt("DisputeStatusId");
		}
	
		System.out.println(k);
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		
		return k;
		
		}
	
}
	


