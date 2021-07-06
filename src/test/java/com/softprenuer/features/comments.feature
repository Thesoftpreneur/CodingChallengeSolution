Feature: Comments
  test gist comments functionality

  Scenario: List gist comments
    Given a Gist id: "90e0c8b6e5c468bf76f37120cd997468"
    When a request listing all the comments is sent
    Then "200" status code is returned

  Scenario: Create and delete a gist comment
    Given a Gist id: "90e0c8b6e5c468bf76f37120cd997468"
    Given a Comment body: "This is a new ordinary comment"
    When a request creating a gist comment is sent
    Then "201" status code is returned
    Then the comment body "This is a new ordinary comment" is returned
    ###### Delete a gist Comment
    When a request removing a gist comment is sent
    Then "204" status code is returned

  Scenario: Create a gist comment without authorization
    Given a Gist id: "90e0c8b6e5c468bf76f37120cd997468"
    Given a Comment body: "This comment shouldn't appear"
    When an unauthorized request creating a gist comment is sent
    Then "401" status code is returned

  Scenario: Get a gist comment
    Given a Gist id: "90e0c8b6e5c468bf76f37120cd997468"
    Given a Comment id: "3804478"
    When a request listing a gist comment is sent
    Then "200" status code is returned

  Scenario: Update a gist comment
    Given a Gist id: "90e0c8b6e5c468bf76f37120cd997468"
    Given a Comment id: "3804478"
    Given a Comment body: "This comment is updated #1"
    When a request updating a gist comment is sent
    Then "200" status code is returned
    Then the comment body "This comment is updated #1" is returned
    Given a Comment body: "This comment is updated #2"
    When a request updating a gist comment is sent
    Then "200" status code is returned
    Then the comment body "This comment is updated #2" is returned

  Scenario: Update a non-existing gist comment
    Given a Gist id: "90e0c8b6e5c468bf76f37120cd997468"
    Given a Comment id: "380"
    Given a Comment body: "This comment does not exist"
    When a request updating a gist comment is sent
    Then "404" status code is returned