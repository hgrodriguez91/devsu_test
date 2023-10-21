Feature: 'Testing MovementHandle api controller'
  Background:
    Given url "http://localhost:8080/movimientos"

  Scenario: 'Testing CRUD Movement '
    #Create a movement
    Given request movement
    Given header Content-Type = 'application/json'
    Given method POST
    Then status 200
    And match $ contains {id:"#notnull"}
    And def movId = response.id
    And def accountId = response.accountId
    #Geting all movements
    Given method GET
    Then status 200
    And def ids = $..id
    And assert ids.length > 0
    #Geting one moviment
    Given path movId
    Given method GET
    Then status 200
    And match movId == response.id
    #Updating an movement
    Given path accountId
    Given request {accountId: 3, value: 300, balance: 300 }
    Given method PUT
    Then status 200
    #Delete movement
    Given path accountId
    Given method DELETE
    Then status 200