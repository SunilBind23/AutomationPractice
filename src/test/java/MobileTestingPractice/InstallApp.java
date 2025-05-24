package MobileTestingPractice;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class InstallApp {

	public static void main(String[] args) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("deviceName", "Xiaomi M2006C3LII");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("automationName", "uiautomator2");
		capabilities.setCapability("platformVersion", "12");
		capabilities.setCapability("app", "C:\\Users\\YourName\\Downloads\\ApiDemos-debug.apk");

		URL url = new URL("http://127.0.0.1:4723/");
		AndroidDriver driver = new AndroidDriver(url, capabilities);
		System.out.println("App launched successfully");

		Thread.sleep(3000); // Wait for app to load

		// Perform 3 left swipes
		for (int i = 0; i < 3; i++) {
			swipeLeft(driver);
			Thread.sleep(1000);
		}

		System.out.println("Swiped 3 times");
		driver.quit();
	}

	public static void swipeLeft(AndroidDriver driver) {
		Dimension size = driver.manage().window().getSize();
		int startX = (int) (size.width * 0.8);
		int endX = (int) (size.width * 0.2);
		int y = size.height / 2;

		new TouchAction(driver).press(PointOption.point(startX, y))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(endX, y))
				.release().perform();
	}
}