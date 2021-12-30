package com.dell.webservice.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;

public class AddProductTest {
	
	String driverPath = "C:\\Users\\akashsinha\\eclipse-projects\\java-fsd-capstone-foodbox\\java-fsd-capstone-foodbox\\chromedriver\\chromedriver.exe";
	ChromeDriver driver;
	String baseUrl = "http://localhost:3000/";
	
	@Test
	void testProductUrl() {
		String siteUrl = baseUrl+"products";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(siteUrl);
		assertEquals(siteUrl, driver.getCurrentUrl());
	}
	
	
	@Test
	void testProductAdd_success() {
		String siteUrl = baseUrl+"signin";
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(siteUrl);
		WebElement username = driver.findElementByXPath("/html/body/app-root/app-sign/div/form/div[1]/div/div/input");
		username.sendKeys("Alisha");
		WebElement password = driver.findElementByXPath("/html/body/app-root/app-sign/div/form/div[2]/div/div/input");
		password.sendKeys("pqr");
		ISelect role = new Select(driver.findElementByXPath("/html/body/app-root/app-sign/div/form/div[3]/div/div/select"));
		role.selectByVisibleText("As Admin");
		driver.findElementByXPath("/html/body/app-root/app-sign/div/form/button").click();
	
		String siteUrlNew = baseUrl+"products";
		driver.get(siteUrlNew);
		WebElement addProduct = driver.findElementByXPath("/html/body/app-root/app-products/div[3]/form/a");
		addProduct.click();
		WebElement name = driver.findElementByXPath("/html/body/app-root/app-products/app-createproductdata/div/div/div/form/div[1]/div[1]/div/div/input");
		name.sendKeys("Chilly Chicken");
		WebElement price = driver.findElementByXPath("/html/body/app-root/app-products/app-createproductdata/div/div/div/form/div[1]/div[2]/div/div/input");
		price.sendKeys("200");
		WebElement description = driver.findElementByXPath("/html/body/app-root/app-products/app-createproductdata/div/div/div/form/div[1]/div[3]/div/div/input");
		description.sendKeys("200");
		ISelect category = new Select(driver.findElementByXPath("/html/body/app-root/app-products/app-createproductdata/div/div/div/form/div[1]/div[4]/div/div/select"));
		category.selectByVisibleText("Chinese");
		ISelect foodimage = new Select(driver.findElementByXPath("/html/body/app-root/app-products/app-createproductdata/div/div/div/form/div[1]/div[5]/div/div/select"));
		foodimage.selectByVisibleText("Chilly Chicken");
		ISelect categoryimage = new Select(driver.findElementByXPath("/html/body/app-root/app-products/app-createproductdata/div/div/div/form/div[1]/div[6]/div/div/select"));
		categoryimage.selectByVisibleText("Chinese");
		WebElement seller = driver.findElementByXPath("/html/body/app-root/app-products/app-createproductdata/div/div/div/form/div[1]/div[7]/div/div/input");
		seller.sendKeys("Golden Spoon");
		driver.findElementByXPath("/html/body/app-root/app-products/app-createproductdata/div/div/div/form/div[2]/button[1]").click();
		assertEquals(siteUrlNew,driver.getCurrentUrl());
	}

}
