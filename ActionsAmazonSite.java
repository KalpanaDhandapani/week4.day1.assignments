package com.week4.day1.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionsAmazonSite {

	public static void main(String[] args) throws IOException, InterruptedException {
		ChromeDriver driver = new ChromeDriver();

		// 1. Load the URL (https://www.amazon.in/)
		driver.get("https://www.amazon.in");

		// maximize the window
		driver.manage().window().maximize();

		// Add an implicit wait to ensure the web page elements are fully loaded
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// 2. Search for "oneplus 9 pro".
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("oneplus 9 pro", Keys.ENTER);

		// 3. Get the price of the first product.
		String text = driver.findElement(By.xpath("(//span[@class='a-price-whole'])[3]")).getText();
		System.out.println("first product price = " + text);

		Thread.sleep(3000);
		// 4. Print the number of customer ratings for the first displayed product.
		By locator = By.xpath("//i[contains(@class,'a-icon a-icon-star-small')][1]");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		locator.findElement(driver).click();
		Thread.sleep(2000);

		By locator1 = By.xpath("//span[@data-hook='acr-average-stars-rating-text']");
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait1.until(ExpectedConditions.visibilityOfElementLocated(locator1));
		String text2 = locator1.findElement(driver).getText();

		System.out.println("first product customer ratings = " + text2);

		driver.findElement(By.xpath("//i[@class='a-icon a-icon-close']")).click();

		Thread.sleep(2000);
		// 5. Click the first text link of the first image.
		driver.findElement(By.xpath("(//span[contains(@class,'a-size-medium a-color-base')])[1]")).click();

		Thread.sleep(2000);
		// 6. Take a screenshot of the product displayed.
		Set<String> openWindows = driver.getWindowHandles();
		System.out.println("windows size = " + openWindows.size()); // size = 2 where 0= parent, 1=child

		// create an empty list to get an element
		// convert set to list
		List<String> list = new ArrayList<String>(openWindows);

		driver.switchTo().window(list.get(1)); // switch to child window
		System.out.println("childWindow title = " + driver.getTitle());

		WebElement findElement = driver.findElement(By.xpath("//div[@id='imgTagWrapperId']"));
		File source = findElement.getScreenshotAs(OutputType.FILE);

		File destination = new File("./snapshot/003.png");
		FileUtils.copyFile(source, destination);

		Thread.sleep(3000);
		// 7. Click the 'Add to Cart' button.
		driver.findElement(By.id("add-to-cart-button")).click();

		Thread.sleep(3000);
		// 8. Get the cart subtotal and verify if it is correct.
		WebElement ele = driver.findElement(By.xpath("//span[@id='attach-accessory-cart-subtotal']"));
		String text3 = ele.getText();

		text3 = text3.replace(".00", "");

		boolean bool = text3.contains(text);
		System.out.println("text = " + text);
		System.out.println("text3 = " + text3);
		System.out.println("bool = " + bool);
		if (text3.contains(text)) {
			System.out.println("Price of the product is same as cart subtotal = " + text);
		} else {
			System.out.println("Price of the product is not same as cart subtotal = " + text);
		}

		// 9. Close the browser.
		driver.quit();
	}
}