package com.playwright.java.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.playwright.java.BrowserManager;
import com.playwright.java.PropertiesLoader;

public class BaseTest {
    BrowserManager browserManager;

    @BeforeSuite(alwaysRun = true)
    public void setupProperties() throws Exception {
        PropertiesLoader.LoadProperties();
    }

    @Parameters({ "browser" })
    @BeforeClass(alwaysRun = true)
    public void setupTest(@Optional("chrome") final String browser) {
        this.browserManager = new BrowserManager();
        this.browserManager.createBrowser(browser);
    }

    @AfterMethod(alwaysRun = true)
    public void captureScreenshot() {
        // this.browserManager.captureScreenshot(); Tracing also has screenshots
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        this.browserManager.stopTracing();
        this.browserManager.closeBrowser();
    }   
}
