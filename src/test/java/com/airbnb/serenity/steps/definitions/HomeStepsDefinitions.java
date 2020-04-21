package com.airbnb.serenity.steps.definitions;

import com.airbnb.serenity.steps.libraries.ShowReservationActions;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import net.thucydides.core.annotations.Steps;
import com.airbnb.serenity.steps.libraries.HomeActions;

import static com.airbnb.serenity.page_objects.HomePage.CURRENCY_TABLE_LINK;
import static com.airbnb.serenity.page_objects.HomePage.LANGUAGE_AND_CURRENCY_BUTTON;


public class HomeStepsDefinitions {
    @Steps
    HomeActions me;


    @Given("^(?:.*) is on home page of the popular booking site$")
    public void johnIsOnTheHomePage() {
        me.openPage();
    }

    @Before("@eur_currency")
    public void initEURCurrency(){
        me.openPage();
        me.clicksOn(LANGUAGE_AND_CURRENCY_BUTTON);
        me.clicksOn(CURRENCY_TABLE_LINK);
        ShowReservationActions.currency = "EUR";
        me.setCurrency(ShowReservationActions.currency);
    }

}
