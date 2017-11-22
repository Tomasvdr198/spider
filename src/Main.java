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
import nu.eve.simpleEvent.*;

/**
 * 
 * @author 		Rachid
 * @spider		1337
 * @version		1.0
 * @created		1970-01-01 00:00
 * @last_edited	2016-09-05 12:26
 * @comments	
 *
 */

public class Main { 

	public static WebDriver driver = SeleniumUtils.getDriver(SeleniumUtils.DRIVER_TYPE_FIREFOX);
	public static String website = "https://www.humblebundle.com/store/search?sort=discount&filter=onsale&page=156";
	public static WebDriverWait wait = new WebDriverWait(driver,20);
	public static int spiderID = 200;
	public static ArrayList<Map<String, String>> pathfinderData;
	public static List<Event> events = new ArrayList<Event>();
	public int i = 1;
	public static boolean toNext = true;
	public static String nextPageSelector = ".js-grid-next";

		
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

			//System.out.println("eventUrl " + gameUrl);
			
			
			
			try
			{
			SeleniumUtils.navigate(driver, gameUrl, By.cssSelector("select.selection.js-selection.js-selection-year"));
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

			//Event event = new Event();
			//event.setEventUrl(gameUrl);
			//event.setTitle(title);
			//event.setStartTime(price);


			//EventTicket ticket = new EventTicket();
			


//			Venue venue = new Venue();
//			venue.setId(1053);
//
//			//EventType type = new EventType(tag, 5);
//
//			event.setVenue(venue);
//			//event.setType(type);
//			event.setTicket(ticket);
//
//
//
//			event.sendEvent();
			
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

}


