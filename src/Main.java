import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	public static String website = "https://www.humblebundle.com/store/search?sort=discount&filter=onsale&page=149";
	public static WebDriverWait wait = new WebDriverWait(driver,20);
	public static int spiderID = 200;
	public static ArrayList<Map<String, String>> pathfinderData;
	public static List<Event> events = new ArrayList<Event>();
	public int i = 1;
		
	public static void main(String[] args)
	{
		//		SeleniumUtils.enableLog(true);
		SeleniumUtils.navigate(driver, website, By.cssSelector("entity-block-container js-entity-container"));

		callPathFinder();


		for(Map<String, String> map:pathfinderData)
		{
			//String gameUrl = map.get("href");
			String title 	= map.get("title");
			String image 	= map.get("image");
			String price	= map.get("price");
			String tag 		= map.get("discount");

			//System.out.println("eventUrl " + gameUrl);
			System.out.println("title " + title);
			System.out.println("date " + price);
			System.out.println("image " + image);
			System.out.println("--------------------------------------------");

			//SeleniumUtils.navigate(driver, gameUrl, By.cssSelector("body"));
			
			
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
		targets.put("discount",	new String[]{"discount-percentage","getText"});
		//targets.put("href", 	new String[]{"entity-title","href"});
		targets.put("price",	new String[]{"body > div > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > div.list-content.js-list-content.show-status-container > ul > li:nth-child(1) > div > div > div > div.entity-pricing.js-price-container > div > div > span.price", "getText"});

		Crawler.setWaitTime(3000);
		//Crawler.setNextPageSelector("a.js-grid-next.grid-next.grid-page-nav.hb.hb-angle-right");
		Crawler.setEventSelector("body > div > div.page-wrap > div.base-main-wrapper > div.inner-main-wrapper > section > div.main-content > div.full-width-container.js-page-content > div > div > div.js-search-results-holder.search-results-holder.entity-list > div > div.chunks-container > div.list-content.js-list-content.show-status-container > ul > li:nth-child(2)");
		
		pathfinderData = Crawler.start(driver, targets);

	}

}


