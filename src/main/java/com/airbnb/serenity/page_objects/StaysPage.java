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
    @FindBy(className = CLASS_OF_STAY)
    public List<WebElementFacade> listOfStays;

    public static final By STARS_CSS = By.cssSelector("span > span[aria-hidden=true]");
    public static final By STAY_HREF = By.cssSelector("a[href][target^=listing_][data-check-info-section=true]");
    public static final By NEXT_PAGE_ARROW = By.cssSelector("._i66xk8d > a");



}
