package com.airbnb.serenity.page_objects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("https://bg.airbnb.com/rooms/25563767?location=Bli&adults=2&children=1&check_in=2020-04-12&check_out=2020-04-18&source_impression_id=p3_1586373524_A%2Fgzot5WVdJUn49Q")
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

    public static final By GUESTS_LABEL = By.cssSelector("div#GuestPicker-book_it-trigger span");
    public static final By NUMBER_ADULTS_DISPLAYED = By.cssSelector("div[aria-labelledby*=adults] *:not(svg)[aria-hidden=true]");
    public static final By NUMBER_CHILDREN_DISPLAYED = By.cssSelector("div[aria-labelledby*=children] *:not(svg)[aria-hidden=true]");

}
