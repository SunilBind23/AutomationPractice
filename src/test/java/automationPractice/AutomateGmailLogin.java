package automationPractice;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AutomateGmailLogin {
	public static void main(String[] args) {
		ChromeOptions co=	new ChromeOptions();
		co.addArguments("--incognito");
		WebDriver driver = new ChromeDriver(co);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://mail.google.com");
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("bindsunil9336@gmail.com");
		// navigate the next button
		driver.findElement(By.xpath("//span[text()='Next']")).click();

	}
}
