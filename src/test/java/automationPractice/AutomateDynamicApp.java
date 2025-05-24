package automationPractice;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class AutomateDynamicApp {
public static void main(String[] args) {
WebDriver driver =	new ChromeDriver();
//https://www.tyreplex.com/
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
driver.get("https://www.tyreplex.com");

WebElement el =  driver.findElement(By.xpath("//span[contains(text(),'Honda City 5th Gen Tyres')]"));

System.out.println(el.getText());
Actions act = new Actions(driver);
act.scrollToElement(el).build().perform();
}
}
