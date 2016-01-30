package web;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class WebLoginPage extends AbstractWebIT {

	@Test
	public void testLoginPageCanBeViewed() {
		webDriver.get(APPLICATION_PREFIX + "/");
		final List<WebElement> items = webDriver.findElements(By.id("form-login"));
		assertThat(items.size(), equalTo(1));

	}
}
