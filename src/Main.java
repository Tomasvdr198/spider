import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import nu.eve.pathfinder.Browser;
import nu.eve.pathfinder.Crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


/**
 * 
 * @author 		Tomas
 * 
 */


public class Main { 

	public static WebDriver driver = SeleniumUtils.getDriver(SeleniumUtils.DRIVER_TYPE_FIREFOX);
	public static String website = "https://www.humblebundle.com/store/search?sort=discount&filter=onsale&page=4";
	public static WebDriverWait wait = new WebDriverWait(driver,20);
	public static int spiderID = 200;
	public static ArrayList<Map<String, String>> pathfinderData;
	public int i = 1;
	public static boolean toNext = true;
	public static String nextPageSelector = ".js-grid-next";
	private static Connection connect = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	 
	
		
	public static void main(String[] args)
	{
		
		    
		 
		SeleniumUtils.enableLog(true);
		SeleniumUtils.navigate(driver, website, By.cssSelector("entity-block-container js-entity-container"));
		
		callPathFinder();
		

		for(Map<String, String> map:pathfinderData)
		{

			
			//String gameUrl = map.get("href");
			String title 	= map.get("title");
			String image 	= map.get("image");
			String price	= map.get("price");
			String discount	= map.get("discount");
			String platform = map.get("platform");
			String gameUrl	= map.get("href");
			
			if(platform.toLowerCase().contains("steam"))
			{
				platform = "steam";
			}
			else
			{
				platform = "";
			}

			
			//dataBaseSend(title, image, price, discount, platform, gameUrl);
			
			
			
			
			
			// create a Statement from the connection
			//Statement statement = conn.createStatement();

			// insert the data
			//statement.executeUpdate("INSERT INTO Customers " + "VALUES (1001, 'Simpson', 'Mr.', 'Springfield', 2001)");
			
			try
			{
			SeleniumUtils.navigate(driver, gameUrl, By.cssSelector(""));
			Select dropdown = new Select(driver.findElement(By.cssSelector("select.selection:nth-child(3)")));
			dropdown.selectByIndex(1990);
			driver.findElement(By.cssSelector("input.age-check-button.submit-button.js-submit-button")).click();
			}
			catch(NoSuchElementException e)
			{
				SeleniumUtils.navigate(driver, gameUrl, By.cssSelector("span.js-days.digit"));	
			}
			
			String daysAvailable = driver.findElement(By.cssSelector("span.js-days.digit")).getText();
			String hoursAvailable = driver.findElement(By.cssSelector("span.js-hours.digit")).getText();
			String minutsAvailable = driver.findElement(By.cssSelector("span.js-minutes.digit")).getText();
			String secondsAvailable = driver.findElement(By.cssSelector("span.js-seconds.digit")).getText();
			
			String timeAvailable = daysAvailable + ":" + hoursAvailable + ":" + minutsAvailable + ":" +  secondsAvailable;
			
			
			
			System.out.println(timeAvailable);
			System.out.println("title " + title);
			System.out.println("date " + price);
			System.out.println("image " + image);
			System.out.println("discount " + discount);
			System.out.println("platform " + platform);
			System.out.println("url " + gameUrl);
			System.out.println("--------------------------------------------");

			
			
		}

		SeleniumUtils.shutDown(driver);

	}

	public static void callPathFinder()
	{
		
		Map<String, String[]> targets = new HashMap<String, String[]>();
	
		targets.put("title", 	new String[]{"body > div > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > div.list-content.js-list-content.show-status-container > ul > li:nth-child(17) > div > div > a > div > div > span","getText"});
		targets.put("image", 	new String[]{"body > div > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > div.list-content.js-list-content.show-status-container > ul > li:nth-child(9) > div > div > a > div > img","src"});
		targets.put("discount",	new String[]{"body > div > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > div.list-content.js-list-content.show-status-container > ul > li:nth-child(1) > div > div > div > div.entity-pricing.js-price-container > div > span","getText"});
		targets.put("href", 	new String[]{"a.entity-link.js-entity-link","href"});
		targets.put("price",	new String[]{"body > div > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > div.list-content.js-list-content.show-status-container > ul > li:nth-child(1) > div > div > div > div.entity-pricing.js-price-container > div > div > span.price", "getText"});
		targets.put("platform",	new String[]{"body > div > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > div.list-content.js-list-content.show-status-container > ul > li:nth-child(1) > div > div > div > div.entity-devices.js-platform-delivery-container > div > ul.platforms.no-style-list", "innerHTML"});
		
		Crawler.setWaitTime(1500);
		//Crawler.setNextPageSelector("a.js-grid-next.grid-next.grid-page-nav.hb.hb-angle-right");
		
		Crawler.setNextPageSelector(nextPageSelector);
		Crawler.setEventSelector("body > div > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > div.list-content.js-list-content.show-status-container > ul > li:nth-child(2)");
		Crawler.setUrlCheck(true);
		
		pathfinderData = Crawler.start(driver, targets);
		
		
	}
	
	  
	
	 static void dataBaseSend(String title, String image, String price,  String discount, String platform, String gameUrl ){  
		try{  
			
			 // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/humblebundlespider?"
                            + "user=root&password=");
            
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from free_games");
            
			preparedStatement = connect
			        .prepareStatement("insert into  free_games values (?, ?, ? , ?, ?, ?, default)");
			System.out.println("title is " + title);
			 preparedStatement.setString(1, title);
	         preparedStatement.setString(2, image);
	         preparedStatement.setString(3, price);
	         preparedStatement.setString(4, discount);
	         preparedStatement.setString(5, platform);
	         preparedStatement.setString(6, gameUrl);
	         preparedStatement.executeUpdate();
	         
	         preparedStatement = connect
	                    .prepareStatement("SELECT game_title, game_image, game_price, game_discount, game_platform, game_url COMMENTS from free_games");
	            resultSet = preparedStatement.executeQuery();
	            
	            System.out.println("games in database gezet");
		}catch(Exception e){ System.out.println(e);}  
		
		
		
	}  


}


