package automationPractice;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FlipcartAutomation {

	public static void main(String[] args) throws InterruptedException {
		
  WebDriver  driver =  new ChromeDriver();

  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
  driver.manage().window().maximize();
  driver.get("https://www.flipkart.com/");
  
  JavascriptExecutor jse = (JavascriptExecutor)driver;
  
  
 WebElement loginbtn = driver.findElement(By.xpath("//span[text()='Login']"));
 jse.executeScript("arguments[0].click()", loginbtn);
  driver.findElement(By.xpath("//input[@class='r4vIwl BV+Dqf']")).sendKeys("9651960984");
  Thread.sleep(2000);
  driver.findElement(By.xpath("//button[text()='Request OTP']")).click();
  Thread.sleep(9000);
  // entre otp
  // then next
  
  WebElement searchbx =driver.findElement(By.name("q"));
  Thread.sleep(2000);
  searchbx.sendKeys("letop");
  searchbx.sendKeys(Keys.ENTER);
  // finding element
  
List<WebElement> productList =  driver.findElements(By.xpath("//div[@class='KzDlHZ']"));
if(productList.size()>0) {
	productList.get(0).click();
}
// switch to new window
for(String windHandle : driver.getWindowHandles()) {
	driver.switchTo().window(windHandle);
}

// click on wishlist 
try {
	driver.findElement(By.xpath("//div[@class='+7E521']")).click();
	Thread.sleep(2000);
}catch(Exception e) {
	System.out.println("could not found wishlist icon");
}

// go to wishlist and delete product

// confirm delete
driver.findElement(By.xpath("//button[contains(text(),'REMOVE')]")).click();


// add to  wishlist again
driver.navigate().back();
Thread.sleep(3000);

try {
	driver.findElement(By.xpath("//div[@class='+7E521']")).click();
	Thread.sleep(3000);
}catch(Exception e) {
	System.out.println("could not found wishlist again");
}
// add to cart from wishlist
driver.get("https://www.flipkart.com/wishlist");
Thread.sleep(3000);
// click on product in wishlist
driver.findElement(By.xpath("//div[@class='_16cbFU']")).click();

 Set<String> handles =  driver.getWindowHandles();
 for(String windowh :handles) {
	 driver.switchTo().window(windowh);
	 
 }
 Thread.sleep(3000);
 try {
 driver.findElement(By.xpath("//button[text()='Add to cart']")).click();
 Thread.sleep(3000);
 }catch(Exception e) {
	 System.out.println("Add to cart not found");
 }

// go  to cart
 driver.findElement(By.xpath("//span[text()='Cart']")).click();
 
 // proccedd to payment
 

	}

}
