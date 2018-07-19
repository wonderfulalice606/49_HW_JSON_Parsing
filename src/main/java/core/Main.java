package core;

import java.io.*;
import java.math.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

public class Main {
	public static void main(String[] args) throws InterruptedException, IOException, ParseException, NumberFormatException {
    	
    	String us_currency_symbol = "$";
    	
//    	String ip_Euro = "88.191.179.56";
//    	String ip_Yuan = "61.135.248.220";
//    	String ip_Pound = "92.40.254.196";
//    	String ip_Hryvnia = "93.183.203.67";
//    	String ip_Ruble = "213.87.141.36";
//    	String ip_Yen = "210.138.184.59"; 
//    	String ip_Tugrik = "66.181.168.10";  
//    	String ip_Rupee = "14.139.255.255"; 
//    	String ip_Baht = "1.46.0.0";
//    	String ip_Shekel = "2.55.255.255"; 
    	   	
    	Logger logger = Logger.getLogger(""); logger.setLevel(Level.OFF);
    	String url = "https://www.ebay.com/itm/DJI-Mavic-Air-Arctic-White-Drone-4K-Camera-32MP-Sphere-Panoramas/302652532382?hash=item46777f269e%3Ag%3AY7AAAOSwb85alHD0&_sacat=0&_nkw=drone&_from=R40&rt=nc&_trksid=p2380057.m570.l1313.TR12.TRC2.A0.H0.Xdrone.TRS0";
    	
    	WebDriver driver;
    	System.setProperty("webdriver.chrome.driver", "./resources/webdrivers/mac/chromedriver");
    	System.setProperty("webdriver.chrome.silentOutput", "true");
    	ChromeOptions option = new ChromeOptions();
    	option.addArguments("-start-fullscreen");
    	driver = new ChromeDriver(option);
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	driver.get(url);
    	
    	//DJI Mavic Air - Arctic White Drone - 4K Camera, 32MP Sphere Panoramas!
    	String product_title = driver.findElement(By.id("itemTitle")).getText();
    	
    	double original_price = Double.parseDouble(
    			driver.findElement(By.id("prcIsum")).getText().replaceAll("[A-Z]\\w*\\s\\$", " "));
      	
    	// 799.00
    	driver.quit();
    	
    	String [] ips = new String [] {
    			"88.191.179.56", "61.135.248.220", "92.40.254.196", "93.183.203.67", "213.87.141.36",
    			"210.138.184.59", "66.181.168.10", "14.139.255.255", "1.46.0.0", "2.55.255.255"
    			
    	};
    	
    	for (String ip :ips) {
    		
    		URL api_url = new URL("http://www.geoplugin.net/json.gp?ip=" + ip);   	
    	  	  	
    	JSONParser jp = new JSONParser();
		
		JSONObject cc_json = (JSONObject) jp
				.parse(new BufferedReader(new InputStreamReader(api_url.openConnection().getInputStream())));

		String usa_code = "USD";
		String local_code = (String) cc_json.get("geoplugin_currencyCode"); // EUR
		String pair_code = usa_code + "_" + local_code;
		
		URL rate_url = new URL("http://free.currencyconverterapi.com/api/v6/convert?q=" + pair_code + "&compact=y");

		JSONObject rate_json = (JSONObject) jp
				.parse(new BufferedReader(new InputStreamReader(rate_url.openConnection().getInputStream())));

		JSONObject root = (JSONObject) rate_json.get(pair_code);
		Number rate = (Number) root.get("val");
    	
    	double local_price = new BigDecimal(original_price * rate.doubleValue()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    	
    	System.out.println("Item: " + product_title + "; "
    			+ "US Price: " + us_currency_symbol + original_price + "; "
    			+ "for country: " + cc_json.get("geoplugin_countryName") + "; "
    			+ "Local Price: " + cc_json.get("geoplugin_currencySymbol_UTF8") + local_price); 
    	}
     	
    }
}
