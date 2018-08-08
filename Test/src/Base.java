import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class Base {
	
	
	@Test
	public void start() throws IOException, AWTException {
		
		AndroidDriver<WebElement>  driver;

		// TODO Auto-generated method stub
	 File appDir = new File("src");
     File app = new File(appDir, "ApiDemos-debug.apk");
     DesiredCapabilities capabilities = new DesiredCapabilities();
     
     capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Rahulemulator");
     capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
    driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  /* // ele = driver.findElementById("android:id/list");
//    BufferedImage expectedImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\Images\\ElementScreenshot.png"));
    Screenshot expImage = new AShot().takeScreenshot(driver,driver.findElementById("android:id/list"));
//    BufferedImage actualImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\ElementScreenshot.png"));
   ImageIO.write(expImage.getImage(),"PNG",new File(System.getProperty("user.dir") +"\\ElementScreenshot.png")); 
//    ImageDiffer imgDiff = new ImageDiffer();
//    ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
//    Assert.assertFalse(diff.hasDiff(),"Images are Same");
*/  
   WebElement ele = driver.findElementById("android:id/list");
    File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    BufferedImage  fullImg = ImageIO.read(screenshot);

    // Get the location of element on the page
    org.openqa.selenium.Point point = ele.getLocation();

    // Get width and height of the element
    int eleWidth = ele.getSize().getWidth();
    int eleHeight = ele.getSize().getHeight();

    // Crop the entire page screenshot to get only element screenshot
    BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
        eleWidth, eleHeight);
    ImageIO.write(eleScreenshot, "png", screenshot);

    // Copy the element screenshot to disk
    File screenshotLocation = new File("./element.png");
    FileUtils.copyFile(screenshot, screenshotLocation);
    BufferedImage expImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\Images\\element.png"));
    BufferedImage actualImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\element.png"));
    ImageDiffer imgDiff = new ImageDiffer();
  ImageDiff diff = imgDiff.makeDiff(actualImage, expImage);
  Assert.assertFalse(diff.hasDiff(),"Images are Same");
  
    
	}

}
