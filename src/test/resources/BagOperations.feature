Feature: Bag Operations

  Scenario: Removing a product from the Bag
    Given the user is on a product page
    When adding the product to the Bag
    When I remove a product
    Then the product is removed from the Bag

  Scenario: Increasing/Reducing the quantity of a product in the Bag
    Given the user is on a product page
    When adding the product to the Bag
    When I add quantity
    Then the product quantity is increased
    When I remove quantity
    Then the product quantity is reduced
