package com.dista.methods;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class DynamicXml 
{
	protected DocumentBuilderFactory domFactory = null;
	protected DocumentBuilder domBuilder = null;
	ByteArrayOutputStream baos = null;
	OutputStreamWriter osw = null;
	
	public DynamicXml()
	{
	    try 
	    {
	        domFactory = DocumentBuilderFactory.newInstance();
	        domBuilder = domFactory.newDocumentBuilder();
	    } 
	    catch (FactoryConfigurationError exp) 
	    {
	        System.err.println(exp.toString());
	    }
	    catch (ParserConfigurationException exp) 
	    {
	        System.err.println(exp.toString());
	    } 
	    catch (Exception exp) 
	    {
	        System.err.println(exp.toString());
	    }
	}
	
	public void convertFile(String xlsFileName, String xmlFileName) throws IOException
	{
		// String xmlWithSpecial = "!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\"";
	    try 
	    {
	        Document newDoc = domBuilder.newDocument();
	        Element rootElement = newDoc.createElement("suite");
	        rootElement.setAttribute("name", "Regression suite");
	        rootElement.setAttribute("verbose", "1");
	        
	        
	    	newDoc.appendChild(rootElement);

	        
	        Element rootElement2 = newDoc.createElement("listeners");
	        rootElement.appendChild(rootElement2);

	        
	        Element rootElement6 = newDoc.createElement("listener");
	        rootElement6.setAttribute("class-name", "com.dista.listeners.RetryListener");
	        rootElement2.appendChild(rootElement6);
	        
/*	        Element rootElement7 = newDoc.createElement("listener");
	        rootElement7.setAttribute("class-name", "com.dista.listeners.ExtentListener");
	        rootElement2.appendChild(rootElement7);*/

/*	        Element rootElement8 = newDoc.createElement("listener");
	        rootElement8.setAttribute("class-name", "com.dista.listeners.CustomMethods");
	        rootElement2.appendChild(rootElement8);*/
	        
	        Element rootElement9 = newDoc.createElement("listener");
	        rootElement9.setAttribute("class-name", "com.dista.listeners.TestListener");
	        rootElement2.appendChild(rootElement9);
	        
	        
	        FileInputStream ExcelFile;
			ExcelFile = new FileInputStream(xlsFileName);		//files stored at specified path contains URLs
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			XSSFSheet ExcelWSheet = ExcelWBook.getSheet("XML Data");
			Iterator <Row> ri = ExcelWSheet.iterator(); 
			Row r = ri.next();
			
			boolean loop_condition2 = true, loop_condition3=true;
	
			do
			{
				
				boolean loop_condition = true;
				
				//System.out.println("Text"+r.getCell(0).getStringCellValue());
				
				Element rowElement = newDoc.createElement("test");
				rowElement.setAttribute("name", r.getCell(0).getStringCellValue());
				rootElement.appendChild(rowElement);
				
				Element curElement = newDoc.createElement("classes");
				rowElement.appendChild(curElement);
				
				Element curElement2 = newDoc.createElement("class");
	            curElement2.setAttribute("name", "com.dista.test.automation."+r.getCell(0).getStringCellValue());
	            curElement.appendChild(curElement2);   
	            
	            Element curElement3 = newDoc.createElement("methods");
	            curElement2.appendChild(curElement3);
	            
	            r= ri.next();
	            
	            while(loop_condition )
	            {
	            	//System.out.println(r.getCell(1).getStringCellValue());
	                
	            	String option=null;
	
	                try
	                {
	                	switch (r.getCell(2).getStringCellValue())
	                	{
	                		case "Y":
	                			option = "include";
	                			break;
	                			
	                		case "N":
	                			option = "exclude";
	                            break;
	                            
	                          default:
	                            System.out.println("Invalid Data");
	                            break;
	                	}                          			
	                }
	                catch(NullPointerException npe)
	                {
	                	option= "exclude";
	                }
	                
	                //System.out.println(option);
	        		Element curElement4 = newDoc.createElement(option);
	        		curElement4.setAttribute("name", r.getCell(1).getStringCellValue());
	                curElement3.appendChild(curElement4);
	                                
		            if (ri.hasNext())
		            {
		            	r= ri.next();
		    		
		                try
		                {                       	
		                	if(!r.getCell(1).getStringCellValue().isEmpty())
		                		loop_condition= true;
		                	else
		                		loop_condition=false;
		            	}
		                catch(NullPointerException npe)
		                {
		                	loop_condition=false;
		                }
		            }
		            else
		            {
		            	loop_condition=false;
		            	loop_condition3= false;
		            }
	            }
	            
	            if(loop_condition3)
	            {
		            try
		            {                       	
		            	if(!r.getCell(0).getStringCellValue().isEmpty())
		            		loop_condition2= true;
		            	else
		            		loop_condition2=false;
		            }
		            catch(NullPointerException npe)
		            {
		            	loop_condition2=false;
		            }
	            }
	            else
	            	loop_condition2=false;
			}
			while(loop_condition2);
	
			
			baos = new ByteArrayOutputStream();
			osw = new OutputStreamWriter(baos);
	
			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
			aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
			aTransformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        DOMImplementation domImpl = newDoc.getImplementation();
	        // <!DOCTYPE suite PUBLIC "-//Oberon//YOUR PUBLIC DOCTYPE//EN" "YOURDTD.dtd">
	        //<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">

	        // <!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >
		    DocumentType doctype = domImpl.createDocumentType("doctype",
		    		"", "http://testng.org/testng-1.0.dtd");
		    aTransformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
	          
	        Source src = new DOMSource(newDoc);
	        Result result = new StreamResult(new File(xmlFileName));
	        aTransformer.transform(src, result);
	
	        osw.flush();
	        System.out.println(new String(baos.toByteArray()));
	        
	        ExcelWBook.close();
	
		}
	    catch (Exception exp) 
	    {
	    	exp.printStackTrace();
	    } 
	           
	    osw.close();
	    baos.close();
	}
}


