# new feature
# Tags: optional

Feature: Booking

  @debug
  Scenario Outline: Successful vacation booking reservation

    Given John is on the popular vacation booking site
    And he select currency "<currency>"

    When John search for a place where to stay in "<place>"
    And he searching for vacation "<days stay>"-days trip after "<offset days>" days
    And he search for number of people to accompany him:
      | adults   | kids   |
      | <adults> | <kids> |

    And John has a requirements for his room:
      | min price   | max price   | bathrooms   | additional   |
      | <min price> | <max price> | <bathrooms> | <additional> |

    And he choose the first with "<stars>" stars

    Then John should see the correct sum according entered data
    And he should see the reservation details in the widget

    Examples:
      | place | offset days | days stay | adults | kids | currency | min price | max price | bathrooms | additional              | stars |
      | Bali  | 5           | 7         | 2      | 1    | EUR      | 50        | 100       | 1         | Air conditioner,Jacuzzi | 5     |
      | Bali  | 5           | 7         | 2      | 1    | EUR      | 50        | 100       | 1         | Air conditioner,Jacuzzi | 5     |
      | Bali  | 5           | 7         | 2      | 1    | EUR      | 50        | 100       | 1         | Air conditioner,Jacuzzi | 5     |
      | Bali  | 5           | 7         | 2      | 1    | EUR      | 50        | 100       | 1         | Air conditioner,Jacuzzi | 5     |
      | Bali  | 5           | 7         | 2      | 1    | EUR      | 50        | 100       | 1         | Air conditioner,Jacuzzi | 5     |
      | Bali  | 5           | 7         | 2      | 1    | EUR      | 50        | 100       | 1         | Air conditioner,Jacuzzi | 5     |
