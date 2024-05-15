package com.week4.day1.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class ActionsAmazonSite2 {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Initialize ChromeDriver
		ChromeDriver driver = new ChromeDriver();

		// Load the URL https://www.amazon.in/
		driver.get("https://www.amazon.in");

		// maximize the window
		driver.manage().window().maximize();

		// Add an implicit wait to ensure the web page elements are fully loaded
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// Scroll down to the "Conditions of Use and Sale" section at the bottom of the
		// page using Actions class
		WebElement element = driver.findElement(By.xpath("//a[text()='Conditions of Use & Sale']"));

		Actions act = new Actions(driver);
		act.scrollToElement(element).perform();
		String text = element.getText();
		System.out.println("element text = " + text);

		// Finally, take a screenshot of the displayed webpage
		File source = driver.getScreenshotAs(OutputType.FILE);
		File destination = new File("./snapshot/004.jpg");
		FileUtils.copyFile(source, destination);

		Thread.sleep(3000);
		driver.close();
	}
}