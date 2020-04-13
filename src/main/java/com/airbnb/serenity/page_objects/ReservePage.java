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

@DefaultUrl("https://bg.airbnb.com/rooms/9617736?location=Bali&adults=2&children=1&check_in=2020-04-15&check_out=2020-04-22&source_impression_id=p3_1586507762_lV3DafcSDIApqGSh")
public class ReservePage extends PageObject {

    @FindBy(css = "div._ymq6as span span._pgfqnw")
    public WebElementFacade priceNights;
    //    public static final By PRICE_NIGHTS = By.cssSelector("div._n4om66 span._1p0spma2");
//    public static final By PRICE_NIGHTS = By.cssSelector("._ymq6as span span:nth-of-type(1)");
    public static final By PRICE_NIGHTS = By.cssSelector("div[data-testid='book-it-default'] span[aria-hidden='true']");
//    public static final By PRICE_NIGHTS = By.cssSelector("div[data-testid='book-it-default'] span span:nth-of-type(1)");

    @FindBy(css = "._ra05uc")
    public WebElementFacade sumWithoutTaxesNights;
    public static final By SUM_WITHOUT_TAXES_NIGHTS = By.cssSelector("._ra05uc");

    @FindBy(css = "._adhikmk span._ra05uc")
    public WebElementFacade taxes;
    public static final By TAXES = By.cssSelector("._adhikmk span._ra05uc");

    @FindBy(css = "span._1d3ext9m")
    public WebElementFacade totalPriceWithTaxesNights;
    public static final By TOTAL_PRICE_WITH_TAXES_NIGHTS = By.cssSelector("span._1d3ext9m");

    @FindBy(css = "#GuestPicker-book_it-trigger")
    public WebElementFacade guests;
    public static final By GUESTS = By.cssSelector("#GuestPicker-book_it-trigger");

    @FindBy(css = "#GuestPicker-book_it-form-adults")
    public WebElementFacade adults;
    public static final By ADULTS = By.cssSelector("#GuestPicker-book_it-form-adults");

    @FindBy(css = "#GuestPicker-book_it-form-children")
    public WebElementFacade children;
    public static final By CHILDREN = By.cssSelector("#GuestPicker-book_it-form-children");

    //======================================================================================
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
