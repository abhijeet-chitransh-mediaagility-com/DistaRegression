package com.dista.utilities;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseActions {
	
//	public void MouseHover(WebElement Element)
//	{
//		Actions hover = new Actions(TestLaunchApplication.driver);
//		hover.moveToElement(Element);
//	}

	
	public void HoverAndClick(WebDriver driver, WebElement elementToHover, WebElement elementToClick )
	{
		Actions action =  new Actions(driver);
		action.moveToElement(elementToHover).click(elementToClick).build().perform();
	}
	
}
