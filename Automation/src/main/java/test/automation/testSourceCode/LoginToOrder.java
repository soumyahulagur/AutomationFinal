package test.automation.testSourceCode;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import test.automation.testBase.DataSource;
import test.automation.testBase.TestBase;
import test.automation.waitHelper.WaitHelper;

public class LoginToOrder extends TestBase {
	public static final Logger log = Logger.getLogger(LoginToOrder.class.getName());
	public WebDriver driver;
	public WaitHelper waitHelper;
	
	
	@FindBy(xpath="//a[contains(text(),'Sign in')]")
	WebElement signIn;

	@FindBy(xpath="//input[@id='email']")
	WebElement emailField;
	
	@FindBy(xpath="//input[contains(@id,'passwd')]")
	WebElement passwordField;
	
	@FindBy(xpath="//button[contains(@id,'SubmitLogin')]")
	WebElement logInButton;	
	

	@FindBy(xpath="//*[@id='block_top_menu']/ul/li[3]/a")
	WebElement tshirtSection;
	
	/*@FindBy(xpath="//a[contains(text(),'Faded Short Sleeve T-shirts')]")
	WebElement selectTshirt;*/
/*
	@FindBy(xpath="//iframe[@class='fancybox-iframe']")
	WebElement iFrame;*/
	
	
	/*@FindBy(xpath="//*[@id='add_to_cart']/button/span")
	WebElement addToCart;*/
	
	
	@FindBy(xpath="//div[@class='clearfix']//div[@class='button-container']")
	WebElement proceedToCheckOut;
	
	@FindBy(xpath="//a[@title='Faded Short Sleeve T-shirts']")
	WebElement clickOnProduct;
	
	@FindBy(xpath="//button[@name='Submit']")
	WebElement addToCartFromPage;
	
	
	@FindBy(xpath="//span[contains(text(),'More')]")
	WebElement clickMore;
	
	@FindBy(xpath="//span[text()='Proceed to checkout']")
	WebElement finalCheckOut;
		
	@FindBy(xpath="//input[@id='cgv']")
	WebElement agreeToTerms;
	
	@FindBy(xpath="//a[@class='bankwire']")
	WebElement paymentMode;
	
	@FindBy(xpath="//p[@class='cart_navigation clearfix']//button[@type='submit']")
	WebElement checkOutToPayment;
	
	@FindBy(xpath="//span[text()='I confirm my order']")
	WebElement confirmOrder;
	
	@FindBy(xpath="//strong[text()='Your order on My Store is complete.']")
	WebElement confirmStatus;
	
	@FindBy(xpath="//a[@class='logout']")
	WebElement logout;
	
	
	
	
	
	public LoginToOrder(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitHelper = new WaitHelper(driver);
		waitHelper.waitforElementVisibility(DataSource.getExplicitWait(), signIn);
	}
	
	
	public void loginToApplication(String emailAddress, String password ) throws InterruptedException, IOException{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		signIn.click();
		log.info("Sign in is clicked and login fields are displayed");
		emailField.sendKeys(emailAddress);
		log.info("Entered the UserName " +emailAddress+ " and the object is: "+signIn.toString());
		passwordField.sendKeys(password);
		log.info("Entered the Password " +password+ " and the object is: "+passwordField.toString());
		logInButton.click();
		log.info("Successfully loggedin to shopping site");
		log.info("Start Shopping after login");
		tshirtSection.click();
		log.info("Entering Women section");
		js.executeScript("window.scrollBy(0,450)", "");
		Actions action = new Actions(driver);
		action.moveToElement(clickOnProduct).build().perform();
		clickMore.click();
		log.info("Clicked more options for detail");
		addToCartFromPage.click();
		log.info("Item added to cart");
		waitHelper.waitforElementVisibility(DataSource.getExplicitWait(), proceedToCheckOut);
		proceedToCheckOut.click();
		log.info("first tab checkout");
		js.executeScript("window.scrollBy(0,450)", "");
		waitHelper.waitforElementVisibility(DataSource.getExplicitWait(), finalCheckOut);
		finalCheckOut.click();
		log.info("Summary: tab checkout");
		js.executeScript("window.scrollBy(0,450)", "");
		waitHelper.waitforElementVisibility(DataSource.getExplicitWait(), finalCheckOut);
		finalCheckOut.click();
		log.info("Address: tab checkout");
		agreeToTerms.click();
		checkOutToPayment.click();
		log.info("Final Check out for payment section");
		paymentMode.click();
		confirmOrder.click();
		
	}
	public void signOut() {
		log.info("Clicking on the Signout and the object is: "+logout.toString());
		waitHelper.waitforElementVisibility(DataSource.getExplicitWait(), logout);
		logout.click();		
	}
	public boolean VerifyOrderStatus(){
		log.info("Checking for the presence of dashabord link and the object is: "+confirmStatus.toString());
		waitHelper.waitforElementVisibility(DataSource.getExplicitWait(), confirmStatus);
		return confirmStatus.isDisplayed()? true: false;
		
	}
	 
}
