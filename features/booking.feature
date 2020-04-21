# new feature
# Tags: optional

Feature: Booking

  @eur_currency
  Scenario: Successful booking reservation
    Given John is on home page of the popular booking site
    When John searches for a place where to stay with the following options:
      | place | daysFromNow | days | adults | kids |
      | Bali  | 5           | 7    | 2      | 1    |
    And John has additional requirements for his room:
      | min price | max price | bathrooms | additional              |
      | 50        | 100       | 1         | Air conditioner,Jacuzzi |
    And he choose the first with at least 5 stars
    Then he should see his reservation details

