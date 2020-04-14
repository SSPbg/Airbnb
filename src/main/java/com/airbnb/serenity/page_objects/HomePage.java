package com.airbnb.serenity.page_objects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

import javax.swing.text.html.CSS;
import java.util.List;

@DefaultUrl("/")
public class HomePage
        extends PageObject {
    //Select localization currency EUR
    public static final By LANGUAGE_AND_CURRENCY_BUTTON = By.cssSelector("button._547li01");
    public static final By CURRENCY_TABLE_LINK = By.cssSelector("div#simple-header-locale-menu a[href$=currency]");


    @FindBy (css = "ul._19s389u li button")
    public List<WebElementFacade> listWithCurrencies;


    //Search criteria controls
    public static final By SEARCH_FOR_CITY = By.cssSelector("input[name=query][aria-autocomplete=list]");
    public static final By DIALOG = By.cssSelector("div[role=dialog]");

    public static final By CALENDAR_MONTH_NAME = By.cssSelector("div[data-visible=true] ._gucugi div");
    public static final By NEXT_MONTH_BUTTON = By.cssSelector("div[role=button]:nth-of-type(2) button[aria-label]");

    @FindBy(css = "div[class=_1svux14][data-visible=true] table td[aria-disabled=false]")
    public List<WebElementFacade> tdDatesEnabled;


    public static final By GUEST_PICKER_BTN = By.cssSelector("button:not([aria-label])[aria-expanded=false][type=button]");


    public static final By INCREASE_GUESTS_ADULTS = By.cssSelector("button[aria-describedby='searchFlow-subtitle-label-stepper-adults']:nth-of-type(2)");
   public static final By INCREASE_GUESTS_KIDS = By.cssSelector("button[aria-describedby='searchFlow-subtitle-label-stepper-children']:nth-of-type(2)");


    public static final By SUBMIT_BUTTON = By.cssSelector("button[type=submit]");



}
