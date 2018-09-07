package com.dista.methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zeroturnaround.zip.ZipUtil;


public class ResultRecording {
	
//public void writeIntoFile(WebDriver driver, String result, String report_name, String textfileURL, String screenshotURL) throws IOException{
//		
//	try{
//		System.out.println(result);
//		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//		Date d= new Date();
//		
//		File testingresults= new File(textfileURL);	//report testing results will be write to text file stored in the specified path 
//		FileWriter fw= new FileWriter(testingresults.getAbsoluteFile(),true);
//		if(!result.equals("Passed")){
//			
//			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//			FileUtils.copyFile(scrFile, new File(screenshotURL+df.format(d).replace('/', ' ')+"/"+report_name+".png"));	//Path where screenshots of failed reports will be saved
//			}
//		fw.write(df.format(d)+" - "+report_name+" : "+result);
//		fw.append(System.lineSeparator());
//		fw.close();
//	}
//	catch(Exception e ){
//		e.printStackTrace();
//	}
//		
//	}
	
	public void writeMail(String mail_subject, String report_name) throws IOException {
		
		
		FileInputStream ExcelFile = new FileInputStream("Support/AutomationData.xlsx");	//files stored at specified path contains URLs on which PTC creation needs to be tested
		XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
		XSSFSheet ExcelWSheet = ExcelWBook.getSheet("Email Credentials");
						
		final String s_email_id= ExcelWSheet.getRow(2).getCell(0).getStringCellValue();
		final String s_email_password= ExcelWSheet.getRow(2).getCell(1).getStringCellValue();
		
		System.out.println(s_email_id);
		System.out.println(s_email_password);

		String to= ExcelWSheet.getRow(4).getCell(0).getStringCellValue();
		String CC1= ExcelWSheet.getRow(5).getCell(0).getStringCellValue();
		String CC2= ExcelWSheet.getRow(6).getCell(0).getStringCellValue();
		String CC3= ExcelWSheet.getRow(7).getCell(0).getStringCellValue();
		
		System.out.println(to);
		  //Get the session object  
		  Properties props = new Properties();  
		  props.put("mail.smtp.host", "smtp.gmail.com");  
		  props.put("mail.smtp.socketFactory.port", "465");  
		  props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", "465"); 
		  
		  
		  props.put("mail.debug", "false");
		  props.put("mail.smtp.starttls.enable", "true");
		   
		  Session session = Session.getDefaultInstance(props,  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(s_email_id,s_email_password);//change accordingly  
		   }  
		  });  
		  
		  
		
	       // session.setDebug(true);
		  
		  
		  
		  
		   
		  //compose message  
		  try {  
		   MimeMessage message = new MimeMessage(session);  
		   message.setFrom(new InternetAddress(s_email_id));//change accordingly  
		   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		   
//		   message.addRecipients(Message.RecipientType.CC, 
//				   InternetAddress.parse(CC1));
//		   message.addRecipients(Message.RecipientType.CC, 
//				   InternetAddress.parse(CC2));
//		   message.addRecipients(Message.RecipientType.CC, 
//				   InternetAddress.parse(CC3));
		   

		   
		   message.setSubject("Automation Report");  
		   message.setText("Please download report and double click to open"); 
		   
		   		  
		   Multipart multipart = new MimeMultipart();
		   		    
		  	
		   
		   ZipUtil.pack(new File("TestReport"), new File("ZippedReport/Report.zip"));
		   
		 
		   
 MimeBodyPart messageBodyPart1 = new MimeBodyPart();
 DataSource source1 = new FileDataSource("ZippedReport/Report.zip");
 messageBodyPart1.setDataHandler(new DataHandler(source1));
 messageBodyPart1.setFileName(source1.getName());

 
//first part (the html)
BodyPart messageBodyPart = new MimeBodyPart();
String htmlText = "<H1>Please find the attached report.</H1>";
messageBodyPart.setContent(htmlText, "text/html");
//add it
multipart.addBodyPart(messageBodyPart);
 
 
 
 
 
	       multipart.addBodyPart(messageBodyPart1);
	      // multipart.addBodyPart(messageBodyPart2);
	       message.setContent(multipart);
	       
		   //send message  
	       Transport.send(message);
		   //Transport.send(message);  
		  
		   System.out.println("message sent successfully");  
		   
		  } catch (MessagingException e1) {throw new RuntimeException(e1);}  
		   
	}


public static File zip(String files, String filename) {
	
	File f = null;
    String[] paths;

	f= new File(files);
	   paths= f.list();
	System.out.println("IN.................");
    File zipfile = new File(filename);
    // Create a buffer for reading the files
    byte[] buf = new byte[1024];
    try {
    	System.out.println("IN.................");

        // create the ZIP file
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
        // compress the files
        for(int i=0; i<paths.length;i++) {
        	System.out.println(paths[i]);
            FileInputStream in = new FileInputStream(files+"\\"+paths[i]);
            // add ZIP entry to output stream
            out.putNextEntry(new ZipEntry(paths[i]));
            // transfer bytes from the file to the ZIP file
            int len;
            while((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            // complete the entry
            out.closeEntry();
            in.close();
        }
        // complete the ZIP file
        out.close();
        System.out.println(zipfile.getName());
        return zipfile;
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
    }
    return null;
}

}
