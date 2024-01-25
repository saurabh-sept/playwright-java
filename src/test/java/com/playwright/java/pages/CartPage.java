package com.playwright.java.pages;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CartPage {
    private static final String BASEURL = "baseURL";
    private final Page page;
    private final Locator menuButton;        
    private final Locator cartButton;    

  public CartPage(Page page) {
    this.page = page;
    this.menuButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
    .setName(Pattern.compile("Open Menu")));
    this.cartButton = page.locator("#shopping_cart_container a");
  }

  public void navigate() {
    page.navigate(System.getProperty(BASEURL) + "cart.html");
    assertEquals(page.title(), "Swag Labs");
  }
  
  public void openMenu(){
    menuButton.click();
  }
  
  public void gotoCart(){
    cartButton.click();
  }

  public int getTotalItemsCount() {
    String xpath = "//div[@class='cart_item']";
     return page.locator(xpath).count();
  }

  public List<String> getAllItemNames() {
    String xpath = "//div[@class=\"cart_item\"]//div[@class=\"inventory_item_name \"]";
     return page.locator(xpath).allTextContents();
  }

  public void openItemByOrder(int order) {
    String xpath = "//div[@class='cart_item'][position()="+order+"]//div[@class='inventory_item_name ']";
    page.waitForCondition(() -> page.locator(xpath).isVisible());
    page.locator(xpath).click();
  }

  public String getItemNameByOrder(int order) {
    String xpath = "//div[@class='cart_item'][position()="+order+"]//div[@class='inventory_item_name ']";
    page.waitForCondition(() -> page.locator(xpath).isVisible());
    return page.locator(xpath).innerText();
  }

  public String getItemDescByOrder(int order) {
    String xpath = "//div[@class='cart_item'][position()="+order+"]//div[@class='inventory_item_desc']";
    page.waitForCondition(() -> page.locator(xpath).isVisible());
    return page.locator(xpath).innerText();
  }

  public String getItemPriceByOrder(int order) {
    String xpath = "//div[@class='cart_item'][position()="+order+"]//div[@class='inventory_item_price']";
    page.waitForCondition(() -> page.locator(xpath).isVisible());
    return page.locator(xpath).innerText();
  }

  public void removeItemByOrder(int order) {
    String xpath = "//div[@class='cart_item'][position()="+order+"]//button[text()='Add to cart']";
    page.waitForCondition(() -> page.locator(xpath).isVisible());
    page.locator(xpath).click();
  }

  public void removeItemByName(String name) {
    String xpath = "//div[@class='cart_item' and .//div[text()='"+name+"']]//button";
    page.waitForCondition(() -> page.locator(xpath).isVisible());
    page.locator(xpath).click();
  }
}