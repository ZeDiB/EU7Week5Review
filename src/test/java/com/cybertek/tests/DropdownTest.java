package com.cybertek.tests;

import com.cybertek.utilities.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class DropdownTest {
      /*
     Task3:
    1. Go to:  http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx
    2. Login with username: Tester, password: test
    3. Click  Order button
    4. Verify under Product Information, selected option is “MyMoney”
    5. Then select FamilyAlbum, make quantity 2, and click Calculate,
    6. Then verify Total is equal to Quantity*PricePerUnit
      */

    WebDriver driver;   //declare our reference for the object
    WebDriverWait wait;

    @BeforeMethod
    public void setUp(){
        driver = WebDriverFactory.getDriver("chrome"); //create the object
        driver.manage().window().maximize();
        //implicitly wait,this is going to be applied to whole test cases and element
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx");
    }
    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
       // driver.close();

    }

    @Test
    public void test() throws InterruptedException {
        WebElement userInpuBox = driver.findElement(By.id("ctl00_MainContent_username"));
        userInpuBox.sendKeys("Tester");

        WebElement passwordInputBox = driver.findElement(By.id("ctl00_MainContent_password"));
        passwordInputBox.sendKeys("test"+ Keys.ENTER);

        WebElement orderLink = driver.findElement(By.linkText("Order"));
        orderLink.click();

        String expectedSelectedOption = "MyMoney";
        WebElement productDropDownElement = driver.findElement(By.id("ctl00_MainContent_fmwOrder_ddlProduct"));
        Select productDropdown = new Select(productDropDownElement);
        String actualSelectedOption = productDropdown.getFirstSelectedOption().getText();

        Assert.assertEquals(actualSelectedOption,expectedSelectedOption,"First option selected is Not as expected");

        productDropdown.selectByVisibleText("FamilyAlbum");
        WebElement quantityBox = driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity"));

        quantityBox.sendKeys("2");
        Thread.sleep(2000);
        WebElement calculateButton =driver.findElement(By.cssSelector("input[type='submit']"));
        calculateButton.click();

        int expectedPrice = 160;
        WebElement totalPriceElement = driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtTotal"));

//        System.out.println("totalPriceElement.getAttribute(\"value\") = " + totalPriceElement.getAttribute("value"));
//        System.out.println("totalPriceElement.getAttribute(\"size\") = " + totalPriceElement.getAttribute("size"));

        int actualPrice = Integer.parseInt(totalPriceElement.getAttribute("value"));

        Assert.assertEquals(actualPrice,expectedPrice,"Price is not as expected");


    }





}
