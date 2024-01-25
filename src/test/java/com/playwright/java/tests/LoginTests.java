package com.playwright.java.tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.playwright.java.pages.LoginPage;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.HashMap;

public class LoginTests extends BaseTest
{ 
    public HashMap<LoginPage,HashMap<BrowserContext,Page>> loadAndLoginSetter(String username, String password){        
        final BrowserContext context = this.browserManager.getBrowserContext();
        final Page page = this.browserManager.getPage();
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login(username, password);

        HashMap<BrowserContext,Page> bContext = new HashMap<BrowserContext,Page>();
        bContext.put(context, page);

        HashMap<LoginPage,HashMap<BrowserContext,Page>> fullContext = new HashMap<LoginPage,HashMap<BrowserContext,Page>>();
        fullContext.put(loginPage,bContext);

        return fullContext;
    }

    @DataProvider(name="validUsers")
    public Object[][] validUsersFeed(){
        Object [][] userData=new Object[5][2];
        userData[0][0]="standard_user";           userData[0][1]="secret_sauce";
        userData[1][0]="problem_user";            userData[1][1]="secret_sauce";
        userData[2][0]="performance_glitch_user"; userData[2][1]="secret_sauce";
        userData[3][0]="error_user";              userData[3][1]="secret_sauce";
        userData[4][0]="visual_user";             userData[4][1]="secret_sauce";
        return userData;
    }
    @Test(dataProvider="validUsers")
    public void login_validUsers_successfully(String username,String password) {
        final HashMap<LoginPage,HashMap<BrowserContext,Page>> context = loadAndLoginSetter(username, password);

        for ( LoginPage loginKey : context.keySet() ) {
            for ( BrowserContext browserKey : context.get(loginKey).keySet() ) {
                browserKey.onPage(newPage -> {
                    newPage.waitForLoadState();
                });
                assertEquals(context.get(loginKey).get(browserKey).url(), "https://www.saucedemo.com/inventory.html");
            }
        }
    }

    @DataProvider(name="lockedUsers")
    public Object[][] lockedUsersFeed(){
        Object [][] userData=new Object[1][2];
        userData[0][0]="locked_out_user"; userData[0][1]="secret_sauce";
        return userData;
    }
    @Test(dataProvider="lockedUsers")
    public void login_lockedUsers_getError(String username,String password) {
        final HashMap<LoginPage,HashMap<BrowserContext,Page>> context = loadAndLoginSetter(username, password);

        for ( LoginPage loginKey : context.keySet() ) {
            for ( BrowserContext browserKey : context.get(loginKey).keySet() ) {
                browserKey.onPage(newPage -> {
                    newPage.waitForLoadState();
                });
                assertNotEquals(context.get(loginKey).get(browserKey).url(), "https://www.saucedemo.com/inventory.html");
                assertEquals(loginKey.getErrorMessage(), "Epic sadface: Sorry, this user has been locked out.");
            }
        }
    }
}
