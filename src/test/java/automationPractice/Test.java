package automationPractice;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

public class Test {

	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(
				"https://www.amazon.in/?&tag=googhydrabk1-21&ref=pd_sl_5szpgfto9i_e&adgrpid=155259813593&hvpone=&hvptwo=&hvadid=674893540034&hvpos=&hvnetw=g&hvrand=15815553305902550138&hvqmt=e&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=1007827&hvtargid=kwd-64107830&hydadcr=14452_2316413&gad_source=1");

		WebElement search = driver.findElement(By.id("twotabsearchtextbox"));
		search.sendKeys("phone");
		search.sendKeys(Keys.ENTER);
		driver.findElement(
				By.xpath("//h2[@class='a-size-medium a-spacing-none a-color-base a-text-normal']/child::span")).click();
		System.out.println("first element clicked succefull");

		// taking screenshot
		TakesScreenshot ts = (TakesScreenshot) driver;
		File des = ts.getScreenshotAs(OutputType.FILE);
		File target = new File("screenShotName.png");
		try {
			Files.copy(des, target);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
