@Selenium
Feature: Table filtering
  As a user I want to be able to filter data in the table

  Scenario Outline: The name match-case checkbox adds case sensitivity to the search results
    Given an open browser
    When user navigates to the main page
    And user selects the main match-case checkbox
    And user inputs "<search_phrase>" into the searchbox
    And user chooses "Name" filter option
    Then only filtered names with "<search_phrase>" are shown with case sensitivity
    Then the number of results is "<occurences>"

    Examples:
      | search_phrase | occurences |
      | Postimex      | 1          |
      | postimex      | 0          |
      | Melbourne     | 0          |

  Scenario Outline: The name filter filters by name
    Given an open browser
    When user navigates to the main page
    And user inputs "<search_phrase>" into the searchbox
    And user chooses "Name" filter option
    Then only filtered names with "<search_phrase>" are shown regardless of case
    Then the number of results is "<occurences>"

    Examples:
      | search_phrase | occurences |
      | Postimex      | 1          |
      | postimex      | 1          |
      | Melbourne     | 0          |

  Scenario Outline: The city filter filters by city
    Given an open browser
    When user navigates to the main page
    And user inputs "<search_phrase>" into the searchbox
    And user chooses "City" filter option
    Then only filtered cities with "<search_phrase>" are shown regardless of case
    Then the number of results is "<occurences>"

    Examples:
      | search_phrase | occurences |
      | Postimex      | 0          |
      | postimex      | 0          |
      | Melbourne     | 1          |