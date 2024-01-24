package com.playwright.java.pages;

import static org.testng.Assert.assertEquals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {
    private static final String BASEURL = "baseURL";
    private final Page page;
    private final Locator userNameInput;    
    private final Locator passwordInput;    
    private final Locator loginButton;    
    private final Locator loginErrorLabel;
    private final Locator loginErrorButton;

  public LoginPage(Page page) {
    this.page = page;
    this.userNameInput = page.locator("[data-test='username']");
    this.passwordInput = page.locator("[data-test='password']");
    this.loginButton = page.locator("[data-test='login-button']");
    this.loginErrorLabel = page.locator("[data-test='error']");
    this.loginErrorButton = page.locator("[data-test='error']").getByRole(AriaRole.BUTTON);
  }

  public void navigate() {
    page.navigate(System.getProperty(BASEURL));
    assertEquals(page.title(), "Swag Labs");
  }

  public void login(String username, String password) {
    userNameInput.fill(username);
    passwordInput.fill(password);
    loginButton.click();
  }

  public String getErrorMessage() {
    return loginErrorLabel.textContent();
  }

  public void closeErrorMessage() {
    loginErrorButton.click();
  }
}
