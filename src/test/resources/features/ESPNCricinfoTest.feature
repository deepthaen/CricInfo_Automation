Feature: Testing various functionalities on the ESPN Cricinfo website

  Scenario: Verify that the homepage loads successfully
    Given the browser is open
    When I navigate to the ESPN Cricinfo homepage
    Then the homepage should load with all elements visible
    Then I close the browser

  Scenario Outline: Verify the display of live scores menu for different match types
    Given the browser is open
    When I navigate to the <type> section
    Then the live scores should be visible and updated in real-time
    Then I close the browser
    Examples:
      | type |
      | "Live Cricket Score" |

  Scenario Outline: Verify search functionality for a player
    Given the browser is open
    When I search for a player by name <playerName>
    Then the relevant player profile should appear in the search results
    Then I close the browser
    Examples:
      | playerName       |
      | "Virat Kohli"    |
      | "Joe Root"       |
      | "Kane Williamson"|

  Scenario: Verify navigation to "Teams" section
    Given the browser is open
    When I navigate to the "Cricket Teams" section
    Then the "Teams" page should load successfully with team details
    Then I close the browser
#
#  Scenario: Verify the stats section displays correct data
#    Given the browser is open
#    When I navigate to the "Stats" section
#    Then the player/team statistics should be displayed accurately
#
#  Scenario Outline: Verify login functionality for users with different credentials
#    Given a user has valid login credentials
#    When the user logs in with username "<username>" and password "<password>"
#    Then the user should be logged in successfully
#    Examples:
#      | username    | password    |
#      | "user1"     | "password1" |
#      | "user2"     | "password2" |
#      | "user3"     | "password3" |
#
#  Scenario: Verify website's mobile responsiveness
#    Given the website is opened on a mobile device or emulator
#    When I check the layout and responsiveness
#    Then the site should adapt and display correctly on mobile devices
#
#  Scenario: Verify the advertisement display on the homepage
#    Given the browser is open
#    When I verify the advertisement display
#    Then the ads should be displayed correctly without obstructing content
#
#  Scenario: Verify the website’s performance under load
#    Given the load test tool is configured
#    When 100+ users visit the website
#    Then the website should respond within acceptable limits under heavy load
#
#  Scenario: Verify match information display for an ongoing match
#    Given a match is ongoing
#    When I navigate to the match information page
#    Then the match details should be accurate and displayed without issues
