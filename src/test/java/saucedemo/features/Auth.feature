Feature: Authentication

  @Positive @Auth
  Scenario Outline: Verify Success User Login
    Given User on Login Pages Saucedemo
    When User fills valid <username> as username and <password> as password
    And User click on Login button
    Then User redirect to Dashboard Page

    Examples:
      | username      | password     |
      | standard_user | secret_sauce |
      | problem_user  | secret_sauce |
      | error_user    | secret_sauce |
      | visual_user   | secret_sauce |