package com.dista.test.automation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestLaunchApplication {

	public static WebDriver driver = null;
	public static Logger Add_Log = null;


	public void launch(String browser, String URL, String username, String password)
			throws IOException, InterruptedException {

		System.out.println("DRIVER 4:: "+ driver);
		
		if (browser.equalsIgnoreCase("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-popup-blocking");
			//options.addArguments("--headless");
			options.addArguments("--start-maximized");
			options.addArguments("chrome.switches", "--disable-extensions");
			 driver = new ChromeDriver(options);
		} 


		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(URL);
		Thread.sleep(5000);
		driver.findElement(By.name("userid")).sendKeys(username);
		Thread.sleep(3000);
		driver.findElement(By.name("password")).sendKeys(password);
		
		driver.findElement(By.xpath("//button[@ng-click='doLogin()']")).click();
		
		System.out.println("DRIVER 3:: "+ driver);
	

	}

	

	@BeforeSuite(alwaysRun = true)
	public void Login() throws IOException, InterruptedException {
		System.out.println("Executing Login");
		Add_Log = Logger.getLogger("rootLogger");				

		FileInputStream ExcelFile = new FileInputStream("Support/AutomationData.xlsx"); // files stored at specified
																							// path contains URLs
		XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
		XSSFSheet ExcelWSheet = ExcelWBook.getSheet("Login Credentials");

		String browser = ExcelWSheet.getRow(2).getCell(1).getStringCellValue();
		String URL = ExcelWSheet.getRow(3).getCell(1).getStringCellValue();
		String username = ExcelWSheet.getRow(5).getCell(1).getStringCellValue();
		String password = ExcelWSheet.getRow(6).getCell(1).getStringCellValue();
		Add_Log.info("Data file loaded successfully.");
		System.out.println("browsers " + browser + URL + username + password);
		launch(browser, URL, username, password);
		//return driver;
	}


	@AfterSuite
	public void logout() throws IOException, InterruptedException {

		
		driver.findElement(By.xpath("//*[@id='Forma_1']")).click();
		driver.findElement(By.xpath("span[contains(text(), 'Log Out') ]")).click();
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				// Driver maybe already closed. Ignore.
			}

		}

	}



	public WebDriver getDriver() {
		// TODO Auto-generated method stub
		return driver;
	}
}
