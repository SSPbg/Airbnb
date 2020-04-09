package com.airbnb.serenity.steps.definitions;

import com.airbnb.serenity.entities.BookingOptions;
import com.airbnb.serenity.page_objects.ReservePage;
import com.airbnb.serenity.steps.libraries.BookingActions;

import com.airbnb.serenity.steps.libraries.ReserveActions;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertions;

import static com.airbnb.serenity.page_objects.HomePage.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.airbnb.serenity.page_objects.ReservePage.*;
import static com.airbnb.serenity.utils.SetBookingOptionsByList.prepareBookingOptionsObject;


public class BookingStepsDefinitions {

    private double pricePerNight;
    private BookingOptions bookingOptions;
    @Steps
    BookingActions dimo;

    @Steps
    private ReserveActions reserveActions;
    @Steps
    private ReservePage reservePage;


    @Given("^(?:.*) is on the popular vacation booking site$")
    public void johnIsOnTheHomePage() {
        dimo.openPage();
    }

    @Given("^s?he select english language plus currency \"([^\"]*)\"$")
    public void heSelectCurrencyEuro(String currency) {
        dimo.clicksOn(LANGUAGE_AND_CURRENCY_BUTTON);
        dimo.clicksOn(CURRENCY_TABLE_LINK);
        dimo.setCurrency(currency);
    }


    @When("^(?:.*) search for a place where to stay in \"([^\"]*)\"$")
    public void heSearchForAPlaceWhereToStayIn(String place) {
        bookingOptions = new BookingOptions();
        bookingOptions.setPlace(place);

        dimo.startSearchingWithPlace(bookingOptions.getPlace());
    }

    @When("^s?he searching for vacation \"([^\"]*)\"-days trip after \"([^\"]*)\" days$")
    public void heSearchingForVacationDaysTripAfterDays(Integer howLong, Integer daysFromNow) {
        bookingOptions.setStartDate(daysFromNow);
        bookingOptions.setEndDate(daysFromNow, howLong);
        dimo.applyDate(bookingOptions.getStartDate());
        dimo.applyDate(bookingOptions.getEndDate());
    }


    @When("^s?he search for number of people to accompany him:$")
    public void heSearchForNumberOfPeopleToAccompanyHim(List<BookingOptions> guests) { //List<Map<String,Integer>>
        bookingOptions.setKids(guests.get(0).getKids());
        bookingOptions.setAdults(guests.get(0).getAdults());

        dimo.selectAdditionalGuests(bookingOptions);


    }

    @When("^(?:.*) has a requirements for his room:$")
    public void userHasARequirementsForHisRoom(List<Map<String, String>> requirements) {
        prepareBookingOptionsObject(bookingOptions, requirements);
        dimo.setPriceRange(bookingOptions.getMinPrice(), bookingOptions.getMaxPrice());
        dimo.selectRoomsAndBeds(bookingOptions.getBathRooms());
        dimo.setAdditionalAmenities(bookingOptions.isAirConditioner(),bookingOptions.isJacuzzi());


    }

    @When("^s?he choose the first with \"([^\"]*)\" stars$")
    public void heChooseTheFirstWithStars(Float stars) throws InterruptedException {
        dimo.selectTheFirstStayWithAtLeastGivenStar(stars);

    }



    @Then("^he should see the reservation details in the widget$")
    public void heShouldSeeTheReservationDetailsInTheWidget() {
        //dimo.checkTheCalendarsDaysWithBlack(bookingOptions);
    }


    @Then("^(?:.*) should see the correct sum according entered data$")
    public void userShouldSeeTheCorrectSumAccordingEnteredData() {
        SoftAssertions softly = new SoftAssertions();
        dimo.checkPrice();
//        pricePerNight = reserveActions.readsDoubleFrom(PRICE_NIGHTS);
//        System.out.println(pricePerNight);

//        softly.assertThat(reserveActions.readsTextFrom(PRICE_NIGHTS))
//                .as("Price for 1 night")
//                .contains(String.valueOf(pricePerNight));

/*        softly.assertThat(reserveActions.readsTextFrom(ADULTS))
                .as("Read text from adults")
                .isEqualTo(this.bookingOptions.getAdults());

        softly.assertThat(reserveActions.readsTextFrom(CHILDREN))
                .as("Read text from children")
                .isEqualTo(this.bookingOptions.getKids());
        softly.assertThat(reserveActions.readsNumericValueFrom(TAXES))
                .as("Price for taxes")
                .isEqualTo(this.bookingOptions.getTaxes());

        softly.assertThat(reserveActions.readsNumericValueFrom(TOTAL_PRICE_WITH_TAXES_NIGHTS))
                .as("Total price with taxes")
                .isEqualTo(this.bookingOptions.getTotalPrice());*/
        softly.assertAll();
    }
}