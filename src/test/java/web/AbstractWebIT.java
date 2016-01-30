package web;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration({ "classpath:selenium-context.xml" })
public abstract class AbstractWebIT extends AbstractJUnit4SpringContextTests {
	@Autowired
	protected WebDriver webDriver;

	protected final String APPLICATION_PREFIX = "http://localhost:58080";

	@Before
	public void setUp() {
		webDriver.manage().window().maximize();
	}
}
