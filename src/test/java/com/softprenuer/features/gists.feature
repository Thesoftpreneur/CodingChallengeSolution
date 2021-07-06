Feature: Gists API
  The Gists API should be able to do CRUD operations

  Scenario: Create a Gist
    Given a gist name: "LoremName"
    Given a gist description: "Lorem Description"
    Given a gist content: "Lorem Content"
    When a request is sent
    Then the gist name is "LoremName"
    Then the gist description is "Lorem Description"
    Then the gist content is "Lorem Content"
