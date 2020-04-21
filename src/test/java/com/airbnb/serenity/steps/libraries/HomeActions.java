package com.airbnb.serenity.steps.libraries;

import com.airbnb.serenity.page_objects.HomePage;
import net.thucydides.core.annotations.Step;

import static com.airbnb.serenity.page_objects.HomePage.CURRENCY_TABLE_LINK;
import static com.airbnb.serenity.page_objects.HomePage.LANGUAGE_AND_CURRENCY_BUTTON;

public class HomeActions
        extends BaseActions {
    private HomePage homePage;

    public void openPage() {
        homePage.open();
    }

    public void setCurrency(String currency) {
        for (int index = 0; index < homePage.listWithCurrencies.size(); index++) {
            if (homePage.listWithCurrencies.get(index).getText().contains(currency)) {
                clicksOn(homePage.listWithCurrencies.get(index));
                return;
            }
        }
    }





}
