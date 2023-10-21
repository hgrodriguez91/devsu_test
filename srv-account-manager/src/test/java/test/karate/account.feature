Feature: Account manager karate test
  Background:
    Given url  "http://localhost:8080/cuentas"

  Scenario: 'Testing CRUD Accounts'
    # Creating account
    Given header Content-Type = 'application/json'
    Given request account
    Given method POST
    Then status 200
    And match $ contains {id:"#notnull"}
    And def accountId = response.id
    # List all accounts
    Given method GET
    Then status 200
    And def ids = $..id 
    And assert ids.length > 0
    # Get one account
    Given path accountId
    Given method GET
    Then status 200
    And assert accountId == response.id
    # Update account
    Given path accountId
    Given request { clientId: 3,type: "Corriente", balance: 1000, status: true}
    Given method PUT
    Then status 200
    And match $.balance == "1000"
    And def movId = response.id
    #Delete account
    Given path accountId
    Given method DELETE
    Then status 200