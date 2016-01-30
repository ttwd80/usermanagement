package webjar;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class WebjarProperVersionIT {

	@Test
	public void testWebjarProperVersion() throws IOException {
		List<String> list = listEntries();
		if (list.size() == 0) {
			fail("nothing in webjar.properties");
		}
		List<String> listInvalid = new ArrayList<String>();
		for (String value : list) {
			if (isValid(value)) {
				System.out.println(value + " OK");
			} else {
				listInvalid.add(value);
			}
		}
		if (listInvalid.size() > 0) {
			fail(StringUtils.join(listInvalid, ", "));
		}
	}

	private boolean isValid(String value) {
		if (value.startsWith("/webjars/")) {
			try {
				String location = "/META-INF/resources" + value;
				long length = new ClassPathResource(location).contentLength();
				if (length < 0) {
					return false;
				} else {
					return true;
				}
			} catch (IOException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	private List<String> listEntries() throws IOException {
		List<String> list = new ArrayList<>();
		Properties properties = new Properties();
		String filename = "webjar.properties";
		Resource resource = new ClassPathResource(filename);
		InputStream inputStream = resource.getInputStream();
		properties.load(inputStream);
		Enumeration<?> enumeration = properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			Object next = enumeration.nextElement();
			if (next != null) {
				String value = properties.getProperty(next.toString());
				if (StringUtils.trimToNull(value) != null) {
					list.add(value);
				}
			}
		}
		IOUtils.closeQuietly(inputStream);
		Collections.sort(list);
		return list;
	}
}
