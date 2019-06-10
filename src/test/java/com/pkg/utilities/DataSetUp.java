package com.pkg.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pkg.base.ReusableMethods;

public class DataSetUp {
	//static String testInputPath="C:\\MMCS_Workspace\\Automation\\MMCS_ISS_APITesting\\src\\test\\resources\\TestData\\TestDataSetUp.xlsx";
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	static String requestBody;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
	
		for (int i=1;i<9;i++){
		//readExcel(i);
		}
		
		}

	public static ArrayList<String> readExcel(String testInputPath, int sheetIndex,int id) throws IOException {
		

		 ArrayList<String> data = new ArrayList<String>();		
		File file = new File(testInputPath);
		try {
			FileInputStream fileinput = new FileInputStream(file);
			workbook = new XSSFWorkbook(fileinput);
			int sheetcount = workbook.getNumberOfSheets();
			//System.out.println(sheetcount);
			for (int i = 0; i < sheetcount; i++) {
				String sheetName = workbook.getSheetName(sheetIndex);
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
						//ReusableMethods.storeValue("Sr.No.Index", k);					
						}
					
					k++;
				}
				
				//data.clear();
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

						System.out.println(data);

					}

				}
	}
	
	
	

}
 catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return data;
	}
}
