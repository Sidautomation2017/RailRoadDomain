package com.pkg.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.groovy.transform.sc.transformers.BooleanExpressionTransformer;

import com.pkg.base.ReusableMethods;
import com.pkg.repositories.Configuration;
import com.pkg.testcases.SetSystemStatus;

public class ExcelReader {

	//static String testInputPath = Configuration.testDataPath + Configuration.fileName;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	static String requestBody;
	//static String testInputPath=Configuration.testDataPath+Configuration.SST_PayloadPath;	
	
	public static void main(String []arg) throws IOException{
		
		System.out.println(getData(SetSystemStatus.testInputPath, 0, 1).get(2));
		
	}


	public static ArrayList<String> getData(String testInputPath, int sheetIndex,int id) throws IOException {
	
		 ArrayList<String> data = new ArrayList<String>();		
		File file = new File(testInputPath);
		try {
			FileInputStream fileinput = new FileInputStream(file);
			workbook = new XSSFWorkbook(fileinput);
			int sheetcount = workbook.getNumberOfSheets();
			//System.out.println(sheetcount);
			for (int i = 0; i < sheetcount; i++) {
				String sheetName = workbook.getSheetName(i);
				//System.out.println(sheetName);
				sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				Row firstrow = rows.next();
				Iterator<Cell> cell = firstrow.cellIterator();
				int k = 0;
				int coloumn = 0;
				// to check whether next cell value is present
				while (cell.hasNext()) {
					Cell value = cell.next();
					if (value.getStringCellValue().equalsIgnoreCase("Sr.No.")) {
						coloumn = k;
						ReusableMethods.storeValue("Sr.No.Index", k);
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("TestScenario")){
						ReusableMethods.storeValue("TestScenario_Index", k);
						
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("WayBillSettlementIdentifier")){
						ReusableMethods.storeValue("WayBillSettlementIdentifier_Index", k);
						
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("RemoveLockReject")){
						ReusableMethods.storeValue("RemoveLockReject_Index", k);
						
					}
					
					
					else if (value.getStringCellValue().equalsIgnoreCase("OverrideSystemStatus")){
						ReusableMethods.storeValue("OverrideSystemStatus_Index", k);
						
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("StatusCode")){
						ReusableMethods.storeValue("StatusCode_Index", k);
						
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("ExpectedSettlementStatusID")){
						ReusableMethods.storeValue("ExpectedSettlementStatusID_Index", k);
						
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("ExpectedPrURRWIN_ActiveCount")){
						ReusableMethods.storeValue("ExpectedPrURRWIN_ActiveCount_Index", k);
						
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("ISSIdentifier")){
						ReusableMethods.storeValue("ISSIdentifier_Index", k);
						
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("ExpectedSettlementStatusID")){
						ReusableMethods.storeValue("ExpectedSettlementStatusID_Index", k);
						
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("UpdateTypeIndicator")){
						ReusableMethods.storeValue("UpdateTypeIndicator_Index", k);
						
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("OverrideDisputeStatus")){
						ReusableMethods.storeValue("OverrideDisputeStatus_Index", k);
						
					}
					
					else if (value.getStringCellValue().equalsIgnoreCase("ExpectedDisputeStatusID")){
						ReusableMethods.storeValue("ExpectedDisputeStatusID_Index", k);
						
					}
					k++;
				}
				
				data.clear();
				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(coloumn).getNumericCellValue()==id) {
						Iterator<Cell> cv = r.cellIterator();
						while (cv.hasNext()) {
							Cell c = cv.next();
							if(c.getCellTypeEnum()==CellType.STRING){
							data.add(c.getStringCellValue());
							}
						
							else{
								data.add(NumberToTextConverter.toText(c.getNumericCellValue()));							
								
							}
							
						}

					
					}

				}

			}
			System.out.println(data);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return data;

	}

}
