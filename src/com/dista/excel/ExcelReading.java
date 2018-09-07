package com.dista.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReading {

	public static void main(String[] args) throws IOException {
		//HashMap<String,String> hm	=storedatainhashmap("AutomationData.xlsx", "Sheet1", 1);
		//hm.get("uname");
		
	HashMap[]	hmarr=storedatainhashmaparr("AutomationData.xlsx", "CreateJob");
	int l=hmarr.length;
	for (int k=0;k<l;k++){
		HashMap<String,String> hm	=hmarr[k];
		String val1=hm.get("uname");
		String val2=hm.get("pwd");
		System.out.println(val1+"and"+val2);
	}
	
		//System.out.println(hm.get("uname"));
		/*HashMap<String,String>    hm=new HashMap<String,String>();
		hm.put("ndls", "new delhi");
		hm.put("dli", "delhi");
		hm.put("ndls", "noida");
		
		String value=hm.get("ndls");
		System.out.println(value);*/
		
		
				
		
		/*String[][]   a=storedatainarray();
		int l=a.length;
		System.out.println(l);
		for (int m=0;m<l;m++){
			String[] arr=a[m];
			int l1=arr.length;
			System.out.println(l1);
			for (int k=0;k<l1;k++){
				String str=arr[k];
				System.out.println(str);
			}
		}*/
		
		
		
		
		
		//reading_xls();
	
		

	}
	
	
	public static void reading_xls() throws IOException{
		
		
FileInputStream    fis=new FileInputStream("ram.xls");
		
		HSSFWorkbook  wbook=   new HSSFWorkbook(fis);
		HSSFSheet    sheetobj=wbook.getSheet("Sheet1");
		
		int rcnt=sheetobj.getLastRowNum();
		System.out.println(rcnt);
		
		
		
		
		
	}
	
	public static Workbook getwbook(String filename ) throws IOException{
		String extname=filename.split("\\.")[1];
		FileInputStream    fis=new FileInputStream(filename);
		
		
		Workbook wbook=null;
		if (extname.equalsIgnoreCase("xls")==true){
			
	   wbook =new HSSFWorkbook(fis);
		}else if (extname.equalsIgnoreCase("xlsx")){
			
			 wbook=new XSSFWorkbook(fis);
		}
		
		return wbook;
	}
	
	
	public static int getrowcnt(String wbookpath,String sname) throws IOException{
		Workbook wbook=getwbook(wbookpath);
		Sheet sheetobj=wbook.getSheet(sname);
		int rnum=sheetobj.getLastRowNum();
		return rnum;
	}
	

	
	
public static HashMap<String, String> storedatainhashmap(String wpath,String sname,int rnum) throws IOException{
	HashMap<String,String>    hm=new HashMap<String,String>();
	  Workbook   wbook=  getwbook(wpath);
	  Sheet sheetobj=wbook.getSheet(sname);
	  Row    firstrowobj=sheetobj.getRow(0);
	  Row    rowobj=sheetobj.getRow(rnum);
	int cellnum=  rowobj.getLastCellNum();
	for (int i=0;i<cellnum;i++){
		Cell cellobj=rowobj.getCell(i);
		String keyname=firstrowobj.getCell(i).getStringCellValue();
		String keyval=cellobj.getStringCellValue();
		hm.put(keyname, keyval);
	}
	  
	return hm;  
}




public static HashMap[] storedatainhashmaparr(String wpath,String sname) throws IOException{
	
	  Workbook   wbook=  getwbook(wpath);
	int rnum=  getrowcnt(wpath, sname);
	HashMap[] hmarr= new HashMap[rnum];
	  Sheet sheetobj=wbook.getSheet(sname);
	  Row    firstrowobj=sheetobj.getRow(0);
	 
	int cellnum=  firstrowobj.getLastCellNum();
	for (int j=1;j<=rnum;j++){
		HashMap<String,String>    hm=new HashMap<String,String>();
		 Row    rowobj=sheetobj.getRow(j);
	for (int i=0;i<cellnum;i++){
		Cell cellobj=rowobj.getCell(i);
		String keyname=firstrowobj.getCell(i).getStringCellValue();
		String keyval=cellobj.getStringCellValue();
		hm.put(keyname, keyval);
	}
	hmarr[j-1]=hm;
	} 
	return hmarr;  
}

}
