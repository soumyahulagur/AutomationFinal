package test.automation.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class DataSource {
	private static Logger log = Logger.getLogger(DataSource.class.getName());
	public static Properties OR;
	
	private String browerType;
	private String userName;
	private String password;
	private String url;
	long implicitWait;
	long explicitWait;
	long pageLoadTime;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBrowserType() {
		return browerType;
	}

	public void setBrowserType(String browerType) {
		this.browerType = browerType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public static long getImplicitWait() {
		
		return Long.parseLong(OR.getProperty("implicitlyWait"));
	}

	public static long getExplicitWait() {
		return Integer.parseInt(OR.getProperty("explicitWait"));
	}

	public static long getPageLoadTime() {
		return Integer.parseInt(OR.getProperty("pageLoadTime"));
	}


	static {
		log.info("loading config.properties..");
		OR = new Properties();
		File f1 = new File  (System.getProperty("user.dir")+"/src/main/resources/config.properties");
		try {
			FileInputStream file = new FileInputStream(f1);
			OR.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("loading config.properties done");
	}


}
