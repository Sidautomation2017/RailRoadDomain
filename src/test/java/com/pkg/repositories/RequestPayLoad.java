package com.pkg.repositories;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pkg.base.ReusableMethods;
import com.pkg.testcases.EDI_Validation;
import com.pkg.testcases.SetSystemStatus;

public class RequestPayLoad {
	 final static Logger logger=LogManager.getLogger(RequestPayLoad.class.getName());
	static HashMap<String,Object> map=new HashMap<String, Object>();
	
	public static String postToken(){
		String message="{\"UserName\": \"Sidheshwar.Tondare@cloudmoyo.com\",\"Password\": \"Sptapr19$\"}";
		return message;
		
	}
	
	public static HashMap<String,Object> postEDIValidate(int id){

		map.put("editType", EDI_Validation.arrayData.get(id).get(2));		
		map.put("requestType", EDI_Validation.arrayData.get(id).get(3));
		map.put("ediData",EDI_Validation.arrayData.get(id).get(4));		
		logger.info(map);
		return map;		
		
	}
	
	public static HashMap<String,Object> postSyStemProcess(int id){

		map.put("WayBillSettlementIdentifier", SetSystemStatus.arrayData.get(id).get(ReusableMethods.getValue("WayBillSettlementIdentifier_Index")));		
		map.put("RemoveLockReject", SetSystemStatus.arrayData.get(id).get(ReusableMethods.getValue("RemoveLockReject_Index")));
		String ovrrideSystemStatus=SetSystemStatus.arrayData.get(id).get(ReusableMethods.getValue("OverrideSystemStatus_Index"));	
		map.put("OverrideSystemStatus",customizedString(ovrrideSystemStatus));
		if(!SetSystemStatus.arrayData.get(id).get(ReusableMethods.getValue("UpdateTypeIndicator_Index")).equalsIgnoreCase("NA")){
			map.put("UpdateTypeIndicator", SetSystemStatus.arrayData.get(id).get(ReusableMethods.getValue("UpdateTypeIndicator_Index")));			
		}
	
		String  overrideDisputeStatus=SetSystemStatus.arrayData.get(id).get(ReusableMethods.getValue("OverrideDisputeStatus_Index"));
		if(!overrideDisputeStatus.equalsIgnoreCase("NA")){
		map.put("OverrideDisputeStatus", customizedString(overrideDisputeStatus));
		}
		
		logger.info(map);
		System.out.println(map);
		return map;		
		
		
		
	}
	
	
	public static String customizedString(String string){
		if(string.equals("null")){
			string="";
		}
		
		return string;
	}
	

}
