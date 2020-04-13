package com.airbnb.serenity.steps.libraries;

import com.airbnb.serenity.page_objects.BasePage;
import com.airbnb.serenity.page_objects.HomePage;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.By;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.interactions.Actions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.airbnb.serenity.page_objects.StaysPage.NEXT_PAGE_ARROW;

public class BaseActions {
    protected BasePage currentPage;

    public void navigateToURL() {
        currentPage.open();
    }

    @Step("Enters '{1}' in field {0}")
    protected void fillsFieldWithData(WebElementFacade fieldElement,
                                      String data) {
        if (data != null) {
            fieldElement.waitUntilEnabled().waitUntilVisible()
                    .type(data);
        }
    }

    @Step("Selects '{1}' item from drop down {0}")
    protected void selectsFromDropDownAnItemByValue(WebElementFacade dropDownElement,
                                                    String itemValue) {
        if (itemValue != null) {
            dropDownElement.selectByValue(itemValue);
        }
    }

    protected void fillsFieldWithData(By fieldElement,
                                      String data) {
        fillsFieldWithData((WebElementFacade) currentPage.find(fieldElement),
                data);
    }

    protected void selectsFromDropDownAnItemByValue(By dropDownLocator,
                                                    String itemValue) {
        this.selectsFromDropDownAnItemByValue((WebElementFacade) currentPage.find(dropDownLocator),
                itemValue);
    }

    protected void selectsFromDropDownAnItemByVisibleText(By dropDownLocator,
                                                          String itemValue) {
        this.selectsFromDropDownAnItemByVisibleText((WebElementFacade) currentPage.find(dropDownLocator),
                itemValue);
    }

    @Step("Selects '{1}' item from drop down {0}")
    protected void selectsFromDropDownAnItemByVisibleText(WebElementFacade dropDownElement,
                                                          String itemValue) {
        if (itemValue != null) {
            dropDownElement.selectByVisibleText(itemValue);
        }
    }

    @Step
    protected void clicksOn(WebElementFacade buttonOrLink) {
        buttonOrLink.waitUntilClickable()
                .click();
    }

    @Step
    public boolean canSeeElement(WebElementFacade webElement) {
        return webElement.isVisible();
    }


//    @Step("Read the text from Web Element")
    public String readsTextFrom(WebElementFacade webElement) {
        return webElement.waitUntilVisible()
                .waitUntilPresent()
                .getText()
                .trim();
    }

    @Step("Clicks on Web Element")
    public void clicksOn(final By locator) {
        currentPage.find(locator)
                .waitUntilClickable()
                .waitUntilEnabled()
                .click();
    }

    public String readsTextFrom(By locator) {
        return readsTextFrom((WebElementFacade) currentPage.find(locator).waitUntilPresent().waitUntilVisible());
    }

    @Step("Reads the text from Web Element")
    public List<String> readsTextFromList(By listItemsLocator) {
        List<WebElementFacade> errorsItemsElements = currentPage.findAll(listItemsLocator);
        List<String> errorMessages = new ArrayList<>(errorsItemsElements.size());

        for (WebElementFacade item : errorsItemsElements) {
            errorMessages.add(item.getText()
                    .trim());
        }
        return errorMessages;
    }


    public String getValueFrom(By locator) {
        return getValueFrom((WebElementFacade) currentPage.find(locator));
    }

    @Step("Gets the value from Web Element")
    public String getValueFrom(WebElementFacade webElement) {
        return webElement
                .waitUntilVisible()
                .getValue();
    }

    public Number getPriceFromCurrency(By locator) {
        String numericText = readsTextFrom((WebElementFacade) currentPage.find(locator));
        NumberFormat number = NumberFormat.getCurrencyInstance(Locale.US);

        try {
            return number.parse(numericText);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't convert " + numericText + " to Double!");
        }

    }

    public double readsDoubleFrom(By locator) {
        String numericText = readsTextFrom((WebElementFacade) currentPage.find(locator));
        NumberFormat number = NumberFormat.getCurrencyInstance(Locale.GERMANY);

        try {
            return number.parse(numericText).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't convert " + numericText + " to Double!");
        }
    }

    public int readsIntegerFrom(By locator) {
        String numericText = readsTextFrom((WebElementFacade) currentPage.find(locator));
        NumberFormat number = NumberFormat.getCurrencyInstance(Locale.GERMANY);

        try {
            return number.parse(numericText).intValue();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't convert " + numericText + " to Double!");
        }
    }

    public void setElementInVisibleScreen(By locator) {

        WebElementFacade element = (WebElementFacade) currentPage.find(locator);
        Actions act = new Actions(currentPage.getDriver());
        act.moveToElement(element).build().perform();
    }

    public void moveToElementAndClicksOnXY(WebElementFacade element,Integer xOffset,Integer yOffset) {

        Actions act = new Actions(currentPage.getDriver());
        act.moveToElement(element,xOffset,yOffset).click().build().perform();

    }

    public void setElementInVisibleScreen(WebElementFacade element) {
        Actions act = new Actions(currentPage.getDriver());
        act.moveToElement(element).build().perform();
    }

    public WebElementFacade returnParentWithGivenTag(WebElementFacade child,String parentTag){
        By locatorParent = new By.ByXPath("./ancestor::"+parentTag+"[last()]");
        return  child.find(locatorParent);
    }

    public Float parseStringToFloat(String str) {
        return Float.valueOf(str);
    }

    public String formatMonth(Month mon) {
        return mon.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public void goToURL(String url){
        currentPage.getDriver().navigate().to(url);
    }

    public List<WebElementFacade> getAllWebElementFacadeABy(By locator){
        return currentPage.findAll(locator);
    }

    public WebElementFacade getWebElementFacadeBy(By locator){
        currentPage.find(locator).waitUntilVisible().waitUntilEnabled();
        return  currentPage.find(locator);
    }

    @Step("")
    public int readsNumericValueFrom(By locator) {
        String numericText = currentPage.find(locator).getValue();
        return Integer.parseInt(numericText);
    }

    public LocalDate convertStringToDate(String format, String dateInString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);


            LocalDate  date = LocalDate.parse(dateInString,formatter);
            System.out.println(date);
            return date;
          //  System.out.println("time zone : " + TimeZone.getDefault().getID());
          //  System.out.println(formatter.format(date));



    }

    public static int converToPrice(String displayedPrice, NumberFormat numberFormat){
        Number num = 0;
        try {
            num = numberFormat.parse(displayedPrice);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't convert " + displayedPrice + " to Double!");
        }
       // System.out.println(num);

        return num.intValue();
    }
}
