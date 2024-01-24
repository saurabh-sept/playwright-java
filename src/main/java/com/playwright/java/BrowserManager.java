package com.playwright.java;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

public class BrowserManager {

    private final Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;
    private static final String HEADLESS = "headless";
    private static final String TRACING = "tracing";
    private static boolean isTracingSet = false;

    public BrowserManager() {
        this.playwright = Playwright.create();
    }

    public void createBrowser(final String browser) {
        switch (browser) {
            case "edge": setupEdgeBrowser();
            case "firefox": setupFirefoxBrowser();
            default: setupChromeBrowser();
        }
        setBrowserContext();
        setPage();
        setTracing();
    }

    private void setupChromeBrowser() {
        this.browser = this.playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(System.getProperty(HEADLESS))));
    }

    private void setupFirefoxBrowser() {
        this.browser = this.playwright.firefox().launch(new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(System.getProperty(HEADLESS))));
    }

    private void setupEdgeBrowser() {
        this.browser = this.playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(System.getProperty(HEADLESS))).setChannel("msedge"));
    }

    
    public Browser getBrowser() {
        return browser;
    }

    
    public void setBrowserContext() {
        browserContext=getBrowser().newContext();
    }

    
    public BrowserContext getBrowserContext() {
        return browserContext;
    }
    
    public void setPage(){
        page=getBrowserContext().waitForPage(()->getBrowserContext().newPage());
    }

    public Page getPage() {
        return page;
    }

    public byte[] captureScreenshot(){
        Path objPath = Paths.get("target/screenshots/Screenshot_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy_hhmmss"))+".png");
        return page.screenshot(new Page.ScreenshotOptions().setPath(objPath).setFullPage(true));
    }

    public void setTracing(){
        if(Boolean.parseBoolean(System.getProperty(TRACING)) && !Objects.isNull(browserContext)){
            browserContext.tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true));

            isTracingSet = true;
        }
    }

    public void stopTracing(){
        if(isTracingSet){
            browserContext.tracing().stop(new Tracing.StopOptions()
            .setPath(Paths.get("target/trace.zip")));
        }
    }
    
    public boolean isTracingOptionSet(){
        return isTracingSet;
    }
    
    public void close() {
        playwright.close();
        System.clearProperty(HEADLESS);
        System.clearProperty(TRACING);
    }

    public void closeBrowser() {
        this.browser.close();
        this.close();
    }    
}
