package test.automation.waitHelper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WaitHelper {
public static final Logger log = Logger.getLogger(WaitHelper.class.getName());
	
	public static WebDriver driver;

	public WaitHelper(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	/**
	 * This method will wait for element
	 * @param l
	 * @param element
	 */
	public void waitforElementVisibility(long l, WebElement element){
		log.info("Waiting for the element visibility ");
		System.out.println("Driver for waitforElementVisibility:: "+ driver);
		WebDriverWait wait = new WebDriverWait(driver, l);
		wait.until(ExpectedConditions.visibilityOf(element));
		log.info("Element is present ");
	}

	public void waitforElementThenClick(WebDriver driver, WebElement element) throws InterruptedException {
        try 
        {
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } 
        catch (Exception e) {
        }
    }
	
	public void waitforMoveToElementThenClick(WebDriver driver, WebElement element) throws InterruptedException {
        try 
        {
        	Actions myaction = new Actions(driver);
        	myaction.moveToElement(element);
        	WebDriverWait wait = new WebDriverWait(driver, 20);
        	wait.until(ExpectedConditions.elementToBeClickable(element));
        	myaction.click().perform();
        } 
        catch (Exception e) {
        }
    }

	
	public void staleConnection(WebDriver driver, WebElement element) {
		int Counter=0;
		do
		{
			try
		{

			if(element.isEnabled() && element.isDisplayed() )
			{
				Counter=Counter+1;
				WebDriverWait wait = new WebDriverWait(driver, 60);
	        	wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
				break;
			}
		}
		catch(StaleElementReferenceException e)
		{
			WebDriverWait wait = new WebDriverWait(driver, 60);
        	wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		}
		}
		while(Counter == 0);
	}
	
	public Boolean RetryingFindClick_old(WebElement webElement)
    {
        Boolean result = false;
        int attempts = 0;
        while (attempts < 5)
        {
            try
            {
                webElement.click();
                result = true;
                break;
            }
            catch (StaleElementReferenceException e)
            {   	
            	e.printStackTrace();
            	driver.navigate().refresh();
            	//RetryingFindClick(webElement);
            	
            }
            attempts++;
        }
        return result;
    }

	
	public void RetryingFindClick(WebElement webElement) throws IOException,InterruptedException
    {
        Boolean result = false;
        int attempts = 0;
        WebElement WebEle = null;
        while (attempts < 5)
        {
            try
            {
                if(webElement.isEnabled() && webElement.isDisplayed())
                {
	                WebEle = refreshWebElement(driver,webElement);
	            	WebEle.click();
	            	log.info("First time click of retryClick");
	                //result = true;
	                break;
                }
                else
                {
                	RetryingFindClick(webElement);
                }
                
            }
            catch (StaleElementReferenceException e)
            {   	
            	e.printStackTrace();
            	WebEle = refreshWebElement(driver,webElement);
            	WebEle.click();
            	
            }
            attempts++;
        }
        
    }
	
	
	 public WebElement refreshWebElement(WebDriver webDriver, WebElement webEl) throws IOException,InterruptedException {
	        String elementInfo = webEl.toString();
	        elementInfo = elementInfo.substring(elementInfo.indexOf("->"));
	        String elementLocator = elementInfo.substring(elementInfo.indexOf(": "));
	        elementLocator = elementLocator.substring(2, elementLocator.length() - 1);
	        System.out.println(elementInfo);

	        WebElement retWebEl = null;
	        if (elementInfo.contains("-> link text:")) {
	            retWebEl = webDriver.findElement(By.linkText(elementLocator));
	        } else if (elementInfo.contains("-> name:")) {
	            retWebEl = webDriver.findElement(By.name(elementLocator));
	        } else if (elementInfo.contains("-> id:")) {
	            retWebEl = webDriver.findElement(By.id(elementLocator));
	        } else if (elementInfo.contains("-> xpath:")) {
	            retWebEl = webDriver.findElement(By.xpath(elementLocator));
	        } else if (elementInfo.contains("-> class name:")) {
	            retWebEl = webDriver.findElement(By.className(elementLocator));
	        } else if (elementInfo.contains("-> css selector:")) {
	            retWebEl = webDriver.findElement(By.cssSelector(elementLocator));
	        } else if (elementInfo.contains("-> partial link text:")) {
	            retWebEl = webDriver.findElement(By.partialLinkText(elementLocator));
	        } else if (elementInfo.contains("-> tag name:")) {
	            retWebEl = webDriver.findElement(By.tagName(elementLocator));
	        } else {
	            System.out.println("No valid locator found. Couldn't refresh element");
	        }
	        return retWebEl;
	    }

}
