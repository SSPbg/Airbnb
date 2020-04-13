package com.airbnb.serenity.steps.libraries;

import com.airbnb.serenity.entities.BookingOptions;
import com.airbnb.serenity.page_objects.ReservePage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.JavascriptExecutor;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.airbnb.serenity.page_objects.ReservePage.GUESTS_LABEL;

public class ReserveActions
        extends BaseActions {
    private ReservePage resevrationPage;
    public void openPage(){
        resevrationPage.open();
    }


    @Step("Assert the dates")
    public void checkDates(BookingOptions options){

        List<WebElementFacade> tripDates =  getAllWebElementFacadeABy(ReservePage.START_OF_TRIP_DATE);
        String startOfTripDate=tripDates.get(0).getText();
        System.out.println("The start Of trip date on the Reservation Page is: "+tripDates.get(0).getText());

        String endOfTripDateDisplayed =  tripDates.get(1).getText();
        System.out.println("The End Of trip date on the Reservation Page is: "+tripDates.get(1).getText());

        LocalDate startDate = convertStringToDate("M/d/yyyy",startOfTripDate);
        LocalDate endDate = convertStringToDate("M/d/yyyy",endOfTripDateDisplayed);
        //"yyyy-MM-dd"

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

        /*
        JavascriptExecutor js = (JavascriptExecutor) resevrationPage.getDriver();
        js.executeScript("return document.readyState").equals("complete");

        js.executeScript("arguments[0].scrollIntoView(true);", resevrationPage.find(By.cssSelector("[data-testid=book-it-default]")));
        //wait.until(ExpectedConditions.presenceOfElementLocated(resevrationPage.find(By.cssSelector("[data-testid=book-it-default]"))));
        js.executeScript("return arguments[0].value", resevrationPage.find(By.cssSelector("input[type=hidden][name=number_of_guests]")));
        js.executeScript("return arguments[0].value", resevrationPage.find(By.cssSelector("input[type=hidden][name=number_of_adults]")));
        js.executeScript("return arguments[0].value", resevrationPage.find(By.cssSelector("input[type=hidden][name=number_of_adults]")));
      */
        WebElementFacade guestLabel = null;
        guestLabel = getWebElementFacadeBy(resevrationPage.GUESTS_LABEL);

        System.out.println("Overall guests: "+guestLabel.getText());

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
         int overallGuestsExpetcted =options.getAdults()+options.getKids();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat( overallGuests)
                .as("Number of all guests has to be as expected:")
                .isEqualTo(overallGuestsExpetcted);
        softly.assertThat(Integer.parseInt(adultsDisplayed))
                .as("Number of all adults has to be as expected:")
                .isEqualTo(options.getAdults());
        softly.assertAll();

        if (guestLabel.isClickable()) {
            clicksOn(GUESTS_LABEL);
        }


    }

    public String getPriceStripedFromCurrencySymbol(String price,String Symbol){
        return price.replace(Symbol, StringUtils.EMPTY);
    }

    @Step
    public void checkPrice(BookingOptions options) {

        //currentPage.waitForRenderedElementsToBePresent(By.xpath("//div[@class='_80f7zz']//span[@class='_pgfqnw'] | //div[@class='_n4om66']//span[@class='_doc79r']"));
        //String priceDisplayed = readsTextFrom(By.xpath("//div[@class='_80f7zz']//span[@class='_pgfqnw'] | //div[@class='_n4om66']//span[@class='_doc79r']"));
        JavascriptExecutor js = (JavascriptExecutor) resevrationPage.getDriver();
        js.executeScript("return document.readyState").equals("complete");
        resevrationPage.waitFor(resevrationPage.pricePerDay.get(0));
        js.executeScript("arguments[0].scrollIntoView(true);", resevrationPage.pricePerDay.get(0));

        String priceDisplayed = readsTextFrom(resevrationPage.pricePerDay.get(0));

        String code = options.getCurrency().substring(0,2);

        NumberFormat numberFormat;
        if (options.getCurrency().contentEquals("EUR"))
        {
            numberFormat = new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.GERMANY));
        }
        else {
            Locale localeForCurrency = new Locale("", code);
            numberFormat = new DecimalFormat("#.00", new DecimalFormatSymbols(localeForCurrency));

        }
        priceDisplayed = getPriceStripedFromCurrencySymbol(priceDisplayed,numberFormat.getCurrency().getSymbol());

        // NumberFormat number = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        System.out.println("Currency Symbol " + numberFormat.getCurrency().getSymbol());
        System.out.println("Currency Display Name " + numberFormat.getCurrency().getDisplayName());

        int unitPriceDisplayed = converToPrice(priceDisplayed,numberFormat);
        System.out.println("Price for one day displayed: "+unitPriceDisplayed);


        int row=1;
        int priceForAllDays =0;
        int finalPrice=0;
        int totalPriceDisplayed = 0;

        for (WebElementFacade priceElement:resevrationPage.listPrices) {
           String priceOnTheRow  = getPriceStripedFromCurrencySymbol(priceElement.getText(),numberFormat.getCurrency().getSymbol());
           int priceOnTheRowInt =  converToPrice(priceOnTheRow,numberFormat);


        }

        System.out.println("Base price for the days:"+priceForAllDays);
        System.out.println("Final price for the days:"+finalPrice);
        System.out.println("Total price displayed: "+totalPriceDisplayed);
    }


}
