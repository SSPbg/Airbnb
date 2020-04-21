package com.airbnb.serenity.page_objects;

//import net.serenitybdd.core.annotations.findby.FindBy;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import javax.accessibility.AccessibleStateSet;
import java.util.List;

public class ShowReservationPage extends PageObject {

    public static final By START_OF_TRIP_DATE = By.xpath("//div[@class='_1yy0mt98'] | //div[@class='_ykxqsm']");


    public static final By GUESTS_LABEL = By.xpath("//div[@class='guest-label']//span | //label[@for='GuestPicker-book_it-trigger']//span");
    public static final By GUESTS_LABEL_2 = By.cssSelector("label span:not([class])");
    public static final By GUESTS_LABEL_3 = By.xpath("//label//span");

    public static final By forLabel = By.cssSelector("*[for=]");

    public static final By SCROOL_TO_ELEMENT = By.cssSelector("div[data-plugin-in-point-id=DESCRIPTION_DEFAULT] section button");
    public static final By DATA_TEST_ID = By.cssSelector("div[data-testid=book-it-default]");
    public static final By NUMBER_ADULTS_DISPLAYED = By.cssSelector("div[aria-labelledby*=adults] *:not(svg)[aria-hidden=true]");
    public static final By NUMBER_CHILDREN_DISPLAYED = By.cssSelector("div[aria-labelledby*=children] *:not(svg)[aria-hidden=true]");

    @FindAll({
            @FindBy(css = "form#book_it_form span span[class=_j1kt73]"),
            @FindBy(css = "[data-plugin-in-point-id=BOOK_IT_SIDEBAR] li>span:nth-of-type(2)") //_ra05uc+ _l1ngr4-
    })
    public List<WebElementFacade> listPrices;

    @FindAll({
            @FindBy(css = "div[data-plugin-in-point-id=BOOK_IT_SIDEBAR] div[data-testid=book-it-default] span>span:not([style]):first-of-type"),
            @FindBy(css = "span span[class=_doc79r]"), //_ra05uc+ _l1ngr4-
            @FindBy (css= "span[class=_1p0spma2] span")
    })
    public List<WebElementFacade> pricePerDay;

    @FindAll({
            @FindBy(css = "form#book_it_form span span div[aria-hidden=true]")
    })
    public List<WebElementFacade> discountPrice;
}
