package com.week4.day1.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class ActionsSnapdealSite {

	public static void main(String[] args) throws InterruptedException, IOException {

		ChromeOptions option = new ChromeOptions();
		option.addArguments("--disable-notifications");

		// initialize the ChromeDriver
		ChromeDriver driver = new ChromeDriver(option);

		// 1. Launch (https://www.snapdeal.com/)
		driver.get("https://www.snapdeal.com/");

		// maximize the window
		driver.manage().window().maximize();
		
		// add an implicit wait to load the web elements of a page
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		// 2. Go to "Men's Fashion".
		Thread.sleep(3000);
		WebElement element = driver.findElement(By.xpath("(//span[@class='catText'])[1]"));
		Actions act = new Actions(driver);
		act.moveToElement(element).perform();

		// 3. Go to "Sports Shoes".
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[@class='linkTest']")).click();

		// 4. Get the count of sports shoes.
		WebElement findElement = driver
				.findElement(By.xpath("//div[text()='Sports Shoes for Men']/following-sibling::div"));
		String sportsShoesCount = findElement.getText();
		System.out.println("sportsShoesCount = " + sportsShoesCount);

		Thread.sleep(3000);
		// 5. Click on "Training Shoes".
		driver.findElement(By.xpath("//div[text()='Training Shoes']")).click();

		// 6. Sort the products by "Low to High".
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@class='sort-drop clearfix']")).click();
		WebElement sort = driver.findElement(By.xpath("(//li[@data-index='1'])[2]"));

		Actions act1 = new Actions(driver);
		act1.click(sort).perform();

		// 7. Check if the displayed items are sorted correctly.
		Thread.sleep(3000);
		List<WebElement> productsPrice = driver.findElements(By.xpath("//span[@class='lfloat product-price']"));
		List<Integer> newList = new ArrayList<Integer>();

		System.out.println("productsPrice size " + productsPrice.size());

		for (int i = 0; i < productsPrice.size(); i++) {
			String str = productsPrice.get(i).getText().replace("Rs. ", "").replace(",", "");
			int value = Integer.parseInt(str);
			newList.add(value);
		}

		System.out.println("newList size " + newList.size());
		System.out.println("before sort newList " + newList);
		Collections.sort(newList);

		System.out.println("after sort newList " + newList);
		int size = newList.size();
		int lastIndex = size-1;
		System.out.println("lastIndex " + lastIndex);
		int minValue = newList.get(0);
		int maxValue = newList.get(lastIndex);
		System.out.println("least price " + minValue);
		System.out.println("highest price " + maxValue);
		
		Thread.sleep(5000);
		// 8. Select any price range ex:(500-700).
		WebElement findElement4 = driver.findElement(By.name("fromVal"));
		Actions act5 = new Actions(driver);
		act5.scrollToElement(findElement4).perform();
		findElement4.clear();
		findElement4.sendKeys("500");
		
		WebElement findElement5 = driver.findElement(By.name("toVal"));
		findElement5.clear();
		findElement5.sendKeys("700");
		
		driver.findElement(By.xpath("//div[text()[normalize-space()='GO']]")).click();

		Thread.sleep(3000);
		// 9. Filter by any colour.
		driver.findElement(By.xpath("(//input[@type='checkbox']/following-sibling::label)[2]")).click();

		// 10. Verify all the applied filters.
		// 11. Mouse hover on the first resulting "Training Shoes".
		Thread.sleep(3000);
		WebElement ele = driver.findElement(By.xpath("(//div[contains(@class,'col-xs-6 ')])[3]"));
		Actions act3 = new Actions(driver);
		act3.moveToElement(ele).perform();

		// 12. Click the "Quick View" button.
		driver.findElement(By.xpath("//div[contains(@class,'center quick-view-bar')]")).click();

		// 13. Print the cost and the discount percentage.
		Thread.sleep(3000);
		WebElement findElement2 = driver.findElement(By.className("payBlkBig"));
		WebElement findElement3 = driver.findElement(By.className("percent-desc"));

		System.out.println("price " + findElement2.getText());
		System.out.println("discount " + findElement3.getText());

		Thread.sleep(3000);
		// 14. Take a snapshot of the shoes.
		File source = driver.getScreenshotAs(OutputType.FILE);
		File destination = new File("./snapshot/005.jpg");
		FileUtils.copyFile(source, destination);

		Thread.sleep(3000);
		// 15. Close the current window.
		driver.findElement(By.xpath("//div[contains(@class,'close close1')]")).click();
		// 16. Close the main window
		Thread.sleep(5000);
		driver.close();
	}
}