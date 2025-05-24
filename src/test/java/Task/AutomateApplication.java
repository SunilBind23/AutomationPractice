package Task;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.io.Files;

public class AutomateApplication {
	WebDriver driver;

	private void jsClick(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	private void ActionsSendKeys(WebElement el, String value) {
		new Actions(driver).sendKeys(el, value).build().perform();
	}

	
	public void setup() {
		ChromeOptions cho = new ChromeOptions();
		cho.addArguments("--incognito");
		driver = new ChromeDriver(cho);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://www.saucedemo.com/");
	}

	public void Login(String userName, String Password) {
		ActionsSendKeys(driver.findElement(By.id("user-name")), userName);
		ActionsSendKeys(driver.findElement(By.id("password")), Password);

		jsClick(driver.findElement(By.id("login-button")));
	}

	@Test(priority = 0)
	public void VerifyPurchaseSuccessFull() throws InterruptedException {
		Login("standard_user", "secret_sauce");
		new Select(driver.findElement(By.className("product_sort_container"))).selectByValue("hilo");
		List<WebElement> allPrice = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));

		List<Double> priceList = new ArrayList<>();

		for (WebElement el : allPrice) {
			System.out.println(el.getText().replace("$", ""));
			String priceText = el.getText().replace("$", "");
			priceList.add(Double.parseDouble(priceText));
		}
		Collections.sort(priceList, Collections.reverseOrder());
		System.out.println("Top 2 highest prices: " + priceList.get(0) + "," + priceList.get(1));

		double totalPrice = priceList.get(0) + priceList.get(1);

		List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[text()='Add to cart']"));

		jsClick(addToCartButtons.get(0));
		jsClick(addToCartButtons.get(1));
		System.out.println("items are added to cart !");

		jsClick(driver.findElement(By.className("shopping_cart_link")));

		// validate all items add in cart

		List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));
		if (cartItems.size() == 2) {
			System.out.println("Success: Exact 2 product are added");

		} else {
			System.out.println("fail: Exact 2 product but found :" + cartItems.size());
		}
		List<WebElement> cartPrice = driver.findElements(By.className("inventory_item_price"));
		double calculatedTotal = 0.0;
		for (WebElement priceElement : cartPrice) {
			String priceText = priceElement.getText().replace("$", "");
			double price = Double.parseDouble(priceText);
			calculatedTotal += price;
		}
		System.out.println("calculatedTotal price is + " + calculatedTotal);

		double expectedTotal = totalPrice;

		jsClick(driver.findElement(By.name("checkout")));

		Thread.sleep(500);
		WebElement fname = driver.findElement(By.id("first-name"));
		Thread.sleep(500);
		WebElement Lname = driver.findElement(By.id("last-name"));
		Thread.sleep(500);
		WebElement PostalCode = driver.findElement(By.id("postal-code"));
		Thread.sleep(500);
		ActionsSendKeys(fname, "John");

		Thread.sleep(500);
		ActionsSendKeys(Lname, "Doe");

		Thread.sleep(500);
		ActionsSendKeys(PostalCode, "226001");

		jsClick(driver.findElement(By.id("continue")));

		jsClick(driver.findElement(By.id("finish")));

		String ActualText = driver.findElement(By.xpath("//h2[@class='complete-header']")).getText().toUpperCase();
		String ExpectedMsg = "THANK YOU FOR YOUR ORDER";
		if (ActualText.contains(ExpectedMsg)) {
			System.out.println("test case pass");
			getScreenShot("purchase_success");
			
			Thread.sleep(500);
			driver.quit();
		} else {
			System.out.println("test case fail");
		}
		

	}

	@Test(priority = 1)
	public void FailureTestCase() {
		setup();
		Login("invalid_user", "wrong_pass");
		WebElement errorMsg = driver.findElement(By.className("error-button"));

		if (errorMsg.isDisplayed() == true) {
			System.out.println("Login failed as expected with invalid credentials");
			getScreenShot("login_failed");
		} else {
			System.out.println("Login  SuccessFull");
		}
		driver.quit();
	}

	

	private void getScreenShot(String SSName) {
		try {
			// Take screenshot
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			// Create "ScreenShot" folder if it doesn't exist
			File dir = new File("ScreenShot");
			if (!dir.exists()) {
				dir.mkdirs(); // Create directory
			}

			// Destination file inside the folder
			File destFile = new File(dir, SSName + ".png");

			// Copy screenshot to destination
			Files.copy(screenshot, destFile);

			System.out.println("Screenshot saved at: " + destFile.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
