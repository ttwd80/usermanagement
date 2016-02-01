package web;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

public class WebLoginPageIT extends AbstractWebIT {

	@Test
	public void testLoginPageCanBeViewed() throws IOException {
		webDriver.get(APPLICATION_PREFIX + "/");
		final List<WebElement> items = webDriver.findElements(By.id("form-login"));
		final File file = ((TakesScreenshot) (webDriver)).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("image-0001.png"));
		assertThat(items.size(), equalTo(1));
	}

	@Test
	public void testLoginBad() throws IOException {
		webDriver.get(APPLICATION_PREFIX + "/");
		webDriver.findElement(By.id("username")).sendKeys("bad");
		webDriver.findElement(By.id("password")).sendKeys("bad");
		webDriver.findElement(By.id("submit")).click();
		final File file = ((TakesScreenshot) (webDriver)).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("image-0002.png"));
		final String currentUrl = webDriver.getCurrentUrl();
		assertThat(currentUrl, endsWith("/login?fail=true"));
	}
}
