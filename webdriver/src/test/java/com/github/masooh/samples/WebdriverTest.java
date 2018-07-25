package com.github.masooh.samples;


import java.util.logging.Level;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

public class WebdriverTest {

    WebDriver driver;

    @BeforeEach
    void setUp() {
        // Optional, if not specified, WebDriver will search your path for chromedriver.
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "src/main/bin/chromedriver");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "webdriver.log");

        ChromeOptions options = new ChromeOptions();

        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);

        options.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);

        driver = new ChromeDriver(options);
    }

    @Test
    public void testGoogleSearch() {
        driver.get("http://www.google.com/");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("ChromeDriver");
        searchBox.submit();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
