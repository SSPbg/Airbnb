package com.airbnb.serenity.page_objects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

import java.util.List;

public class StaysPage
extends PageObject {
    //List of Stays
    public static final String CLASS_OF_STAY = "_8ssblpx";
    @FindBy(css = "a[href][target^=listing_][data-check-info-section=true]")
    public List<WebElementFacade> listOfStays;

    public static final String STAR_WITH_VALUE = "//div[@class='_8ssblpx']//span[@class='_3zgr580'][text()='star_value']";

    public static final By LIST_OF_STAYS = By.className(CLASS_OF_STAY);
    public static final String STARS_CSS = "span[role=img] span[aria-hidden=true]";
    public static final String STARS_XPATH = "following::span[@role='img']/span[@aria-hidden='true']";
    public static final By NEXT_PAGE_ARROW = By.cssSelector("._i66xk8d > a");
    public static By priceForOneNight = By.cssSelector("span._1p7iugi");

}
