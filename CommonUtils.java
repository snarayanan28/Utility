package utility;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import com.google.common.base.Predicate;

/**
 * @author HariCharan
 * @version 1.2.1
 * @category Generic methods
 */

public class Utils {

	public static WebDriver driver;
	public static JavascriptExecutor JSdriver;
	public static String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
			.format(Calendar.getInstance().getTime());

	/**
	 * <h1>open_FirefoxBrowser!</h1> This method will invoke a Firefox browser
	 * with maximized window
	 * 
	 */
	public static WebDriver open_FirefoxBrowser() {
		try {

			driver = new FirefoxDriver();
			Log.info("New driver instantiated");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			Log.info("Implicit wait applied on the driver for 10 seconds");
			// driver.get(Constant.getUrl());
			// Log.info("Web application launched successfully");

		} catch (Exception e) {
			Log.error("Class Utils | Method OpenBrowser | Exception desc : "
					+ e.getMessage());
		}
		return driver;
	}

	/**
	 * <h1>open_FirefoxBrowser_MobileView!</h1> This method will invoke a
	 * Firefox browser by setting Iphone useragent
	 */
	public static WebDriver open_FirefoxBrowser_MobileView() throws Exception {
		try {

			FirefoxProfile ffprofile = new FirefoxProfile();
			ffprofile.setPreference("general.useragent.override", "iPhone");
			// iPhone,
			WebDriver driver = new FirefoxDriver(ffprofile);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().setSize(new Dimension(400, 800));

		} catch (Exception e) {
			Log.error("Class Utils | Method OpenBrowser -IPHONE | Exception desc : "
					+ e.getMessage());
		}
		return driver;
	}

	/**
	 * <h1>open_FirefoxBrowser_TabletView!</h1> This method will invoke a
	 * Firefox browser by setting Ipad useragent
	 */
	public static WebDriver open_FirefoxBrowser_TabletView() throws Exception {
		try {

			FirefoxProfile ffprofile = new FirefoxProfile();
			ffprofile.setPreference("general.useragent.override", "iPad");

			WebDriver driver = new FirefoxDriver(ffprofile);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().setSize(new Dimension(768, 1024));

		} catch (Exception e) {
			Log.error("Class Utils | Method OpenBrowser - IPAD | Exception desc : "
					+ e.getMessage());
		}
		return driver;
	}

	/**
	 * <h1>takeScreenshot!</h1> This method will capture a screenshot appending
	 * timestamp to it
	 */
	public static void takeScreenshot(String screenshotName) throws Exception {
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(Constant.FailedScreenshotPath
				+ screenshotName + timeStamp + ".png"));
	}

	/**
	 * <h1>takeScreenshot!</h1> This method will capture a screenshot appending
	 * timestamp to it using Ashot Api
	 */
	public static void takeScreenShot_Ashot(String outputFilePath)
			throws IOException {
		Screenshot screenshot = new AShot().takeScreenshot(driver);
		ImageIO.write(screenshot.getImage(), "PNG", new File(outputFilePath
				+ timeStamp + ".png"));
	}

	static void takeWebelementScreenshot_Ashot(String outputFilePath,
			WebElement element) throws IOException {
		Screenshot screenshot = new AShot().takeScreenshot(driver, element);
		ImageIO.write(screenshot.getImage(), "PNG", new File(outputFilePath
				+ timeStamp + ".png"));
	}

	/*
	 * public static PhantomJSDriver openHeadLessBrowser() throws Exception {
	 * try { DesiredCapabilities phantomBeast = DesiredCapabilities.phantomjs();
	 * 
	 * phantomBeast.setJavascriptEnabled(true);
	 * 
	 * phantomBeast.setCapability("phantomjs.binary.path",
	 * "D:\\eclipse\\phantomjs-2.0.0-windows\\bin\\phantomjs.exe");
	 * 
	 * fd = new PhantomJSDriver(phantomBeast);
	 * 
	 * fd.manage().window().maximize();
	 * fd.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	 * 
	 * fd.get(Constant.getUrl());
	 * 
	 * } catch (Exception e) {
	 * Log.error("Class Utils | Method OpenBrowser | Exception desc : " +
	 * e.getMessage()); } return fd; }
	 */

	/**
	 * <h1>open_ChromeBrowser!</h1> This method will invoke a chrome browser
	 */
	public static WebDriver open_ChromeBrowser() throws Exception {
		try {
			System.setProperty("webdriver.chrome.driver",
					Constant.ChromeDriverPath);
			DesiredCapabilities capability = DesiredCapabilities.chrome();
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new ChromeDriver(capability);

			Log.info("New driver instantiated");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			Log.info("Implicit wait applied on the driver for 10 seconds");
			// driver.get(Constant.getUrl());
			Log.info("Web application launched successfully");

		} catch (Exception e) {
			Log.error("Class Utils | Method OpenBrowser | Exception desc : "
					+ e.getMessage());
		}
		return driver;
	}

	/**
	 * <h1>takeScreenshotOfWebelement!</h1> This method will capture a
	 * screenshot of desired webelement
	 */
	public static void takeScreenshotOfWebelement(WebElement element,
			String screenshotName) throws Exception {
		File v = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage bi = ImageIO.read(v);
		org.openqa.selenium.Point p = element.getLocation();
		int n = element.getSize().getWidth();
		int m = element.getSize().getHeight();
		BufferedImage d = bi.getSubimage(p.getX(), p.getY(), n, m);
		ImageIO.write(d, "png", v);

		FileUtils.copyFile(v, new File(Constant.FailedScreenshotPath
				+ screenshotName + timeStamp + ".png"));
	}

	/**
	 * <h1>switchToNewWindow!</h1> This method will helps us to switch to a New
	 * window
	 */
	public static void switchToNewWindow() {
		Set s = driver.getWindowHandles();
		Iterator itr = s.iterator();
		String w1 = (String) itr.next();
		String w2 = (String) itr.next();
		driver.switchTo().window(w2);
	}

	public static void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	/**
	 * <h1>switchToOldWindow!</h1> This method will helps us to switch to a Old
	 * window
	 */
	public static void switchToOldWindow() {
		Set s = driver.getWindowHandles();
		Iterator itr = s.iterator();
		String w1 = (String) itr.next();
		String w2 = (String) itr.next();
		driver.switchTo().window(w1);
	}

	/**
	 * <h1>switchToDefaultContent!</h1> This method will helps us to switch to a
	 * default content
	 */
	public static void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	/**
	 * <h1>switchToFrame_byName!</h1> This method will helps us to switch to a
	 * Frame. Here you need to pass name of the frame
	 */
	public static void switchToFrame_byName(String frameName) {
		driver.switchTo().frame(frameName);
	}

	/**
	 * <h1>switchToFrame_byIndex!</h1> This method will helps us to switch to a
	 * Frame. Here you need to pass number of the frame
	 */
	public static void switchToFrame_byIndex(int frameValue) {
		driver.switchTo().frame(frameValue);
	}

	public static void switchToFrame_byWebElement(String frameName) {
		WebElement webelement = driver.findElement(By.tagName(frameName));
		try {
			driver.switchTo().frame(webelement);
		} catch (Exception e) {
			throw e;
		}
	}

	public static WebDriver openIEBrowser() throws Exception {
		try {
			System.setProperty("webdriver.ie.driver", Constant.IEDriverPath);
			driver = new InternetExplorerDriver();
			Log.info("New driver instantiated");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Log.info("Implicit wait applied on the driver for 10 seconds");
			driver.get(Constant.getUrl());
			driver.navigate()
					.to("javascript:document.getElementById('overridelink').click()");
			Log.info("Web application launched successfully");

		} catch (Exception e) {
			Log.error("Class Utils | Method OpenBrowser | Exception desc : "
					+ e.getMessage());
		}
		return driver;
	}

	public static String getTestCaseName(String sTestCase) throws Exception {
		String value = sTestCase;
		try {
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");
			value = value.substring(posi + 1);
			return value;
		} catch (Exception e) {
			Log.error("Class Utils | Method getTestCaseName | Exception desc : "
					+ e.getMessage());
			throw (e);
		}
	}

	public static void waitForElementToBeClickable(
			Predicate<WebDriver> expectedConditions) {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(expectedConditions);
	}

	public static void waitForElement(WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void waitMyTime(int i) {
		driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);

	}

	public static void setWindowSize(int Dimension1, int dimension2) {
		driver.manage().window().setSize(new Dimension(Dimension1, dimension2));

	}

	public static void pressKeyDown(WebElement element) {
		element.sendKeys(Keys.DOWN);
	}

	public void pressKeyEnter(WebElement element) {
		element.sendKeys(Keys.ENTER);
	}

	public static void pressKeyUp(WebElement element) {
		element.sendKeys(Keys.UP);
	}

	public static void moveToTab(WebElement element) {
		element.sendKeys(Keys.chord(Keys.ALT, Keys.TAB));
	}

	public static void clickWebelement(WebElement element) {
		try {
			boolean elementIsClickable = element.isEnabled();
			while (elementIsClickable) {
				element.click();
			}

		} catch (Exception e) {
			System.out.println("Element is not enabled");
			e.printStackTrace();
		}
	}

	public static void clickMultipleElements(WebElement someElement,
			WebElement someOtherElement) {
		Actions builder = new Actions(driver);
		builder.keyDown(Keys.CONTROL).click(someElement)
				.click(someOtherElement).keyUp(Keys.CONTROL).build().perform();
	}

	public static void dragAndDrop(WebElement fromWebElement,
			WebElement toWebElement) {
		Actions builder = new Actions(driver);
		builder.dragAndDrop(fromWebElement, toWebElement);
	}

	public static void dragAndDrop_Method2(WebElement fromWebElement,
			WebElement toWebElement) {
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(fromWebElement)
				.moveToElement(toWebElement).release(toWebElement).build();
		dragAndDrop.perform();
	}

	public static void dragAndDrop_Method3(WebElement fromWebElement,
			WebElement toWebElement) throws InterruptedException {
		Actions builder = new Actions(driver);
		builder.clickAndHold(fromWebElement).moveToElement(toWebElement)
				.perform();
		Thread.sleep(2000);
		builder.release(toWebElement).build().perform();
	}

	public static void hoverWebelement(WebElement HovertoWebElement)
			throws InterruptedException {
		Actions builder = new Actions(driver);
		builder.moveToElement(HovertoWebElement).perform();
		Thread.sleep(2000);

	}

	public static void doubleClickWebelement(WebElement doubleclickonWebElement)
			throws InterruptedException {
		Actions builder = new Actions(driver);
		builder.doubleClick(doubleclickonWebElement).perform();
		Thread.sleep(2000);

	}

	public static void selectElementByNameMethod(WebElement element, String Name) {
		Select selectitem = new Select(element);
		selectitem.selectByVisibleText(Name);
	}

	public static void verifyExpectedAndActualOptionsInDropdown(
			WebElement element, List<String> listOfOptions) {

		Select ele = new Select(element);
		// need to give list of options like below. You can add values from
		// excel or csv
		/*
		 * List<String> ds = new ArrayList<String>(); ds.add("Asia");
		 * ds.add("Europe"); ds.add("Africa");
		 */

		List<String> expectedOptions = listOfOptions;
		List<String> actualOptions = new ArrayList<String>();
		for (WebElement option : ele.getOptions()) {
			System.out.println("Dropdown options are: " + option.getText());
			actualOptions.add(option.getText());

		}
		System.out.println("Numbers of options present in the dropdown: "
				+ actualOptions.size());

		Assert.assertEquals(expectedOptions.toArray(), actualOptions.toArray());
		System.out.println("test");

	}

	public static void deselectElementByNameMethod(WebElement element,
			String Name) {
		Select selectitem = new Select(element);
		selectitem.deselectByVisibleText(Name);
	}

	public static void verifyDropdownHaveNoMultipleSelection(
			WebElement element, String Name) {
		Select ss = new Select(element);
		Assert.assertFalse(ss.isMultiple());
	}

	public static void verifyDropdownHaveMultipleSelection(WebElement element,
			String Name) {
		Select ss = new Select(element);
		Assert.assertTrue(ss.isMultiple());
	}

	public static void verifyOptionsInDropdownInAphabeticalOrder(
			WebElement element) {

		Select ele = new Select(element);
		List<String> expectedOptions = new ArrayList<>();
		List<String> actualOptions = new ArrayList<>();

		for (WebElement option : ele.getOptions()) {
			System.out.println("Dropdown options are: " + option.getText());
			actualOptions.add(option.getText());
			expectedOptions.add(option.getText());
		}

		Collections.sort(actualOptions);
		System.out.println("Numbers of options present in the dropdown: "
				+ actualOptions.size());
		Assert.assertEquals(expectedOptions.toArray(), actualOptions.toArray());

	}

	public static void verifyOptionsSizeOfDropdown(WebElement element,
			int numberOfOptions) {
		Select ssd = new Select(element);
		Assert.assertEquals(numberOfOptions, ssd.getOptions().size());
	}

	public static void selectElementByValueMethod(WebElement element,
			String value) {
		Select selectitem = new Select(element);
		selectitem.selectByValue(value);
	}

	public static void deselectElementByValueMethod(WebElement element,
			String value) {
		Select selectitem = new Select(element);
		selectitem.deselectByValue(value);
	}

	public static void selectElementByIndexMethod(WebElement element, int index) {
		Select selectitem = new Select(element);
		selectitem.selectByIndex(index);
	}

	public static void deselectElementByIndexMethod(WebElement element,
			int index) {
		Select selectitem = new Select(element);
		selectitem.deselectByIndex(index);
	}

	public static void clickCheckboxFromList(String xpathOfElement,
			String valueToSelect) {

		List<WebElement> lst = driver.findElements(By.xpath(xpathOfElement));
		for (int i = 0; i < lst.size(); i++) {
			List<WebElement> dr = lst.get(i).findElements(By.tagName("label"));
			for (WebElement f : dr) {
				System.out.println("value in the list : " + f.getText());
				if (valueToSelect.equals(f.getText())) {
					f.click();
					break;
				}
			}
		}
	}

	public static void downloadFile(String href, String fileName)
			throws Exception {
		URL url = null;
		URLConnection con = null;
		int i;
		url = new URL(href);
		con = url.openConnection();
		File file = new File(".//OutputData//" + fileName);
		BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(file));
		while ((i = bis.read()) != -1) {
			bos.write(i);
		}
		bos.flush();
		bis.close();
	}

	public static void navigate_back() {
		driver.navigate().back();
	}

	public static void refresh() {
		driver.navigate().refresh();
	}

	public static String getToolTip(WebElement toolTipofWebElement)
			throws InterruptedException {
		String tooltip = toolTipofWebElement.getAttribute("title");
		System.out.println("Tool text : " + tooltip);
		return tooltip;
	}

	public static void datepicker_setDateAndTime(String TypeMonth,
			String TypeYear, String Date, String setHour, String setMinute,
			String setSeconds, String setHourShift) {
		List<WebElement> date = driver.findElements(By
				.xpath(".//*[@id='ui-datepicker-div']"));
		System.out.println("number of datepickers present : " + date.size());
		for (int i = 0; i < date.size(); i++) {
			System.out.println("element present in the Date picker box "
					+ date.get(i).getText());
			Select month = new Select(
					date.get(i)
							.findElement(
									By.xpath("//select[@class='form-control datetime-ui-datepicker-month input-width-20']")));
			month.selectByVisibleText(TypeMonth);
			Select year = new Select(
					date.get(i)
							.findElement(
									By.xpath("//select[@class='form-control datetime-ui-datepicker-year input-width-20']")));
			year.selectByVisibleText(TypeYear);

			driver.findElement(By.linkText(Date)).click();

			date.get(i).findElement(By.xpath("//input[contains(@id,'hour')]"))
					.clear();
			date.get(i)
					.findElement(By.xpath("//input[contains(@id,'minute')]"))
					.clear();
			date.get(i)
					.findElement(By.xpath("//input[contains(@id,'second')]"))
					.clear();

			date.get(i).findElement(By.xpath("//input[contains(@id,'hour')]"))
					.sendKeys(setHour);
			date.get(i)
					.findElement(By.xpath("//input[contains(@id,'minute')]"))
					.sendKeys(setMinute);
			date.get(i)
					.findElement(By.xpath("//input[contains(@id,'second')]"))
					.sendKeys(setSeconds);
			Select hourshift = new Select(date.get(i).findElement(
					By.xpath("//select[@class='hourShift']")));
			hourshift.selectByVisibleText(setHourShift);
			driver.findElement(
					By.xpath(".//*[@id='ui-datepicker-div']/div[4]/button"))
					.click();
		}
	}

	public static void clickAllLinksInPage(String NameOfScreenshot)
			throws Exception {

		List<WebElement> Links = driver.findElements(By.tagName("a"));
		System.out.println("Total number of links  :" + Links.size());

		for (int p = 0; p < Links.size(); p++) {
			System.out.println("Elements present the body :"
					+ Links.get(p).getText());
			Links.get(p).click();
			Thread.sleep(3000);
			System.out.println("Url of the page  " + p + ")"
					+ driver.getCurrentUrl());
			takeScreenshot(NameOfScreenshot + "_" + p);
			navigate_back();
			Thread.sleep(2000);
		}
	}

	public static void navigate_forward() {
		driver.navigate().forward();
	}

	public static void clearTextField(WebElement element) {
		element.clear();

	}

	public static void highlightelement(WebElement element) {
		for (int i = 0; i < 3; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "color: solid red; border: 5px solid blue;");
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "");

		}

	}

	public static boolean checkAlert() {
		try {
			Alert a = driver.switchTo().alert();
			String str = a.getText();
			System.out.println(str);

			return true;

		} catch (Exception e) {

			System.out.println("no alert ");
			return false;

		}
	}

	public static boolean acceptAlert() {
		try {
			Alert a = driver.switchTo().alert();
			String str = a.getText();
			System.out.println(str);

			a.accept();
			return true;

		} catch (Exception e) {

			System.out.println("no alert ");
			return false;

		}
	}

	public static boolean dismissAlert() {
		try {
			Alert a = driver.switchTo().alert();
			String str = a.getText();
			System.out.println(str);

			a.dismiss();
			return true;

		} catch (Exception e) {

			System.out.println("no alert ");
			return false;

		}
	}

	public static void scrolltoElement(WebElement ScrolltoThisElement) {
		Coordinates coordinate = ((Locatable) ScrolltoThisElement)
				.getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
	}

	public static void checkbox_Checking(WebElement checkbox) {
		boolean checkstatus;
		checkstatus = checkbox.isSelected();
		if (checkstatus == true) {
			System.out.println("Checkbox is already checked");
		} else {
			checkbox.click();
			System.out.println("Checked the checkbox");
		}
	}

	public static void radiobutton_Select(WebElement Radio) {
		boolean checkstatus;
		checkstatus = Radio.isSelected();
		if (checkstatus == true) {
			System.out.println("RadioButton is already checked");
		} else {
			Radio.click();
			System.out.println("Selected the Radiobutton");
		}
	}

	// Unchecking
	public static void checkbox_Unchecking(WebElement checkbox) {
		boolean checkstatus;
		checkstatus = checkbox.isSelected();
		if (checkstatus == true) {
			checkbox.click();
			System.out.println("Checkbox is unchecked");
		} else {
			System.out.println("Checkbox is already unchecked");
		}
	}

	public static void radioButton_Deselect(WebElement Radio) {
		boolean checkstatus;
		checkstatus = Radio.isSelected();
		if (checkstatus == true) {
			Radio.click();
			System.out.println("Radio Button is deselected");
		} else {
			System.out.println("Radio Button is already Deselected");
		}
	}

	public static void passThisStep(String reasonForPass) {
		Assert.assertTrue(true, reasonForPass);
	}

	public static void failThisStep(String reasonForFailing) {
		Assert.fail(reasonForFailing);
	}

	public static String generateRandomString(int length) {
		StringBuilder str = new StringBuilder(
				RandomStringUtils.randomAlphabetic(length));
		int idx = str.length() - 8;

		while (idx > 0) {
			str.insert(idx, " ");
			idx = idx - 8;
		}
		return str.toString();
	}

	public static String generateRandomPhoneNumber() {
		return RandomStringUtils.randomNumeric(10);
	}

	public static String generateRandomNumber(int length) {
		return RandomStringUtils.randomNumeric(length);
	}

	public static String generateRandomAlphaNumeric(int length) {
		StringBuilder str = new StringBuilder(
				RandomStringUtils.randomAlphanumeric(length));
		int idx = str.length() - 8;

		while (idx > 0) {
			str.insert(idx, " ");
			idx = idx - 8;
		}
		return str.toString();

	}

	public static String generateRandomStringWithAllowedSplChars(int length,
			String allowdSplChrs) {
		String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890"
				+ allowdSplChrs + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder str1 = new StringBuilder(RandomStringUtils.random(length,
				allowedChars));
		int idx = str1.length() - 8;

		while (idx > 0) {
			str1.insert(idx, " ");
			idx = idx - 8;
		}
		return str1.toString();
	}

	public static String generateRandomEmail(int length) {
		String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890"
				+ "_-.";
		String email = "";
		String temp = RandomStringUtils.random(length, allowedChars);
		email = temp.substring(0, temp.length() - 9) + "@testdata.com";
		return email;
	}

	public static String generateRandomUrl(int length) {
		String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890"
				+ "-.";
		String url = "";
		String temp = RandomStringUtils.random(length, allowedChars);
		url = temp.substring(0, 3) + "." + temp.substring(4, temp.length() - 4)
				+ "." + "com";
		return url;
	}

	public static boolean isElementPresent_locator(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isElementPresent_webelement(WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void verifyCSSvalue(WebElement element, String cssValueOf,
			String expectedCssValue) {
		try {
			element.isDisplayed();
			System.out.println("CSS Value of " + cssValueOf + " is :"
					+ element.getCssValue(cssValueOf));
			Assert.assertEquals(expectedCssValue,
					element.getCssValue(cssValueOf));
		} catch (NoSuchElementException e) {
			throw e;
		}

	}

	/**
	 * Verifying the flags in cookie such as httponly, issecure
	 * 
	 * @param Expectedcookie
	 */
	public static void verifyFlagsOfCookie(String Expectedcookie) {
		try {
			Cookie saveMyCookie = driver.manage()
					.getCookieNamed(Expectedcookie);
			if (saveMyCookie.isHttpOnly() && saveMyCookie.isSecure()) {
				Assert.assertTrue(true);
			} else {
				System.out.println(saveMyCookie.getName()
						+ " doesnt have flags IsHttpOnly & IsSecure");
				Assert.fail("doesnt have flags IsHttpOnly & IsSecure");
			}

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Adding a cookie with name, value, path
	 * 
	 * @param cookieName
	 * @param cookieValue
	 * @param Domain
	 * @param path
	 */
	public static void addCookie(String cookieName, String cookieValue,
			String Domain, String path) {
		try {
			Cookie name = new Cookie(cookieName, cookieValue, Domain, path,
					new Date());
			driver.manage().addCookie(name);
			refresh();
			System.out.println("Added Cookie " + cookieName);

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Delete all cookies present in the application
	 */
	public static void deleteAllCookies() {
		driver.manage().deleteAllCookies();

	}

	/**
	 * getAllCookies, this will get you all the cookies present in the
	 * page/application
	 */
	public static Set<Cookie> getAllCookies() {
		Set<Cookie> cookiesList = driver.manage().getCookies();
		for (Cookie getcookies : cookiesList) {
			System.out.println(getcookies);
		}
		return cookiesList;
	}

	public static void verifyPageSourceContains(String stringInPagesource) {
		String pageSource = driver.getPageSource();
		Assert.assertTrue(pageSource.contains(stringInPagesource));
	}

	/**
	 * <h1>getPageLoadTime<h1>This method can be used to get page load time.
	 * This returns Long.
	 */
	public static long getPageLoadTime() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long loadEventEnd = (Long) js
				.executeScript("return window.performance.timing.loadEventEnd;");
		long navigationStart = (Long) js
				.executeScript("return window.performance.timing.navigationStart;");
		long loadtime = (loadEventEnd - navigationStart) / 1000;
		System.out.println("Page Load Time is " + loadtime + " seconds.");
		return loadtime;
	}

	/**
	 * In order to zoom out you need to pass an integer, to zoomout n number of
	 * times
	 */
	public static void zoomOut(int toExtent) {
		for (int i = 0; i < toExtent; i++) {
			driver.findElement(By.tagName("html")).sendKeys(
					Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
		}
	}

	/**
	 * In order to zoom In you need to pass an integer, to zoom In n number of
	 * times
	 */
	public static void zoomIn(int toExtent) {
		for (int i = 0; i < toExtent; i++) {
			driver.findElement(By.tagName("html")).sendKeys(
					Keys.chord(Keys.CONTROL, Keys.ADD));
		}
	}

	public static void zoomToDefault() {
		driver.findElement(By.tagName("html")).sendKeys(
				Keys.chord(Keys.CONTROL, "0"));

	}

	/**
	 * <h1>verifyVideoUsingVideoElement<h1>This method can be used to verify a
	 * video present in the page with tag video.
	 * 
	 * @param element
	 * @param srcOfVideo
	 * @param durationOfVideo
	 */
	public static void verifyVideoUsingVideoElement(WebElement element,
			String srcOfVideo, long durationOfVideo) {
		// Get the HTML5 Video Element
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String source = (String) jsExecutor.executeScript(
				"return arguments[0].currentSrc;", element);
		long duration = (Long) jsExecutor.executeScript(
				"return arguments[0].duration", element);

		Assert.assertEquals(source, srcOfVideo);
		Assert.assertEquals(duration, durationOfVideo);

	}

	public static int getRandomNumberBetween(int min, int max) {

		Random foo = new Random();
		int randomNumber = foo.nextInt(max - min) + min;
		if (randomNumber == min) {
			return min + 1;
		} else {
			return randomNumber;
		}

	}

	/**
	 * 
	 * RandomDataMethods.generateRandomDate("dd MMM yyyy", "18 Jul 2016",
	 * "20 Sep 2017")
	 * 
	 * @param Format
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws java.text.ParseException
	 */
	public static String generateRandomDate(String Format, String startDate,
			String endDate) throws java.text.ParseException {
		DateFormat formatter = new SimpleDateFormat(Format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatter.parse(startDate));
		Long value1 = cal.getTimeInMillis();

		cal.setTime(formatter.parse(endDate));
		Long value2 = cal.getTimeInMillis();

		long value3 = (long) (value1 + Math.random() * (value2 - value1));
		cal.setTimeInMillis(value3);
		return formatter.format(cal.getTime());
	}

	/**
	 * Set proxy Address(IP) and port number as a String (ex:localhost:8080)
	 * 
	 * @param ProxyHostWithPort
	 */
	public static void openFirefoxWithProxySettings(String ProxyHostWithPort) {

		Proxy proxy = new Proxy();
		proxy.setHttpProxy(ProxyHostWithPort).setFtpProxy(ProxyHostWithPort)
				.setSslProxy(ProxyHostWithPort)
				.setSocksProxy(ProxyHostWithPort);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.PROXY, proxy);
		driver = new FirefoxDriver(cap);
		driver.manage().window().maximize();

	}

	public static int[] getX_Y_cordinates(WebElement element) {
		int[] xy = null;
		Point p = element.getLocation();
		System.out.println("X Position: " + p.getX());
		System.out.println("Y Position: " + p.getY());
		int x = p.getX();
		int y = p.getY();
		xy = new int[x * y];
		return xy;

	}

	public static int[] getWidth_HeightOfElement(WebElement element) {
		int[] xy = null;
		Dimension dimensions = element.getSize();
		System.out.println("Width : " + dimensions.width);
		System.out.println("Height : " + dimensions.height);
		int x = dimensions.getWidth();
		int y = dimensions.getHeight();
		xy = new int[x * y];
		return xy;

	}
}