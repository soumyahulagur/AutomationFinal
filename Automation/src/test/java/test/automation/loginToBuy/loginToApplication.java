package test.automation.loginToBuy;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.automation.testSourceCode.LoginToOrder;
import test.automation.testBase.TestBase;

public class loginToApplication extends TestBase {
	
public static final Logger log = Logger.getLogger(loginToApplication.class.getName());
	
	LoginToOrder loginpage;
	
	@BeforeClass
	public void setUP(){
		init();
		loginpage = new LoginToOrder(driver);
		 
	}	
		@Test(priority = 0)
		public void VerifyShoppingSite() throws InterruptedException, IOException {
			log.info(" ============Executing TestCase: Verify DataSource Login=================== ");
			loginpage.loginToApplication(getData("userName"), getData("password"));		
			log.info(" ============Finished shopping ensure the order is successful=================== ");
			Assert.assertEquals(loginpage.VerifyOrderStatus(), true);
			log.info(" ===========Successfully Verified the shopping status================== ");
			loginpage.signOut();
			//test commit
		}

}
