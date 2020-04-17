package com.airbnb.serenity.steps.libraries;

import com.airbnb.serenity.entities.BookingOptions;
import com.airbnb.serenity.page_objects.ReservePage;
import com.airbnb.serenity.steps.definitions.BookingStepsDefinitions;
import io.vavr.collection.Array;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.JavascriptExecutor;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import static com.airbnb.serenity.page_objects.ReservePage.GUESTS_LABEL;

public class ReserveActions
        extends BaseActions {
    private ReservePage resevrationPage;

    private  NumberFormat numberFormat;

    public void openPage(){
        resevrationPage.open();
    }


    @Step("Assert the dates")
    public void checkDates(BookingOptions options){

        List<WebElementFacade> tripDates =  getAllWebElementFacadeABy(ReservePage.START_OF_TRIP_DATE);
        String startOfTripDate=tripDates.get(0).getText();
        String endOfTripDateDisplayed =  tripDates.get(1).getText();

        LocalDate startDate = convertStringToDate("M/d/yyyy",startOfTripDate);
        LocalDate endDate = convertStringToDate("M/d/yyyy",endOfTripDateDisplayed);


        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(options.getStartDate())
                .as("Start date of the user input has to be equal with displayed:")
                .isEqualTo(startDate);
        softly.assertThat(options.getEndDate())
                .as("The End Date has to be the same as the user input")
                .isEqualTo(endDate);
        softly.assertAll();



    }

    @Step("Assert the number of guests")
    public void checkGuests(BookingOptions options){
        WebElementFacade guestLabel = getWebElementFacadeBy(resevrationPage.GUESTS_LABEL);

        int overallGuests = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(readsTextFrom(guestLabel));
        if (m.find()) {
            System.out.println(m.group());
            overallGuests =Integer.parseInt(m.group());
        }


        clicksOn(GUESTS_LABEL);
        String adultsDisplayed = readsTextFrom(getWebElementFacadeBy(ReservePage.NUMBER_ADULTS_DISPLAYED));
        String kidsDisplayed =readsTextFrom(getWebElementFacadeBy(ReservePage.NUMBER_CHILDREN_DISPLAYED));
        System.out.println("Adults "+adultsDisplayed+" Kids: "+kidsDisplayed);


        SoftAssertions softly = new SoftAssertions();
        softly.assertThat( overallGuests)
                .as("Number of all guests has to be as expected:")
                .isEqualTo(options.getAdults()+options.getKids());
        softly.assertThat(Integer.parseInt(adultsDisplayed))
                .as("Number of all adults has to be as expected:")
                .isEqualTo(options.getAdults());
        softly.assertAll();

        if (guestLabel.isClickable()) {
            clicksOn(GUESTS_LABEL);
        }


    }

    public String getPriceStripedFromCurrencySymbol(String price){
        return price.replace(numberFormat.getCurrency().getSymbol(), StringUtils.EMPTY);
    }

    public int getDiscount(){
        return getSumWithoutTheLast(getPricesList(resevrationPage.discountPrice))+getLastFromTheList(getPricesList(resevrationPage.discountPrice));
        }


    public NumberFormat getNumberFormat(String currency){
        String code = currency.substring(0,2);

        NumberFormat numberFormat;
        if (currency.contentEquals("EUR"))
        {
            numberFormat = new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.GERMANY));
        }
        else {
            Locale localeForCurrency = new Locale("", code);
            numberFormat = new DecimalFormat("#.00", new DecimalFormatSymbols(localeForCurrency));

        }
        return numberFormat;

    }

    public int getFirstFromTheList(List<String>  pricesList){
        System.out.println("Price for a day*days: "+pricesList.get(0));
        if (pricesList.size()>0)  return Integer.parseInt(pricesList.get(0));
                                   else return 0;

    }


    public int getSumWithoutTheLast(List<String>  pricesList){

        int finalPrice =0;
        for (int k=0;k<pricesList.size()-1;k++) finalPrice = finalPrice+Integer.parseInt(pricesList.get(k));

        return finalPrice;
        }

    public int getLastFromTheList(List<String> listPrices){

        if (listPrices.size()>1)
         return Integer.parseInt(listPrices.get(listPrices.size()-1));
        else return 0;

    }


    public List<String> getPricesList(List<WebElementFacade> listOfWebElements) {
      return   listOfWebElements.stream()
                .map( element -> getPriceStripedFromCurrencySymbol(element.getText()))
                .collect(Collectors.toList());
    }

    @Step
    public void checkPrice(BookingOptions options) {

        JavascriptExecutor js = (JavascriptExecutor) resevrationPage.getDriver();
        js.executeScript("return document.readyState").equals("complete");
        js.executeScript("arguments[0].scrollIntoView(true);", resevrationPage.pricePerDay.get(0));


        numberFormat = getNumberFormat(BookingStepsDefinitions.currency);
        int unitPriceDisplayed =Integer.parseInt(getPriceStripedFromCurrencySymbol(readsTextFrom(resevrationPage.pricePerDay.get(0))));

        System.out.println("Currency Symbol " + numberFormat.getCurrency().getSymbol());
        System.out.println("Currency Display Name " + numberFormat.getCurrency().getDisplayName());
        System.out.println("Price for one day displayed: "+unitPriceDisplayed);


        SoftAssertions softly = new SoftAssertions();

        softly.assertThat( getFirstFromTheList(getPricesList(resevrationPage.listPrices)))
                .as("Unit price is as expected:")
                .isEqualTo(options.getDays()*unitPriceDisplayed);
        softly.assertThat(getLastFromTheList(getPricesList(resevrationPage.listPrices)))
                .as("Total price to be as expected:")
                .isEqualTo(getSumWithoutTheLast(getPricesList(resevrationPage.listPrices))+getDiscount());
        softly.assertAll();


    }


}
