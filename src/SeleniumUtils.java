import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opera.core.systems.OperaDriver;

public class SeleniumUtils {

	protected static boolean printLogs 			= false;
	protected static boolean printStackTraces 	= false;

	protected static final String DRIVER_TYPE_CHROME 	= "Chrome";
	protected static final String DRIVER_TYPE_FIREFOX 	= "Firefox";
	protected static final String DRIVER_TYPE_SAFARI 	= "Safari";
	protected static final String DRIVER_TYPE_OPERA 	= "Opera";
	protected static final String DRIVER_TYPE_PHANTOM 	= "PhantomJS";

	public static final String ATTRIBUTE_TEXT_CONTENT 	= "textContent";
	public static final String ATTRIBUTE_HREF 			= "href";
	public static final String ATTRIBUTE_SRC 			= "src";
	public static final String ATTRIBUTE_INNER_HTML 	= "innerHTML";
	public static final String ATTRIBUTE_GET_TEXT 		= "gettext";

	/**<pre>
	 * Zoekt een WebElement door middel van de gegeven selector op en stuurt het attribuut daarvan als String terug. 
	 * Deze methode throwt geen errors. Wanneer een WebElement niet gevonden kan worden, 
	 * wordt er een lege string teruggestuurd.
	 * </pre>
	 * @return Attribuut als String
	 */
	public static String get(WebDriver driver, By by, String attribute)
	{
		String result;
		
		try
		{
			if(attribute.equals(ATTRIBUTE_GET_TEXT))
			{
				result = driver.findElement(by).getText();
				log("[get]Found path '" + by + "'. Returning value '" + result + "'");
				return result;
			}
			else
			{
				result = driver.findElement(by).getAttribute(attribute);
				log("[get]Found path '" + by + "'. Returning value '" + result + "'");
				return result;
			}
		}
		catch(Exception e)
		{
			log("Could not find using path '" + by + "'. Error message = " + e.getMessage() + "\n");
			printStackTrace(e);
			return "";
		}
	}

	/**
	 * <pre>
	 * Check of een WebElement bestaat op de huidige pagina. 
	 * Dat resultaat wordt als boolean teruggestuurd.
	 * </pre>
	 * @param driver
	 * @param by
	 * @return False wanneer een element niet gevonden kan worden, true als het wel gevonden kan worden.
	 */
	public static boolean doesElementExist(WebDriver driver, By by)
	{
		try
		{
			driver.findElement(by);
			log("[doesElementExist]Found element using BY:'" + by.toString() + "'. Returning true.");
			return true;
		}
		catch(Exception e)
		{
			log("[doesElementExist]Could not find element using BY:'" + by.toString() + "'. Returning false.");
			printStackTrace(e);
			return false;
		}
	}

	/**
	 * <pre>
	 * Deze methode maakt een WebDriver object aan op basis van het gegeven type, en stuurt dat vervolgens terug.
	 * Geldige types zijn: 'Chrome', 'Firefox', 'Safari', 'Opera' en 'PhantomJS'.
	 * </pre>
	 * @param driverType
	 * @return WebDriver
	 */
	public static WebDriver getDriver(String driverType)
	{
		log("[getDriver]Creating new" + driverType + " webdriver");

		switch(driverType)
		{
		case DRIVER_TYPE_CHROME:
			return new ChromeDriver();

		case DRIVER_TYPE_FIREFOX:
			return new FirefoxDriver();

		case DRIVER_TYPE_SAFARI:
			return new SafariDriver();

		case DRIVER_TYPE_PHANTOM:
			return new PhantomJSDriver();

		case DRIVER_TYPE_OPERA:
			return new OperaDriver();

		default:
			throw new IllegalArgumentException("[getDriver]WebDriver '" + driverType + "' is not a supported driver type.");
		}
	}

	/**
	 * <pre>
	 * Deze methode kan je een lijst met selectors meegeven. 
	 * Deze worden ��n voor ��n gecheckt, en er wordt geprobeerd om daarvan het attribuut op te halen.
	 * Bij de eerste succesvolle poging wordt het resultaat teruggestuurd. Mocht het zo zijn dat
	 * Er geen enkele selector werkt, dan wordt er een lege String teruggestuurd.
	 * </pre>
	 * @param driver
	 * @param paths
	 * @param attribute
	 * @return String
	 */
	public static String bruteForce(WebDriver driver, List<String> paths, String attribute)
	{
		for(String path:paths)
		{
			try
			{
				log("[bruteForce]Checking on path '" + path + "'");

				if(attribute.equals(ATTRIBUTE_GET_TEXT))
				{
					return driver.findElement(By.cssSelector(path)).getText();
				}
				else
				{
					return driver.findElement(By.cssSelector(path)).getAttribute(attribute);
				}
			}
			catch(Exception e)
			{
				log("[bruteForce]Element not found.");
				printStackTrace(e);
			}
		}
		log("[bruteForce]Could not find any of the given paths. Returning empty string..");
		return "";
	}

	/**
	 * <pre>
	 * Deze methode kan je een lijst met selectors meegeven. 
	 * Elk attribuut wat succesvol kan worden opgehaald, wordt opgeslagen in een ArrayList(String)
	 * Deze lijst wordt vervolgens teruggestuurd. 
	 * Als er geen enkele attribuut kan worden gevonden, wordt er een lege ArrayList teruggestuurd.
	 * </pre>
	 * @param driver
	 * @param paths
	 * @param attribute
	 * @return List(String)
	 */
	public static List<String> bruteForce(WebDriver driver, ArrayList<String> paths, String attribute)
	{
		List<String> results = new ArrayList<String>();

		for(String path:paths)
		{
			try
			{
				log("[bruteForce(LIST)]Checking on path '" + path + "'");

				if(attribute.equals(ATTRIBUTE_GET_TEXT))
				{
					results.add(driver.findElement(By.cssSelector(path)).getText());
				}
				else
				{
					results.add(driver.findElement(By.cssSelector(path)).getAttribute(attribute));
				}
			}
			catch(Exception e)
			{
				log("[bruteForce(LIST)]Element not found.");
				printStackTrace(e);
			}
		}

		return results;
	}

	/**
	 * <pre>
	 * Haalt WebElements op door middel van de gegeven selector
	 * en stuurt die terug in een List(WebElement)
	 * @param driver
	 * @param by
	 * @return List(WebElement)
	 */
	public static List<WebElement> getElements(WebDriver driver, By by)
	{
		try
		{
			List<WebElement> list = driver.findElements(by);
			log("[getElements]" + list.size() + "elements found using selector:" + by.toString());
			return list;
		}
		catch(Exception e)
		{
			log("[getElements]No elements found using selector:" + by.toString());
			printStackTrace(e);
			return new ArrayList<WebElement>();
		}
	}

	/**
	 * <pre>
	 * Zet dit op true als je de debug modus aan wilt hebben. Dit zal alle logs van
	 * deze klasse (SeleniumUtils) in je console weergeven.
	 * </pre>
	 * @param boolean
	 */
	public static void enableLog(boolean b)
	{
		printLogs = b;
	}
	
	/**
	 * <pre>
	 * Zet dit op true als je de debug modus aan wilt hebben. Dit zal alle stacktraces van
	 * deze klasse (SeleniumUtils) in je console weergeven.
	 * </pre>
	 * @param b
	 */
	public static void enableStackTraces(boolean b)
	{
		printStackTraces = b;
	}
	
	/**
	 * Navigeer naar een pagina en wacht op een bepaald element.
	 * @param driver
	 * @param url
	 * @param waitElement
	 */
	public static void navigate(WebDriver driver, String url, By waitElement)
	{
		driver.get(url);
		wait(driver, 10, waitElement);
	}
	
	/**
	 * Wacht tot een bepaald element klikbaar is op de pagina.
	 * @param driver
	 * @param seconds
	 * @param element
	 */
	public static void wait(WebDriver driver, int seconds, By element)
	{
		WebDriverWait wait = new WebDriverWait(driver, seconds);

		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		catch(TimeoutException e)
		{
			log("Timed out after "+ seconds + " seconds. Element = "+ element);
			printStackTrace(e);
		}
	}
	
	public static void shutDown(WebDriver driver)
	{
		System.out.println("Shutting down browser.");
		driver.quit();
		System.out.println("Shutting down spider");
		System.exit(0);
	}

	public static void log(Object o)
	{
		if(printLogs)
		{
			System.out.println(o.toString());
		}
	}
	
	public static void printStackTrace(Throwable e)
	{
		if(printStackTraces)
		{
			e.printStackTrace();
		}
	}

}
