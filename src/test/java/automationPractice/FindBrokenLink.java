package automationPractice;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class FindBrokenLink {

    @Test
    public void checkBrokenLinks() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
        String url = "https://www.browserstack.com/guide/how-to-find-broken-links-in-selenium";
        driver.get(url);

        List<WebElement> links = driver.findElements(By.tagName("a"));

        for (WebElement linkElement : links) {
            String href = linkElement.getDomAttribute("href");

            // Skip null, empty, or javascript/mailto links
            if (href == null || href.isEmpty() || href.startsWith("javascript") || href.startsWith("mailto")) {
                continue;
            }

            checkLink(href);
        }

        driver.quit();
    }

    public static void checkLink(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(3000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                System.out.println(url + " - OK");
            } else {
                System.out.println(url + " - " + connection.getResponseMessage() + " - Broken link");
            }
        } catch (Exception e) {
            System.out.println(url + " - Exception: " + e.getMessage());
        }
    }
}
