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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.io.Files;

public class AutomationApplication {

	WebDriver driver;

	@BeforeTest
	public void setup() {
		ChromeOptions cho =	new ChromeOptions();
		cho.addArguments("--incognito");
		driver = new ChromeDriver(cho);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://www.saucedemo.com/");
	}

	private void jsClick(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	

	private void sendKeysWithActions(WebElement element, String value) {
		new Actions(driver).sendKeys(element, value).build().perform();

	}

	public void Login(String UserName, String PassWord) {

		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(UserName);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(PassWord);
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
	}

	@Test(priority = 0)
	public void validateOrderSuccessFull() throws InterruptedException {

		System.out.println("TestCaes 1 Started");

		// valid Login
		Login("standard_user", "secret_sauce");

		// From the -product list-Apply the “Price (low to high)” filter
		WebElement weFilter = driver.findElement(By.className("product_sort_container"));
		weFilter.click();

		Select select = new Select(weFilter);
		// select.selectByVisibleText("Price (low to high)");
		select.selectByValue("hilo");
		// Programmatically identify the 2 most expensive items (i.e., reverse the
		// sorted list
		// inventory_item_price

		List<WebElement> allPrice = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));
		List<Double> priceList = new ArrayList<>();

		for (WebElement el : allPrice) {
			System.out.println(el.getText().replace("$", ""));

			String priceText = el.getText().replace("$", "");
			priceList.add(Double.parseDouble(priceText));

		}
		// Sort in descending order
		Collections.sort(priceList, Collections.reverseOrder());

		// Print all sorted prices
		// System.out.println("Sorted Prices: " + priceList);

		// Get top 2 prices
		System.out.println("Top 2 highest prices: " + priceList.get(0) + ", " + priceList.get(1));

		double totalPrice = priceList.get(0) + priceList.get(1);

		List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[text()='Add to cart']"));

		// Step 2: Click the last 2 buttons

		jsClick(addToCartButtons.get(0));
		jsClick(addToCartButtons.get(1));

		System.out.println("items added");

		// Go to checkOut Page
		// Step 1: Navigate to cart page
		WebElement cartButton = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
		jsClick(cartButton);

		
		// Step 2: Get all items in the cart
		List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));

		// Step 3: Validate that exactly 2 items are added
		if (cartItems.size() == 2) {
			System.out.println("Success: Exactly 2 items are present in the cart.");
		} else {
			System.out.println("Error: Expected 2 items, but found " + cartItems.size());
		}

		

		// Step 1: Get all price elements from the cart page
		List<WebElement> cartPrices = driver.findElements(By.className("inventory_item_price"));

		// Calculating price and verify the expected
		
		double calculatedTotal = 0.0;

		// Step 2: Loop through each price, remove $, convert to double, and add to
		// total
		for (WebElement priceElement : cartPrices) {
			String priceText = priceElement.getText().replace("$", "");
			double price = Double.parseDouble(priceText);
			calculatedTotal += price;
		}

		// Step 3: Print the calculated total
		System.out.println("Calculated total: $" + calculatedTotal);

		// Step 4: Expected total
		double expectedTotal = totalPrice; // replace with actual expected values

		// Step 5: Validate
		if (calculatedTotal == expectedTotal) {
			System.out.println("Total matches: $" + calculatedTotal);
		} else {
			System.out.println("Total mismatch. Expected: $" + expectedTotal + ", but got: $" + calculatedTotal);
		}

		// Proceed To CheckOut

		WebElement checkOutBtn = driver.findElement(By.name("checkout"));

		jsClick(checkOutBtn);

		Thread.sleep(500);
		WebElement FnameField = driver.findElement(By.id("first-name"));

		Thread.sleep(500);
		WebElement LnameField = driver.findElement(By.id("last-name"));

		Thread.sleep(500);
		WebElement PostalCode = driver.findElement(By.id("postal-code"));

		Thread.sleep(500);
		sendKeysWithActions(FnameField, "John");
		Thread.sleep(500);
		sendKeysWithActions(LnameField, "Doe");
		Thread.sleep(500);
		sendKeysWithActions(PostalCode, "221406");
		// 6. continue through to the final confirmation page
		Thread.sleep(500);
		WebElement continueBtn = driver.findElement(By.id("continue"));
		Thread.sleep(500);

		jsClick(continueBtn);

		// jse.executeScript("arguments[0].click();", continueBtn);

		Thread.sleep(500);
		jsClick(driver.findElement(By.id("finish")));
		// driver.findElement(By.id("finish")).click();
		// verify final page confirmation massage

		WebElement thankYouMessage = driver.findElement(By.xpath("//h2[contains(text(),'Thank you for your order')]"));
		// Validate the message
		String actualMessage = thankYouMessage.getText().toUpperCase();
		if (actualMessage.contains("THANK YOU FOR YOUR ORDER")) {
			System.out.println("Order placed successfully: " + actualMessage);

			// Take screenshot if message is present
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.className("pony_express")));

			getScreenShot("purchase_success");

		} else {
			System.out.println("Unexpected message: " + actualMessage);
		}

		System.out.println("Test case1 run successFull");
		tearDown();

	}

	public void getScreenShot(String SSName) {
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

	

//================= TestCase 2 Login Failure ==================
	 @Test(priority = 1)
	public void LoginFailure() {
		System.out.println("TestCaes 2 Started");
		setup();
		Login("invalid_user", "wrong_pass");
		// verifying error massage
		WebElement errorMsg = driver.findElement(By.xpath("//button[@class='error-button']/parent::h3"));

		String ActualMsg = errorMsg.getText();
		String ExpectedErorMsg = "Epic sadface: Username and password do not match";
		if (ActualMsg.contains(ExpectedErorMsg)) {
			getScreenShot("login_failed");
		} else {
			System.out.println("Test Case Fail");

		}
		System.out.println("Test case2 run successFull");

	}
	 
	 @AfterTest
		public void tearDown() {
			if (driver != null) {
				driver.quit();
			}
		}
}