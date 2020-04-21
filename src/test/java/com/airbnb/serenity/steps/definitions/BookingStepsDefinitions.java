package com.airbnb.serenity.steps.definitions;

import com.airbnb.serenity.entities.BookingOptions;
import com.airbnb.serenity.steps.libraries.BookingActions;

import com.airbnb.serenity.steps.libraries.ShowReservationActions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;


import java.util.List;
import java.util.Map;

import static com.airbnb.serenity.utils.SetBookingOptionsByList.prepareBookingOptionsObject;


public class BookingStepsDefinitions {


    private BookingOptions bookingOptions;
    @Steps
    BookingActions dimo;

    @Steps
    private ShowReservationActions anni;


    @When("^John searches for a place where to stay with the following options:$")
    public void johnSearchForAPlaceWhereToStayWithTheFollowingOptions(List<BookingOptions> data) {
        bookingOptions =data.get(0);
        dimo.startSearchingWithPlace(bookingOptions.getPlace());
        dimo.applyDate(bookingOptions.getStartDate());
        dimo.applyDate(bookingOptions.getEndDate());
        dimo.selectAdditionalGuests(bookingOptions);


    }


    @When("^(?:.*) has additional requirements for his room:$")
    public void userHasARequirementsForHisRoom(List<Map<String, String>> requirements) {
        prepareBookingOptionsObject(bookingOptions, requirements);
        dimo.setPriceRange(bookingOptions.getMinPrice(), bookingOptions.getMaxPrice());
        dimo.selectRoomsAndBeds(bookingOptions.getBathRooms());
        dimo.setAdditionalAmenities(bookingOptions.isAirConditioner(), bookingOptions.isJacuzzi());


    }

    @When("^s?he choose the first with at least (\\d+\\.?\\d*) stars$")
    public void heChooseTheFirstWithStars(Float stars) throws InterruptedException {
        dimo.selectTheFirstStayWithAtLeastGivenStar(stars);

    }

    @Then("^s?he should see his reservation details$")
    public void heShouldSeeTheReservationDetailsInTheWidget() {
        anni.checkDates(bookingOptions);
        anni.checkGuests(bookingOptions);
        anni.checkPrice(bookingOptions);
    }


}