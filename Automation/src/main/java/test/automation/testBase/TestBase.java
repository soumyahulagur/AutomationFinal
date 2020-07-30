package test.automation.testBase;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.IClass;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import test.automation.testBase.DataSource;
import test.automation.testBase.TestBase;

public class TestBase {
	public static final Logger log = Logger.getLogger(TestBase.class.getName());
	public WebDriver driver;
	//String browser = "firefox";
	//String browser = "chrome";
	public static ExtentReports extent;
	public static ExtentTest test;
	//public static TestBase loginpage;
	String fileName1 = null;
	String filepath = null;
	String OS = System.getProperty("os.name").toLowerCase();


		
	static {
		Calendar calender = Calendar.getInstance();
		System.out.println("Get time returns"+calender.getTimeInMillis());
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String a = formater.format(calender.getTime());
		extent  = new ExtentReports(System.getProperty("user.dir")+"/src/main/java/com/test/automation/sdm/report/test"+".html",true);	
	}
	public void init(){
	     if(isUnix())
		    {
			 	String URL= System.getProperty("serverURL");
				driver.get(URL);
				System.out.println("Inside Unix");
				System.out.println(URL);
		    }
		    else
		    {
		    	driver.get(getData("url"));

		    }
		String title = driver.getTitle();
		System.out.println("title is"+title );
		driver.manage().window().maximize();
		System.out.println("Inside init method");
		String log4jConfpath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfpath); 			
	}

	public WebDriver selectBrowser(String browser){
		if(browser.equalsIgnoreCase("firefox")){
		    System.out.println("Browser object is" + browser);
		    String pathToFirefoxDriver = "/src/main/resources/geckodriver.exe";
		    if(isUnix())
		    {
		        pathToFirefoxDriver = "/usr/bin/geckodriver";
		    }
		    else
		    {

			    pathToFirefoxDriver = System.getProperty("user.dir")+pathToFirefoxDriver;
			    
		    }

			System.setProperty("webdriver.chrome.driver",pathToFirefoxDriver);
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("Marionette", true);
			System.out.println("Selecting firefox browser");
			log.info("Creating object of"+" " + browser );
			driver = new FirefoxDriver();
			System.out.println("Driver object is" + driver);
		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
		    String pathToChromeDriver = "/src/main/resources/chromedriver.exe";
		    if(isUnix())
		    {
		        pathToChromeDriver = "/usr/bin/chromedriver";
		    }
		    else
		    {
			    pathToChromeDriver = System.getProperty("user.dir")+"/src/main/resources/chromedriver.exe";
		    }
		    System.setProperty("webdriver.chrome.driver",pathToChromeDriver);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("Marionette", true);
			ChromeOptions options = new ChromeOptions();	
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--start-maximized");
			//options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-proxy-server");
            options.addArguments("--proxy-server='direct://'");
            options.addArguments("--proxy-bypass-list=*");
            options.addArguments("--disable-dev-shm-usage");     
			System.out.println("Creating object of "+ browser);
			log.info("Creating object of" + browser );
			//options.merge(capabilities);
			driver = new ChromeDriver(options);
			
			System.out.println("Driver object is" + driver);
		}
		return null;
		
		}
		
	public boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		
	}
	
	public void waitforElementVisibility(int timeOutInSeconds, WebElement element){
		System.out.println("Driver for waitforElementVisibility::"+ driver);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	
	public void waitforElementClickable(int timeOutInSeconds, By locator){
		System.out.println("Inside before the method");
		System.out.println("driver is "+driver);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		WebElement ele  = wait.until(ExpectedConditions.elementToBeClickable(locator));
		System.out.println("Inside the method");
		ele.click();
		
	}
	
	public void waitforPageLoad(long timeOutInSeconds,String title){
		System.out.println("Driver for waitforpage::"+ driver);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.titleIs(title));
		
		//wait.until(ExpectedConditions.titleContains(title));
	}
	
	
	public void getScreenShot(String name) throws IOException, InterruptedException{
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		Thread.sleep(5000);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath()+"/src/main/java/com/test/automation/sdm/screenshot/";
			File destFile = new File((String)reportDirectory+ name +"_"+formater.format(calender.getTime())+".png");
			FileUtils.copyFile(scrFile, destFile);
			Reporter.log("<a href='"+destFile.getAbsolutePath()+"'> <img src='"+destFile.getAbsolutePath()+"'height='100' width='100'/></a>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void quitBrowser(){
		driver.quit();
		log.info("Browser quit");
		extent.endTest(test);
		extent.flush();
	}
	
	public void closeBrowser(){
		driver.close();
		log.info("Browser close");
	}
	
	
	public Set<String> winHandles;
	public Iterator<String> itrWinHandles;
	public String parentWindow;
	public String childWindow;
	public Set<String>  getWindowHandles(){
		winHandles = driver.getWindowHandles();
	/*	Iterator<String> itrWinHandles = winHandles.iterator();
		for(String winhandle:winHandles)
			System.out.println("Window handles is"+winhandle);
		parentWindow = itrWinHandles.next();
		childWindow = itrWinHandles.next();*/
		return winHandles;
	}
	
	public Iterator<String> itrWinHandles(){
		itrWinHandles = winHandles.iterator();
		for(String winhandle:winHandles)
			System.out.println("Window handles is"+winhandle);
		return itrWinHandles;
		
	}
	
	public WebDriver parentWinHandle(){
		parentWindow = itrWinHandles.next();
		System.out.println("Parent window is"+parentWindow);
		return driver.switchTo().window(parentWindow);
		
		
				
	}
	
	public WebDriver childWinHandle(){
		childWindow = itrWinHandles.next();
		System.out.println("child  window is"+childWindow);
		return driver.switchTo().window(childWindow);
		
		
				
	}
	public String sendKeyCheck(WebElement element,String projectname) {
		log.info("Project Name added=="+element.getAttribute("value"));
		return element.getAttribute("value");
		
	}
	
	
	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/test/automation/sdm/report/";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.getName();
	}
		
	/**
	 * This method will help us to get the data from the properties file
	 * @param name
	 * @return
	 */		
	public String getData(String name) {
		return DataSource.OR.getProperty(name);		
	}
	
	@BeforeTest
	public void LaunchBrowser() throws InterruptedException {
		System.out.println("Waiting for before test to launch");
		selectBrowser(getData("browserType"));
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Before test is launched");		
	}
	
	@BeforeMethod()
	public void beforeMethod(Method result) {
		System.out.println("In Before method");
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName()+"	Test started");
		
		
	}

	
	public void failureCapture() throws NoSuchMethodException, SecurityException, MalformedURLException, IOException {
	    try{
	    	String screen = captureScreen("");
			test.log(LogStatus.FAIL,test.addScreenCapture(screen));
			driver.navigate().refresh();
	    }
    	catch (Exception e) {
    		// TODO: handle exception
    		e.printStackTrace();
	}
	    
	}
		
	
	@AfterClass(alwaysRun=true)
	public void endTest() {
		System.out.println("Executed After class");
		quitBrowser();
	}

}
