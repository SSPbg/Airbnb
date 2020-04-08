package com.airbnb.serenity.steps.libraries;

import com.airbnb.serenity.entities.BookingOptions;
import com.airbnb.serenity.page_objects.HomePage;
import com.airbnb.serenity.page_objects.MenuWithFilters;
import com.airbnb.serenity.page_objects.StaysPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static com.airbnb.serenity.page_objects.HomePage.*;
import static com.airbnb.serenity.page_objects.MenuWithFilters.ROOMS_AND_BEDS_MENU_ITEM;
import static com.airbnb.serenity.page_objects.StaysPage.STAR_WITH_VALUE;
import static org.assertj.core.api.Assertions.assertThat;



public class BookingActions
        extends BaseActions {
    private HomePage homePage;
    private MenuWithFilters menuWithFilters;
    private StaysPage staysPage;


    public void openPage() {
        homePage.open();
    }

    public void startSearchingWithPlace(String place) {
        //options.getPlace();
        System.out.println(HomePage.WHERE_SEARCH);
        for (int i=0;i<1;i++) {
            try {
                fillsFieldWithData(HomePage.WHERE_SEARCH, place);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                i=0;
            }
        }
        this.currentPage.find(HomePage.WHERE_SEARCH).sendKeys(Keys.ENTER);
    }

    public boolean isTheNextYearNeeded(LocalDate date, By locatorMonthAndYear) {
        String monthAndYearString = readsTextFrom(locatorMonthAndYear);
        if (monthAndYearString.contains(String.valueOf(date.getYear()))) {
            return false;
        }
        return true;
    }

    public boolean isTheNextMonthNeeded(LocalDate date, By locatorMonthAndYear) {
        String monthAndYearString = readsTextFrom(locatorMonthAndYear);
        if (monthAndYearString.contains(formatMonth(date.getMonth()))) {
            return false;
        }
        return true;

    }

    public void applyDate(LocalDate date) {
        while (isTheNextYearNeeded(date, HomePage.CALENDAR_MONTH_NAME))
            clicksOn(HomePage.NEXT_MONTH_BUTTON);

        while (isTheNextMonthNeeded(date, HomePage.CALENDAR_MONTH_NAME))
            clicksOn(HomePage.NEXT_MONTH_BUTTON);

        for (WebElementFacade Day : homePage.tdDatesEnabled) {
            //System.out.println(String.valueOf(Day.getText()));
            if (date.getDayOfMonth() == Integer.parseInt(Day.getText())) {
                clicksOn(Day);
                return;
            }
        }
    }

    public void setCurrency(String currency) {
        for (int index = 0; index < homePage.listWithCurrencies.size(); index++) {
            if (homePage.listWithCurrencies.get(index).getText().contains(currency)) {
                clicksOn(homePage.listWithCurrencies.get(index));
                return;
            }
        }

     }

    public void increaseGuests(By guestLabel, Integer count) {
        for (WebElementFacade guestType : homePage.listTypesOfGuests) {
            if (guestType.thenFindAll(guestLabel).size() == 1) {
                for (int i = 0; i < count; i++) {
                    WebElementFacade increaseGuestsButton = guestType.findBy(HomePage.INCREASE_GUESTS_BTN);
                    clicksOn(increaseGuestsButton);
                }
            }
        }
    }

    public void selectAdditionalGuests(BookingOptions options) {
        clicksOn(HomePage.GUEST_PICKER_BTN);
        increaseGuests(HomePage.ADULTS_LABEL, options.getAdults());
        increaseGuests(HomePage.CHILDRENS_LABEL, options.getKids());
        clicksOn(HomePage.SAVE_GUESTS_BTN);
        clicksOn(HomePage.SUBMIT_BUTTON);
    }

    public void setPriceRange(BigDecimal minPrice,BigDecimal maxPrice){
        clicksOn(MenuWithFilters.PRICE_RANGE_MENU_BUTTON);
        setElementInVisibleScreen(menuWithFilters.priceFilterMin);
        fillsFieldWithData(menuWithFilters.priceFilterMin,minPrice.toString());
        fillsFieldWithData(menuWithFilters.priceFilterMax,maxPrice.toString());
        clicksOn(menuWithFilters.filterPanelSaveButton);

    }

    public void selectRoomsAndBeds(int bathrooms){
       boolean singleMenuItem=false;
        if (currentPage.findAll(ROOMS_AND_BEDS_MENU_ITEM).size()==0){
            clicksOn(MenuWithFilters.MORE_FILTERS_MENU_ITEM);
        }
        else {
            singleMenuItem=true;
            clicksOn(ROOMS_AND_BEDS_MENU_ITEM);
        }
        clicksOn(menuWithFilters.plusBathRoomButton);
        if (singleMenuItem) clicksOn(menuWithFilters.filterPanelSaveButton);
    }

    public void setAdditionalAmenities(boolean isAirConditioner,boolean isJacuzzi){
        if (isAirConditioner){
            clicksOn(menuWithFilters.airConditionerLabel);
        }

        if (isJacuzzi){
            clicksOn(menuWithFilters.jaccuziLabel);
        }
        clicksOn(menuWithFilters.showMoreThanThreeThousandPlacesButton);

    }




    public By returnStarsByStayNumberInPage(int number){
        int nthElement=number+1;
        By StarsBy = By.cssSelector("."+StaysPage.CLASS_OF_STAY+":nth-of-type("+String.valueOf(nthElement)+") "+StaysPage.STARS_CSS);
        return StarsBy;
    }

    public By returnLinkOFStayByStayNumberInPage(int number){
        int nthElement=number+1;
        By linkBy = By.cssSelector("."+StaysPage.CLASS_OF_STAY+":nth-of-type("+String.valueOf(nthElement)+") a");
        return linkBy;
    }

    public By returnStayByStayNumberInPage(int number){
        int nthElement=number+1;
        By linkBy = By.cssSelector("."+StaysPage.CLASS_OF_STAY+":nth-of-type("+String.valueOf(nthElement)+")");
        return linkBy;
    }

    public By prepareByForGivenStars(String stars){
         String byLocatorString = STAR_WITH_VALUE.replace("star_value",stars );
         By starsBy = By.xpath(byLocatorString);
        return starsBy;
    }

    @Step("The Stay with {0} Stars or above not found with this search criteria")
    public void elementNotFound(float stars){
        System.out.println("The element with stars >= "+String.valueOf(stars)+" not found");
    }

    public void selectTheFirstStayWithAtLeastGivenStar(Float stars) throws InterruptedException {


        boolean nextPageExists = true;
         int i=0;
         while (nextPageExists) {
         i=0;

         List<WebElementFacade> Stays= new ArrayList();
         for (int n=0;n<10
                 ;n++) {
             try {
                  Stays = staysPage.listOfStays;

             } catch (Exception e) {
                 System.out.println(e.getMessage());
             }
         }
          while (i<Stays.size()) {
             // setElementInVisibleScreen(Stay);
              WebElementFacade   star1 =null;
              String startText="1";
              for (int n=0;n<3;n++) {
                  try {
                        star1 = (WebElementFacade) Stays.get(i).findBy(By.xpath(".//span[@class='_3zgr580']"));
                       startText=star1.getText();
                      //List<WebElementFacade> starsList = getAllWebElementFacadeABy(returnStarsByStayNumberInPage(i));
                  } catch (Exception e) {

                  }
              }
              if (star1 != null) {
                  //WebElementFacade star = starsList.get(0);

                  float starValue = Float.parseFloat(startText);
                  System.out.println("Star value "+starValue);
                  if (starValue >= stars) {
                     WebElementFacade link = getWebElementFacadeBy(returnLinkOFStayByStayNumberInPage(i));
                      System.out.println(link.getAttribute("href"));
                      goToURL(link.getAttribute("href"));
                      return;
                  }
              }
              i++;
          }
            WebElementFacade nextArrow =getWebElementFacadeBy(StaysPage.NEXT_PAGE_ARROW);
          if (nextArrow.isPresent()) {

              clicksOn(StaysPage.NEXT_PAGE_ARROW);


          } else {
              nextPageExists = false;
              elementNotFound(stars);

          }
      }
    }

    public void checkTheCalendarsDaysWithBlack(BookingOptions options){
        int startDayTrip = options.getDayStartTrip();
        String startTripMonth = formatMonth(options.getStartOfTripMonth());
        int endDayTrip = options.getDayEndTrip();
        String endTripMonth = formatMonth(options.getEndOfTripMonth());
        assertThat(getWebElementFacadeBy(CALENDARS_TIME_PERIOD).getText())
                .as("The start month has to be listed")
                .containsIgnoringCase(startTripMonth);



    }

    public void checkPrice(){

        currentPage.waitForRenderedElementsToBePresent(By.xpath("//div[@class='_80f7zz']//span[@class='_pgfqnw'] | //div[@class='_n4om66']//span[@class='_doc79r']"));
        String priceDisplayed =  readsTextFrom(By.xpath("//div[@class='_80f7zz']//span[@class='_pgfqnw'] | //div[@class='_n4om66']//span[@class='_doc79r']"));

        NumberFormat numberFormat = new DecimalFormat("¤#.00", new DecimalFormatSymbols(Locale.GERMANY));

       // NumberFormat number = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        System.out.println("Currency Symbol "+numberFormat.getCurrency().getSymbol());
        System.out.println("Currency Display Name "+numberFormat.getCurrency().getDisplayName());
        Number num=0;
        try {
            num=numberFormat.parse(priceDisplayed);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't convert " + priceDisplayed + " to Double!");
        }
        System.out.println(num);
    }
}
